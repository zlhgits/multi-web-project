package com.zlh.model;

import com.zlh.config.NeoConsts;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * @package com.dctech.andrea.model.neo4j.dto
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/11/5
 */
@Data
@Slf4j
@QueryResult
@NodeEntity(label = "web")
public class WebDto extends NeoBase {
    /**
     * 所属系统唯一标识
     */
    private String sysNameEn;
    /**
     * 端口
     */
    private String webPort;
    /**
     * 进程名称
     */
    private String webName;
    /**
     * 中间件
     */
    private String middleWares;
    /**
     * ip
     */
    private String ip;
    /**
     * 所属服务器
     */
    @Relationship(NeoConsts.R_APP_TO_SERVER)
    private List<ServerDto> serverList;
    /**
     * 关联负载均衡
     */
    @Relationship(NeoConsts.R_APP_TO_APP)
    private List<ServiceDto> ServiceList;

    /**
     * 关联系统
     */
    @Relationship(type = NeoConsts.R_APP_TO_APP,direction = Relationship.INCOMING)
    private SystemDto sys;
}
