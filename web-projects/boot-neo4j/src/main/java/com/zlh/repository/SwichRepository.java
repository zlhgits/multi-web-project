package com.zlh.repository;

import com.zlh.model.SwichDto;
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
public interface SwichRepository extends Neo4jRepository<SwichDto,String> {
    /**
     * 根据系统id查找交换机节点
     * @param id
     * @return
     */
    @Query("MATCH (n:SystemDto{id:{id}})-[*]->(s:server)-[r:`使用`]->(w:swich) with distinct w.id as id,w.name as name,w.nameEn as nameEn,w.swichIp as swichIp RETURN id,name,swichIp,nameEn order by id")
    List<Map<String,Object>> findSwichBySysId(@Param("id") String id);

    /**
     * 根据系统id删除交换机
     * @param sysId
     */
    @Query("MATCH (n:SystemDto{id:{sysId}})-[*]->(s:server)-[r:`使用`]->(w:swich) DETACH DELETE w")
    void delSwichBySysId(@Param("sysId") String sysId);

    /**
     * 查询所有交换机
     * @return
     */
    @Query("MATCH (n)-[r]->(o:swich) with o.id as id,o.name as name,collect(distinct n) as attributes RETURN id,name,attributes")
    List<SwichDto> findAllSwich();
}
