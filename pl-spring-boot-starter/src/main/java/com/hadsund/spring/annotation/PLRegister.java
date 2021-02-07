package com.hadsund.spring.annotation;

import com.hadsund.main.push.impl.DefPusher;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PLRegister {

    Class<?> pusher() default DefPusher.class;

    String desc() default "";

}
