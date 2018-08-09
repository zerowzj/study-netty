package com.company.project.support.blogic;

import com.company.project.support.context.RequestContext;

/**
 * 业务逻辑
 *
 * @author wangzhj
 */
public interface BLogic {

    /**
     * 执行业务
     *
     * @param cxt
     */
    void doBusiness(RequestContext cxt) throws Exception;
}
