package com.zlh.service.impl;

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
}
