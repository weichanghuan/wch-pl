package com.hadsund.main.callback;

import com.hadsund.main.event.Event;

public interface EventCallback {
    void onSuccess(Event event);

    void onException(final Throwable e,Event event);
}
