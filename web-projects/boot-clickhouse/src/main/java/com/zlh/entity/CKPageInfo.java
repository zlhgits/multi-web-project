package com.zlh.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * ck 分页
 * @package com.zlh.entity
 * @company: dacheng
 * @author: zlh
 * @createDate: 2021/2/22
 */
@Slf4j
@Getter
@Setter
public class CKPageInfo {
    /**
     * 查询页码
     */
    private int pageNum;
    /**
     * 分页查询数
     */
    private int pageSize;
    /**
     * 游标
     */
    private int cursor;
    /**
     * 总行数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 分页查询结果
     */
    private List list=new ArrayList();
}
