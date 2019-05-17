/**
 *
 */
package com.sydml.transaction.platform.data;


import com.sydml.transaction.platform.sign.Ignore;
import com.sydml.transaction.platform.sign.Page;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.QueryParam;

/**
 * 分页参数
 */
public class PageCondition extends SortCondition {

    @Page
    public static final Integer PAGE_DEFAULTVALUE = -1;

    @Page
    public static final Integer LIMIT_DEFAULTVALUE = Integer.MAX_VALUE;
    /**
     *
     */
    @Ignore
    private static final long serialVersionUID = -6440361698670747510L;
    /**
     * 查询第几页
     */
    @Page
//    @QueryParam("page")
    private Integer page = PAGE_DEFAULTVALUE;
    /**
     * 每页显示条数
     */
    @Page
    @QueryParam("limit")
    private Integer limit = LIMIT_DEFAULTVALUE;

    /**
     * 是否统计总条数
     */
    @Page
    @QueryParam("count")
    private Boolean count = Boolean.TRUE;

    public int getPage() {
        if (page == null) {
            return PAGE_DEFAULTVALUE;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        if (limit == null) {
            return LIMIT_DEFAULTVALUE;
        }
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Boolean getCount() {
        return count;
    }

    public void setCount(Boolean count) {
        this.count = count;
    }

}
