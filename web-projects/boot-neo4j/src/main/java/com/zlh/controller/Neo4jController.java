package com.zlh.controller;

import com.zlh.config.CommonConstants;
import com.zlh.model.SystemDto;
import com.zlh.service.Neo4jService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @package com.zlh.controller
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/23
 */
@RestController
@RequestMapping("/neo4j")
public class Neo4jController {
    private static final Logger logger=LoggerFactory.getLogger(Neo4jController.class);

    @Autowired
    Neo4jService neo4jService;

    /**
     * 根据系统英文名称查找拓扑图，如sysNameEn:大乘内研系统
     * @param systemDto
     * @return
     */
    @PostMapping("/findTopoeBySysEn")
    public Map<String, Object> findTopoeBySysEn(@RequestBody SystemDto systemDto){
        try {
            Map<String, Object> objectMap = neo4jService.findTopoeBySysEn(systemDto);
            return objectMap;
        }catch (Exception e){
            logger.error("根据系统id查找拓扑图error",e);
        }
        return null;
    }

    @RequestMapping(value = "/downLoadTemplate",method = RequestMethod.GET)
    public void downLoadTemplate(HttpServletResponse response) throws Exception {
        neo4jService.downLoadTemplate(response);
    }

    /**
     * 批量导入Topoe数据
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadTopoe")
    public SystemDto uploadTopoe(MultipartFile file) throws Exception {
        if (file.isEmpty()){
            throw new Exception("文件为空! 请选择非空文件上传!");
        }
        String fileType = file.getOriginalFilename().split("\\.")[1];
        if(!CommonConstants.DATASET_FILE_TYPE_CSV.equalsIgnoreCase(fileType)){
            throw new Exception("文件不合法! 请选择csv文件上传!");
        }
        SystemDto systemDto = neo4jService.uploadTopoe(file);
        return systemDto;
    }

    /**
     * 根据系统id删除拓扑，如id:大乘内研系统
     */
    @DeleteMapping("/delTopoeBySysId")
    public void delTopoeBySysId(@RequestBody SystemDto systemDto){
        neo4jService.delTopoeBySysId(systemDto);
    }
}
