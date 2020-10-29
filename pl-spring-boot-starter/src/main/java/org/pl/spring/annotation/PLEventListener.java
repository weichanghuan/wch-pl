package org.pl.spring.annotation;

import org.pl.spring.registry.DefRegistry;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PLEventListener {

    Class<?> registry() default DefRegistry.class;

    String desc() default "";

}
