package org.pl.spring.annotation;

import org.pl.main.push.impl.DefPusher;
import org.pl.spring.registry.DefRegistry;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PLRegister {

    Class<?> pusher() default DefPusher.class;

}
