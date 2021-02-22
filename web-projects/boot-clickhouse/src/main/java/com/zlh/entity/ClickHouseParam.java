package com.zlh.entity;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

/**
 * @package com.zlh.entity
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/2/20
 */
@Getter
@Setter
public class ClickHouseParam extends ClickHouseProperties {
    /**
     * 请求url，支持多个
     * jdbc:clickhouse://ip:port,ip:port
     * {@code jdbc:clickhouse://<first-host>:<port>,<second-host>:<port>/<database>?param1=value1&param2=value2 }
     */
    private String url;
    /**
     * 连接可用性检测间隔时间
     * 单位(秒)
     * 默认 10秒钟检测一次
     */
    private int delay = 10;
}
