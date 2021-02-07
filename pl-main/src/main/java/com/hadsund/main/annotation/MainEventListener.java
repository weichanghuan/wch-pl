package com.hadsund.main.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MainEventListener {

    /**
     * 注册类型
     * @return
     */
    Class<?> registry();

    /**
     * 描述监听者
     * @return
     */
    String desc()default "";

}
