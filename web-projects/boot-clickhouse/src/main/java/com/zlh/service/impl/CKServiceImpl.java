package com.zlh.service.impl;

import com.zlh.entity.CKPageList;
import com.zlh.service.CKService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @package com.zlh.service.impl
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/2/20
 */
@Slf4j
@Service
public class CKServiceImpl implements CKService {
    @Resource
    @Qualifier(value = "clickHouseJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public void findCkTableData() {
        List<Map<String, Object>> showTables = jdbcTemplate.queryForList("show tables");
        for (Map<String, Object> map:showTables){
            Collection<Object> values = map.values();
            values.forEach(System.out::println);
        }
    }

    @Override
    public void findCkLimitData() {
        List<Map<String, Object>> res = jdbcTemplate.queryForList("select * from aiops.dc_kpi_monitor_source limit 10");
        for (Map<String, Object> map:res){
            Collection<Object> values = map.values();
            values.forEach(System.out::println);
        }
    }

    @Override
    public void findCkPageList() {
        String sql = "select * from aiops.dc_kpi_monitor_source";
        int pageNum = 1;
        int pageSize = 5;
        CKPageList ckPageList = this.getCKPageList(sql, pageNum, pageSize);
        System.out.println("ckPageList: total->"+ckPageList.getTotal()+" pages->"+ckPageList.getPages());
        List<Map<String, Object>> res = jdbcTemplate.queryForList(sql+" limit "+ckPageList.getCursor()+","+pageSize);
        // 封装到分页结果集
        ckPageList.setList(res);
        for (Map<String, Object> map:res){
            Collection<Object> values = map.values();
            values.forEach(System.out::print);
            System.out.println();
        }
    }

    /**
     * 分页工具
     * @param sql
     * @param pageNum
     * @param pageSize
     * @return
     */
    private CKPageList getCKPageList(String sql,int pageNum,int pageSize){
        CKPageList ckPageList = new CKPageList();
        ckPageList.setPageNum(pageNum);
        ckPageList.setPageSize(pageSize);
        // limit游标公式(pageNum-1)*pageSize
        int cursor = 0;
        if(pageNum > 1){
            cursor = (pageNum - 1) * pageSize;
        }
        ckPageList.setCursor(cursor);
        //查询总行数
        String rowsql="select count(*) from ("+sql+") t";
        Long totalRows = jdbcTemplate.queryForObject(rowsql, Long.class);
        ckPageList.setTotal(totalRows);
        // 总页数
        int pages = (int) (totalRows / pageSize + ((totalRows % pageSize == 0) ? 0 : 1));
        ckPageList.setPages(pages);

        return ckPageList;
    }
}
