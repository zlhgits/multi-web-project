package com.zlh.model;

import com.zlh.config.NeoConsts;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * 数据库
 * @package com.zlh.model
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */
@Data
@QueryResult
@NodeEntity(label = "db")
public class DbDto extends NeoBase {
    /**类型*/
    private String dbType;
    /**端口*/
    private String dbPort;
    private String agentIp;
    private String dbName;
    /**
     * 部署的主机
     */
    @Relationship(NeoConsts.R_APP_TO_SERVER)
    private List<ServerDto> serverList;
}
