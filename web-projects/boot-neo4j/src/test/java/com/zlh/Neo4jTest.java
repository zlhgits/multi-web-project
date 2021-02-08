package com.zlh;

import com.zlh.service.Neo4jService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @package com.zlh
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */

public class Neo4jTest extends Neo4jApplicationTest{
    @Autowired
    Neo4jService neo4jService;

    /**
     * 查找所有系统相关
     */
    @Test
    public void findAllSys(){
        neo4jService.findAllSys();
    }
}
