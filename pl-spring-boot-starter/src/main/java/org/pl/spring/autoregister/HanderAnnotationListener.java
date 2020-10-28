package org.pl.spring.autoregister;


import org.pl.main.listener.Listener;
import org.pl.main.push.Pusher;
import org.pl.main.registry.Registry;
import org.pl.spring.annotation.PLEventListener;
import org.pl.spring.annotation.PLRegister;
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
                Registry registry = (Registry) applicationContext.getBean(beanNamesForType[0]);
                registry.addListener(listener);

            }
        }
    }
}
