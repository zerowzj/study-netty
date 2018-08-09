package com.company.project.blogic;

import com.company.project.support.Cmd;
import com.company.project.support.blogic.BaseBLogic;
import org.springframework.stereotype.Service;

@Cmd("1002")
@Service
public class Demo2BLogic extends BaseBLogic {

    @Override
    public void checkInput() {
        LOGGER.info("Demo2BLogicDemo2BLogicDemo2BLogic");
    }

    @Override
    public void processBusiness() {

    }
}
