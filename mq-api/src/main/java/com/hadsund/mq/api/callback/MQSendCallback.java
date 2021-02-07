package com.hadsund.mq.api.callback;

public interface MQSendCallback {
    void onSuccess(String message);

    void onException(final Throwable e, String message);
}
