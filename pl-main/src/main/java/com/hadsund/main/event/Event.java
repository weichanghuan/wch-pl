package com.hadsund.main.event;

import com.hadsund.main.callback.EventCallback;

public interface Event {
    /**
     * 应用内
     */
    String EVENTINAPPLICATION = "in application";

    /**
     * 应用间
     */
    String EVENTAPPLICATIONROOM = "application romm";

    /**
     * 获取注册名称
     *
     * @return
     */
    String getRegistryName();

    /**
     * 队列或者主题名称
     *
     * @return
     */
    String eventQueue();

    /**
     * event使用场景
     *
     * @return
     */
    String eventUsed();

    /**
     * 在异步处理
     *
     * @return
     */
    boolean isAsynHandle();

    /**
     * 设置tag
     *
     * @return
     */
    String eventTag();

    /**
     * 获取事件结果回调函数
     *
     * @param eventCallBack
     */
    EventCallback getEventCallBack();

    /**
     * 只适用与进程间的延迟发送
     *
     * @return
     */
    Long getDelayTime();
}
