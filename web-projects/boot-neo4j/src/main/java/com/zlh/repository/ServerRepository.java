package com.zlh.repository;

import com.zlh.model.ServerDto;
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
public interface ServerRepository extends Neo4jRepository<ServerDto,String> {
    /**
     * 根据系统id查找主机及关联交换机
     * @param id
     * @return
     */
    @Query("MATCH (n:SystemDto{id:{id}})-[*]->(s:server)-[r:`使用`]->(w:swich) with distinct s.id as id,s.name as name,s.nameEn as nameEn,s.serverIp as serverIp,s.serverName as serverName,s.serverOs as serverOs,r as relation,collect(distinct w) as attributes RETURN id,name,serverIp,serverName,serverOs,relation,attributes,nameEn order by id")
    List<Map<String,Object>> findNodeBySysId(@Param("id") String id);

    /**
     * 根据系统id查找主机
     * @param id
     * @return
     */
    @Query("MATCH (n:SystemDto{id:{id}})-[*]->(s:server) with distinct s.id as id,s.name as name,s.nameEn as nameEn,s.serverIp as serverIp,s.serverName as serverName,s.serverOs as serverOs RETURN id,name,serverIp,serverName,serverOs,nameEn order by id")
    List<Map<String,Object>> findAllServerBySysId(@Param("id") String id);

    /**
     * 查找实例模块
     * @param id
     * @return
     */
    @Query("MATCH (n:server{id:{id}})<-[*..1]-(s) with s.id as id,s.name as name,s.nameEn as nameEn,s.middleWares as middleWares return id,name,middleWares,nameEn order by name")
    List<Map<String,Object>> findInstanceById(@Param("id") String id);

    /**
     * 根据系统id删除服务器
     * @param sysId
     */
    @Query("MATCH (n:SystemDto{id:{sysId}})-[*]->(c:server) DETACH DELETE c")
    void delServerBySysId(@Param("sysId") String sysId);
}
