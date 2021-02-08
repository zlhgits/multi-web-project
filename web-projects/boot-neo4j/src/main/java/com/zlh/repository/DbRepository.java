package com.zlh.repository;

import com.zlh.model.DbDto;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @package com.zlh.repository.dto
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */
public interface DbRepository extends Neo4jRepository<DbDto,String> {
    /**
     * 根据系统id查找数据库节点
     * @param id
     * @return
     */
    @Query("MATCH (n:SystemDto{id:{id}})-[*]->(f:db)-[r:`属于`]->(c:server) with distinct f.id as id,f.name as name,f.nameEn as nameEn,f.dbType as dbType,f.dbPort as dbPort,r as relation,collect(distinct c) as attributes RETURN id,name,dbType,dbPort,relation,attributes,nameEn order by id")
    List<Map<String,Object>> findDbBySysId(@Param("id") String id);
    /**
     * 根据系统id删除db
     * @param sysId
     */
    @Query("MATCH (n:SystemDto{id:{sysId}})-[*]->(f:db) DETACH DELETE f")
    void delDbBySysId(@Param("sysId") String sysId);
}
