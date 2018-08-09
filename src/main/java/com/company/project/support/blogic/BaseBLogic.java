package com.company.project.support.blogic;

import com.company.project.support.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基本业务逻辑
 *
 * @author wangzhj
 */
public abstract class BaseBLogic implements BLogic {

    protected final static Logger LOGGER = LoggerFactory.getLogger(BaseBLogic.class);

    @Override
    public final void doBusiness(RequestContext cxt) throws Exception {
        try {
            checkInput();
            processBusiness();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 验证输入
     */
    public abstract void checkInput();

    /**
     * 处理业务
     */
    public abstract void processBusiness();
}
