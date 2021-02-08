package com.zlh.model;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 光交机/交换机
 * @package com.zlh.model
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */
@Data
@QueryResult
@NodeEntity(label = "swich")
public class SwichDto extends NeoBase {
    /**交换机/光交机ip*/
    private String swichIp;
    /**交换机/光交机名称*/
    private String swichName;
    /**交换机/光交机品牌*/
    private String swichBrand;
    /**交换机/光交机型号*/
    private String swichPattern;
}
