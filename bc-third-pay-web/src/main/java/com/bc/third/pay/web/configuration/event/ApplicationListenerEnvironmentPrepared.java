package com.bc.third.pay.web.configuration.event;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-11
 * Time:  下午 4:27.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationListenerEnvironmentPrepared implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {


    private static final Logger logger = Logger.getLogger(ApplicationListenerEnvironmentPrepared.class.getName());
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        logger.info("系统环境准备完成......");
    }
}
