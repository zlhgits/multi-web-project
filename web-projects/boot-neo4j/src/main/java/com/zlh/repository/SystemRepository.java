package com.zlh.repository;

import com.zlh.model.SystemDto;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

/**
 * @package com.zlh.repository.dto
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */
public interface SystemRepository extends Neo4jRepository<SystemDto,String> {
    /**
     * 根据系统id删除System
     * @param sysId
     */
    @Query("MATCH (n:SystemDto{id:{sysId}}) DETACH DELETE n")
    void delSystemBySysId(@Param("sysId") String sysId);
}
