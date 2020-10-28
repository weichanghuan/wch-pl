package org.pl.main.autoregister;

import org.pl.main.annotation.MainRegister;
import org.pl.main.push.Pusher;
import org.pl.main.registry.Registry;
import org.pl.main.utils.AnnotationUtil;
import org.pl.main.utils.ClassUtil;

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
