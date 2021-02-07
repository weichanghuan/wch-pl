package com.hadsund.main.event;

import com.hadsund.main.callback.EventCallback;
import com.hadsund.main.callback.impl.DefEventCallback;

/**
 * @Author: wch
 * @Description:
 * @Date: 2020/11/4 2:23 PM
 */
public abstract class AbstractEvent implements Event {

    @Override
    public boolean isAsynHandle() {
        return false;
    }

    @Override
    public String eventTag() {
        return "*";
    }

    @Override
    public EventCallback getEventCallBack() {
        return new DefEventCallback();
    }

    @Override
    public Long getDelayTime() {
        return 0L;
    }

}
