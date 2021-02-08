package com.zlh.config;

/**
 * 应用与应用：关联
 * 应用与服务器：属于
 * 服务器与交换机：使用
 * @package com.zlh.config
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/19
 */
public interface NeoConsts {
    /**
     * 关系：应用使用的服务器
     */
    String R_APP_TO_SERVER = "属于";
    /**
     * 关系：数据库使用的服务器
     */
    String R_APP_TO_APP = "关联";
    /**
     * 关系：服务器使用的交换机
     */
    String R_SERVER_TO_SWICH = "使用";
}
