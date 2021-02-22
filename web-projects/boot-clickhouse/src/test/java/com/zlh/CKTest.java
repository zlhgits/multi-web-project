package com.zlh;

import com.zlh.service.CKService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @package com.zlh
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/2/20
 */
public class CKTest extends ClickHouseApplicationTest {
    @Autowired
    CKService ckService;

    @Test
    public void findCkData(){
        ckService.findCkTableData();
        System.out.println("*********************");
        ckService.findCkLimitData();
    }

    @Test
    public void findCkPageList(){
        ckService.findCkPageList();
    }
}
