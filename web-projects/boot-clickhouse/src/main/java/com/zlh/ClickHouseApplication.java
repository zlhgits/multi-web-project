package com.zlh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @package com.zlh
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/2/20
 */
@Slf4j
@SpringBootApplication
public class ClickHouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClickHouseApplication.class);
        log.info("-----------ClickHouseApplication running--------------");
    }
}
