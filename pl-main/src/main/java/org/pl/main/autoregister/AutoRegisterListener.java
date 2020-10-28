package org.pl.main.autoregister;

import org.pl.main.annotation.MainEventListener;
import org.pl.main.listener.Listener;
import org.pl.main.registry.Registry;
import org.pl.main.utils.AnnotationUtil;
import org.pl.main.utils.ClassUtil;

import java.util.List;

public class AutoRegisterListener {

    public static void annotationListener(Registry registry, String packageName) {
        // 获取特定包下所有的类(包括接口和类)
        List<Class<?>> clsList = ClassUtil.getAllClassByPackageName(packageName);
        //输出所有使用了特定注解的类的注解值
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
