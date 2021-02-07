package com.hadsund.main.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MainRegister {

    /**
     * 发布者类型
     * @return
     */
    Class<?> pusher();

    /**
     * 描述
     * @return
     */
    String desc()default "";

}
