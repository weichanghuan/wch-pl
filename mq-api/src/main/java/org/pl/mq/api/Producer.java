package org.pl.mq.api;

import java.util.List;

public interface Producer {

    void publishMessageSyn(String topic, String message) throws Exception;


    List<String> getTopicList() throws Exception;

}
