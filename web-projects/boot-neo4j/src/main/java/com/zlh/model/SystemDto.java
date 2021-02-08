package com.zlh.model;

import lombok.Data;

/**
 * @package com.zlh.model
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */
@Data
public class SystemDto extends NeoBase {
    /**
     * 系统英文名称
     */
    private String sysNameEn;
    /**
     * 系统名称
     */
    private String sysName;
    /**
     * 系统编号
     */
    private String sysNum;
}
