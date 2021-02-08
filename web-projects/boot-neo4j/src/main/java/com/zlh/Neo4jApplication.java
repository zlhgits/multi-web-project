package com.zlh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @package com.zlh
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/19
 */
@SpringBootApplication
public class Neo4jApplication {
    private static final Logger logger=LoggerFactory.getLogger(Neo4jApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class,args);
        logger.info("============start succes==============");
    }
}
