package com.controller;

import com.datasourceswitch.DataSourceEnum;
import com.datasourceswitch.config.DataSourceSwitch;
import com.plusEntity.Authic;
import com.plusMapper.userPlusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class dataSourceSwitchController {

    private  final  static Logger logger = LoggerFactory.getLogger(dataSourceSwitchController. class);

    @Autowired
    private userPlusMapper userPlusMapper;

    @RequestMapping("/getSwichUsermaster1")
    public void getSwichUsermaster1() {
        logger.info("getSwichUsermaster1().....");
        List<Authic> plusUser = userPlusMapper.getPlusUser();
        for (int i = 0; i < plusUser.size(); i++) {
            Authic authic = plusUser.get(i);
            logger.info(authic.toString());
        }
    }

    @DataSourceSwitch
    @RequestMapping("/getSwichUsermaster")
    public void getSwichUsermaster() {
        logger.info("getSwichUsermaster().....");
        List<Authic> plusUser = userPlusMapper.getPlusUser();
        for (int i = 0; i < plusUser.size(); i++) {
            Authic authic = plusUser.get(i);
            logger.info(authic.toString());
        }
    }

    @DataSourceSwitch(Value = DataSourceEnum.FOLLOW)
    @RequestMapping("/getSwichUserFollow")
    public void getSwichUserFollow() {
        logger.info("getSwichUserFollow().....");
        List<Authic> plusUser = userPlusMapper.getPlusUser();
        for (int i = 0; i < plusUser.size(); i++) {
            Authic authic = plusUser.get(i);
            logger.info(authic.toString());
        }
    }

}
