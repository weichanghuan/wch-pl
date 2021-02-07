package com.hadsund.main.autoregister;

import com.hadsund.main.annotation.MainEventListener;
import com.hadsund.main.listener.Listener;
import com.hadsund.main.registry.Registry;
import com.hadsund.main.utils.AnnotationUtil;
import com.hadsund.main.utils.ClassUtil;

import java.util.List;

public class AutoListenerToRegister {

    public static void annotationListener(Registry registry, String packageName) {
        List<Class<?>> clsList = ClassUtil.getAllClassByPackageName(packageName);
        List<Class<?>> classes = AnnotationUtil.getAnnotationClass(clsList, MainEventListener.class);
        for (Class<?> aClass : classes) {
            try {
                registry.addListener((Listener) aClass.newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
