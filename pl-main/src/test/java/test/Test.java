package test;

import org.pl.main.annotation.MainEventListener;
import org.pl.main.listener.Listener;
import org.pl.main.push.Pusher;
import org.pl.main.push.impl.DefPusher;
import org.pl.main.registry.Registry;
import org.pl.main.utils.AnnotationUtil;
import org.pl.main.utils.ClassUtil;

import java.util.List;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/10/28 1:04 AM
 */
public class Test {

    public static void main(String[] args) throws Exception {
        // 容器上下文

        // 定义注册中心
        Registry registry = new TransactionRegistry();

        // 获取特定包下所有的类(包括接口和类)
        List<Class<?>> clsList = ClassUtil.getAllClassByPackageName("test");
        //输出所有使用了特定注解的类的注解值
        List<Class<?>> classes = AnnotationUtil.getAnnotationClass(clsList, MainEventListener.class);

        for (Class<?> aClass : classes) {
            registry.addListener((Listener) aClass.newInstance());
        }

        // 定义event
        TransferEvent<String> event = new TransferEvent<>();
        event.setEventId("eventId");
        event.setEventType("eventType");
        event.setContent("转账给危常焕30亿");

        // 发送
        Pusher pusher = new DefPusher();
        pusher.addRegistry("付款注册中心", registry);
        pusher.synSend(event);


    }
}
