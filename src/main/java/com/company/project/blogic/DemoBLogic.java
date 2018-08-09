package com.company.project.blogic;

import com.company.project.support.Cmd;
import com.company.project.support.blogic.BaseBLogic;
import org.springframework.stereotype.Service;

@Cmd("1001")
@Service
public class DemoBLogic extends BaseBLogic {

    @Override
    public void checkInput() {
        LOGGER.info("DemoBLogicDemoBLogicDemoBLogic");
//        throw new IllegalArgumentException("参数错误");
    }

    @Override
    public void processBusiness() {

    }
}
