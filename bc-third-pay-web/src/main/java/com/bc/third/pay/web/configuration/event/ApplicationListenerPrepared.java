package com.bc.third.pay.web.configuration.event;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-11
 * Time:  下午 4:28.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationListenerPrepared implements ApplicationListener<ApplicationPreparedEvent> {
    private static final Logger logger = Logger.getLogger(ApplicationListenerPrepared.class.getName());
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        logger.info("系统上下文准备完成......");
    }
}
