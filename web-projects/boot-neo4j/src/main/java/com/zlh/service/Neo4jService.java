package com.zlh.service;

import com.google.common.collect.Lists;
import com.zlh.model.*;
import com.zlh.repository.*;
import com.zlh.util.FileReaderUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @package com.zlh.service
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */
@Service
public class Neo4jService {
    private static final Logger logger=LoggerFactory.getLogger(Neo4jService.class);

    @Autowired
    DbRepository dbRepository;
    @Autowired
    ServerRepository serverRepository;
    @Autowired
    SwichRepository swichRepository;
    @Autowired
    ServiceRepository svrRepository;
    @Autowired
    WebRepository webRepository;
    @Autowired
    SystemRepository sysRepository;

    /**
     * 查找所有系统相关
     */
    public void findAllSys(){
        Iterable<SystemDto> list = sysRepository.findAll();
        list.forEach(System.out::println);
        List<SwichDto> swichList = swichRepository.findAllSwich();
        swichList.forEach(System.out::println);
    }

    public Map<String, Object> findTopoeBySysEn(SystemDto system) throws Exception {
        String sysEn = system.getSysNameEn();
        Map<String, Object> map = new HashMap<>(5);
        //web
        List<Map<String,Object>> webList = webRepository.findWebBySysId(sysEn);
        map.put("web",webList);
        //service
        List<Map<String,Object>> serviceList = svrRepository.findServiceBySysId(sysEn);
        map.put("service",serviceList);
        //db
        List<Map<String,Object>> dbList = dbRepository.findDbBySysId(sysEn);
        map.put("数据库",dbList);
        //服务器
        List<Map<String,Object>> serverList = serverRepository.findNodeBySysId(sysEn);
        List<Map<String,Object>> serverAllList = serverRepository.findAllServerBySysId(sysEn);
        serverAllList.addAll(serverList);
        map.put("服务器",serverAllList);
        //交换机
        List<Map<String,Object>> swichList = swichRepository.findSwichBySysId(sysEn);
        map.put("交换机",swichList);
        return map;
    }

    public void downLoadTemplate(HttpServletResponse response) throws Exception {
        InputStream inputStream = this.getClass().getResourceAsStream("/template/cmdb_template.csv");
        //下载模版
        FileReaderUtils.downLoadFile(inputStream,response);
    }

    private static final String[] CMDB_TYPE = {"swich","server","db","service","web","system"};

    public SystemDto uploadTopoe(MultipartFile file) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            // 读取csv数据,type,id,name,relation,os,dbType
            List<Map<String, String>> csvList = FileReaderUtils.readCsv(inputStream);
            Map<String,List<Map<String, String>>> typeMap = new HashMap<>(6);
            for (Map<String, String> strMap: csvList){
                String type = strMap.get("type");
                List<Map<String, String>> mapList = typeMap.get(type);
                if (mapList == null){
                    mapList = new ArrayList<>(16);
                    typeMap.put(type,mapList);
                }
                mapList.add(strMap);
            }

            // 系统
            SystemDto systemDto = new SystemDto();
            List<Map<String, String>> sysMapList = typeMap.get(CMDB_TYPE[5]);
            for (Map<String, String> sysMapTmp: sysMapList){
                String id = sysMapTmp.get("id");
                String name = sysMapTmp.get("name");
                systemDto.setId(id);
                systemDto.setName(name);
                systemDto.setNameEn(id);
                systemDto.setSysNameEn(id);
            }
            // 保存系统system
            sysRepository.save(systemDto);

            // 交换机
            Map<String, SwichDto> swichMap = new HashMap<>(16);
            List<Map<String, String>> swichMapList = typeMap.get(CMDB_TYPE[0]);
            for (Map<String, String> swichMapTmp: swichMapList){
                String id = swichMapTmp.get("id");
                String name = swichMapTmp.get("name");
                SwichDto swichDto = new SwichDto();
                //系统可能共用交换机
                swichDto.setId(id+"-"+systemDto.getId());
                swichDto.setName(name);
                swichDto.setNameEn(id+"-system");
                swichDto.setSwichIp(id);
                swichMap.put(id,swichDto);
            }
            //保存交换机
            swichRepository.saveAll(swichMap.values());

            // 服务器
            Map<String, ServerDto> serverMap = new HashMap<>(16);
            List<Map<String, String>> serverMapList = typeMap.get(CMDB_TYPE[1]);
            for (Map<String, String> serverMapTmp: serverMapList){
                String id = serverMapTmp.get("id");
                String name = serverMapTmp.get("name");
                String relation = serverMapTmp.get("relation");
                ServerDto serverDto = new ServerDto();
                //系统可能共用服务器
                serverDto.setId(id+"-"+systemDto.getId());
                serverDto.setName(name);
                serverDto.setNameEn(id+"-system");
                serverDto.setServerIp(id);
                String os = serverMapTmp.get("os");
                serverDto.setServerOs(os);
                //关联关系
                List<SwichDto> relationList = getRelationList(relation, swichMap);
                serverDto.setSwichList(relationList);

                serverMap.put(id,serverDto);
            }
            //保存服务器
            serverRepository.saveAll(serverMap.values());

