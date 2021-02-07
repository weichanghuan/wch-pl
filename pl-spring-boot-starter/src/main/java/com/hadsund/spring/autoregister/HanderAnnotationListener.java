package com.hadsund.spring.autoregister;


import com.hadsund.main.listener.Listener;
import com.hadsund.main.push.Pusher;
import com.hadsund.main.registry.Registry;
import com.hadsund.spring.annotation.PLEventListener;
import com.hadsund.spring.annotation.PLRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class HanderAnnotationListener implements ApplicationListener<ContextRefreshedEvent> {


    private static final Logger logger = LoggerFactory.getLogger(HanderAnnotationListener.class);

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.debug("init HanderAnnotationListener");
        try {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            listenerToRegistry(applicationContext);
            registerToPusher(applicationContext);
        } catch (Exception ex) {
            logger.error("HanderAnnotationListener is error", ex);
        }

    }


    private void registerToPusher(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            PLRegister register = applicationContext.findAnnotationOnBean(beanName, PLRegister.class);
            if (register != null) {
                Registry registry = (Registry) applicationContext.getBean(beanName);
                String[] beanNamesForType = applicationContext.getBeanNamesForType(registry.getClass().getAnnotation(PLRegister.class).pusher());
                if (beanNamesForType.length == 0) {
                    logger.info("register {} not match Pusher", beanName);
                    continue;
                }
                if (beanNamesForType.length > 1) {
                    logger.info("register {} The same class bean cannot be greater than 1", beanName);
                    continue;
                }

                Pusher pusher = (Pusher) applicationContext.getBean(beanNamesForType[0]);
                pusher.addRegistry(registry.getRegistryName(),registry);

            }
        }
    }

    private void listenerToRegistry(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            PLEventListener annotationOnBean = applicationContext.findAnnotationOnBean(beanName, PLEventListener.class);
            if (annotationOnBean != null) {
                Listener listener = (Listener) applicationContext.getBean(beanName);
                String[] beanNamesForType = applicationContext.getBeanNamesForType(listener.getClass().getAnnotation(PLEventListener.class).registry());
                if (beanNamesForType.length == 0) {
                    logger.info("listener {} not match Registry", beanName);
                    continue;
                }
                if (beanNamesForType.length > 1) {
                    logger.info("listener {}  The same class bean cannot be greater than 1", beanName);
                    continue;
                }
                Registry registry = (Registry) applicationContext.getBean(beanNamesForType[0]);
                registry.addListener(listener);

            }
        }
    }
}
