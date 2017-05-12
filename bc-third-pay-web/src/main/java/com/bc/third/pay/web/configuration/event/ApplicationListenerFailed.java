package com.bc.third.pay.web.configuration.event;

import org.springframework.boot.context.event.ApplicationFailedEvent;
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
public class ApplicationListenerFailed implements ApplicationListener<ApplicationFailedEvent> {
    private static final Logger logger = Logger.getLogger(ApplicationListenerFailed.class.getName());
    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        logger.warning("系统启动失败......");
    }
}
