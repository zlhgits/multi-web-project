package com.zlh.repository;

import com.zlh.model.WebDto;
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
public interface WebRepository extends Neo4jRepository<WebDto,String> {
    /**
     * 根据系统id查找数据库节点
     * @param id
     * @return
     */
    @Query("MATCH (n:SystemDto{id:{id}})-[*]->(f:web)-[r]->(c) with distinct f.id as id,f.name as name,f.nameEn as nameEn,f.webName as webName,f.middleWares as middleWares,r as relation,collect(distinct c) as attributes RETURN id,name,webName,middleWares,relation,attributes,nameEn order by id")
    List<Map<String,Object>> findWebBySysId(@Param("id") String id);

    /**
     * 根据系统id删除Web
     * @param sysId
     */
    @Query("MATCH (n:SystemDto{id:{sysId}})-[*]->(f:web) DETACH DELETE f")
    void delWebBySysId(@Param("sysId") String sysId);
}
