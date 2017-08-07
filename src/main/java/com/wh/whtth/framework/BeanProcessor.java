package com.wh.whtth.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by Lizanle on 2017/8/7.
 */
public class BeanProcessor implements BeanPostProcessor{
    Logger logger = LoggerFactory.getLogger(BeanProcessor.class);
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        logger.info( s);
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        return o;
    }

}
