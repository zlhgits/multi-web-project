package com.zlh.model;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;

import java.util.List;

/**
 * Neo基础类
 * @package com.zlh.model
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/23
 */
@Data
public class NeoBase {
    /**
     * Neo4j会分配的ID（节点唯一标识 当前类中有效）
     */
    @Id
    @Index(unique = true)
    private String id;

    private String name;
    private String nameEn;

    /**
     * 关系
     */
    private String relation;
    /**
     * 指向关系
     */
    private List<String> attributes;
}
