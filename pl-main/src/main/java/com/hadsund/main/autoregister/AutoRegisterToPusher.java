package com.hadsund.main.autoregister;

import com.hadsund.main.annotation.MainRegister;
import com.hadsund.main.push.Pusher;
import com.hadsund.main.registry.Registry;
import com.hadsund.main.utils.AnnotationUtil;
import com.hadsund.main.utils.ClassUtil;

import java.util.List;

public class AutoRegisterToPusher {

    public static void annotationRegister(Pusher pusher, String packageName) {
        List<Class<?>> clsList = ClassUtil.getAllClassByPackageName(packageName);
        List<Class<?>> classes = AnnotationUtil.getAnnotationClass(clsList, MainRegister.class);
        for (Class<?> aClass : classes) {
            try {
                Registry registry = (Registry) aClass.newInstance();
                pusher.addRegistry(registry.getRegistryName(),registry);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
