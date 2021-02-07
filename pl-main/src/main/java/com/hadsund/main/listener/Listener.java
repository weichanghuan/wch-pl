package com.hadsund.main.listener;

import com.alibaba.fastjson.JSON;
import com.hadsund.main.event.Event;
import com.hadsund.main.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Listener<T extends Event> {

    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    private final Type target;

    public Listener() {
        //  this.target = getClass().getGenericSuperclass().getClass();
        this.target = ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    /**
     * 匹配事件队列或者主题
     *
     * @return
     */
    public abstract String matchEventQueue();

    /**
     * 监听处理
     *
     * @param event
     */
    public abstract void listen(T event);

    /**
     * 匹配
     *
     * @param event
     * @return
     */
    public abstract boolean match(T event);

    /**
     * 处理监听
     *
     * @param event
     */
    public final void handleListen(T event) {
        try {
            if (match(event)) {
                listen(event);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("handleListen is fail listener :{} ,Event :{}, ex:{}", this, JSON.toJSONString(event), ex.getMessage());
        }

    }

    /**
     * 获取事件类型
     *
     * @return
     */
    public Type getEventType() {
        return target;
    }


}
