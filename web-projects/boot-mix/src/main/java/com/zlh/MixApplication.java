package com.zlh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @package com.zlh
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/7/27
 */
@SpringBootApplication
public class MixApplication {
    public static void main(String[] args) {
        SpringApplication.run(MixApplication.class,args);
        System.out.println("--------启动成功-------");
    }
}
