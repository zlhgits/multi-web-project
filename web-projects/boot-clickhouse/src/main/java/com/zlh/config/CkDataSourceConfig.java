package com.zlh.config;

import com.zlh.entity.ClickHouseParam;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @package com.zlh.config
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/2/20
 */
@Slf4j
@Configuration
public class CkDataSourceConfig {
    @Bean
    @Resource
    @ConfigurationProperties(prefix = "spring.clickhouse")
    public ClickHouseParam clickHouseParam() {
        ClickHouseParam clickHouseParam = new ClickHouseParam();
        return clickHouseParam;
    }

    @Resource
    @Bean(name = "clickHouseDataSource")
    public DataSource clickHouseDataSource(ClickHouseParam clickHouseParam) {
        BalancedClickhouseDataSource balancedClickhouseDataSource
                = new BalancedClickhouseDataSource(clickHouseParam.getUrl(), clickHouseParam);
        balancedClickhouseDataSource.scheduleActualization(clickHouseParam.getDelay(), TimeUnit.SECONDS);
        return balancedClickhouseDataSource;
    }

    @Bean(name = "clickHouseJdbcTemplate")
    public JdbcTemplate clickHouseJdbcTemplate(@Qualifier(value = "clickHouseDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