            // 数据库
            Map<String, DbDto> dbMap = new HashMap<>(16);
            List<Map<String, String>> dbMapList = typeMap.get(CMDB_TYPE[2]);
            for (Map<String, String> dbMapTmp: dbMapList){
                //系统可能共用db
                String id = dbMapTmp.get("id");
                String[] split = StringUtils.split(id, "-");
                String name = dbMapTmp.get("name");
                DbDto dbDto = new DbDto();
                //db id为ip+系统id
                dbDto.setId(id+"-"+systemDto.getId());
                dbDto.setName(name);
                String ip = split[0];
                dbDto.setNameEn(ip+"-"+name);
                dbDto.setDbPort(split[1]);
                String dbType = dbMapTmp.get("dbType");
                dbDto.setDbType(dbType);
                //关联关系
                ServerDto serverDto = serverMap.get(ip);
                if (serverDto != null) {
                    dbDto.setServerList(Lists.newArrayList(serverDto));
                }
                dbMap.put(id,dbDto);
            }
            //保存db
            dbRepository.saveAll(dbMap.values());

            // service
            Map<String, ServiceDto> serviceMap = new HashMap<>(16);
            List<Map<String, String>> serviceMapList = typeMap.get(CMDB_TYPE[3]);
            for (Map<String, String> serviceMapTmp: serviceMapList){
                String id = serviceMapTmp.get("id");
                String[] split = StringUtils.split(id, "-");
                String name = serviceMapTmp.get("name");
                String relation = serviceMapTmp.get("relation");
                ServiceDto serviceDto = new ServiceDto();
                serviceDto.setId(id+"-"+systemDto.getId());
                serviceDto.setName(name);
                String ip = split[0];
                serviceDto.setNameEn(ip+"-"+name);
                serviceDto.setSvrName(name);
                serviceDto.setSysNameEn(systemDto.getNameEn());
                serviceDto.setSys(systemDto);
                //关联关系
                List<DbDto> dbList = getRelationList(relation, dbMap);
                serviceDto.setDbList(dbList);
                List<ServerDto> serverList = getRelationList(ip, serverMap);
                serviceDto.setServerList(serverList);

                serviceMap.put(id,serviceDto);
            }
            //保存service
            svrRepository.saveAll(serviceMap.values());

            // web
            Map<String, WebDto> webMap = new HashMap<>(16);
            List<Map<String, String>> webMapList = typeMap.get(CMDB_TYPE[4]);
            for (Map<String, String> webMapTmp: webMapList){
                String id = webMapTmp.get("id");
                String[] split = StringUtils.split(id, "-");
                String name = webMapTmp.get("name");
                String relation = webMapTmp.get("relation");
                WebDto webDto = new WebDto();
                webDto.setId(id+"-"+systemDto.getId());
                webDto.setName(name);
                String ip = split[0];
                webDto.setNameEn(ip+"-"+name);
                webDto.setWebName(name);
                webDto.setSysNameEn(systemDto.getNameEn());
                webDto.setSys(systemDto);
                // 关联关系
                List<ServiceDto> serviceList = getRelationList(relation, serviceMap);
                webDto.setServiceList(serviceList);
                List<ServerDto> serverList = getRelationList(ip, serverMap);
                webDto.setServerList(serverList);

                webMap.put(id,webDto);
            }
            // 保存web
            webRepository.saveAll(webMap.values());
            return systemDto;
        }catch (Exception e){
            throw new Exception(e);
        }finally {
            if (inputStream != null){
                inputStream.close();
            }
        }
    }

    private <T> List<T> getRelationList(String relation, Map<String,T> map){
        List<T> tList = new ArrayList<>();
        String all = "all";
        if (all.equals(relation)){
            tList.addAll(map.values());
        }else if (StringUtils.isNotBlank(relation)){
            String[] split = StringUtils.split(relation, ";");

            for (int i=0; i<split.length;i++){
                T t = map.get(split[i]);
                if (t == null){
                    break;
                }
                tList.add(t);
            }
        }
        return tList;
    }

    public void delTopoeBySysId(SystemDto systemDto) {
        String sysId = systemDto.getId();
        //删除交换机
        swichRepository.delSwichBySysId(sysId);
        //删除服务器
        serverRepository.delServerBySysId(sysId);
        //删除db
        dbRepository.delDbBySysId(sysId);
        //删除service
        svrRepository.delServiceBySysId(sysId);
        //删除web
        webRepository.delWebBySysId(sysId);
        //删除system
        sysRepository.delSystemBySysId(sysId);
    }
}
