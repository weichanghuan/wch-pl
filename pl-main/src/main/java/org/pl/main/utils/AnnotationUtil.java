package org.pl.main.utils;


import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;


public class AnnotationUtil {

    public static List<Class<?>>  getAnnotationClass(List<Class<?>> clsList, Class annotationClass) {
        List<Class<?>> list=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(clsList)) {
            for (Class<?> cls : clsList) {
                Annotation annotation = cls.getAnnotation(annotationClass);
                if (annotation != null) {
                    list.add(cls);
                }
            }
        }
        return list;
    }

}
