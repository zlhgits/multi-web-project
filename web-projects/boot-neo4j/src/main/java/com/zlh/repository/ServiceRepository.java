package com.zlh.repository;

import com.zlh.model.ServiceDto;
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
public interface ServiceRepository extends Neo4jRepository<ServiceDto,String> {
    /**
     * 根据系统id查找数据库节点
     * @param id
     * @return
     */
    @Query("MATCH (n:SystemDto{id:{id}})-[*]->(f:service)-[r]->(c) with distinct f.id as id,f.name as name,f.nameEn as nameEn,f.svrName as svrName,f.middleWares as middleWares,r as relation,collect(distinct c) as attributes RETURN id,name,svrName,middleWares,relation,nameEn,attributes  order by id")
    List<Map<String,Object>> findServiceBySysId(@Param("id") String id);

    /**
     * 根据系统id删除Service
     * @param sysId
     */
    @Query("MATCH (n:SystemDto{id:{sysId}})-[*]->(f:service) DETACH DELETE f")
    void delServiceBySysId(@Param("sysId") String sysId);
}
