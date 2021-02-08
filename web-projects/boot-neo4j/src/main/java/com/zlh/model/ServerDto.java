package com.zlh.model;

import com.zlh.config.NeoConsts;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * 主机/服务器
 * @package com.zlh.model
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */
@Data
@QueryResult
@NodeEntity(label = "server")
public class ServerDto  extends NeoBase {
    /**
     * 主机ip
     */
    private String serverIp;
    /**
     * 主机名称
     */
    private String serverName;
    /**
     * os版本
     */
    private String serverOs;
    /**
     * 使用的交换机
     */
    @Relationship(NeoConsts.R_SERVER_TO_SWICH)
    private List<SwichDto> swichList;
}
