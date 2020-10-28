package org.pl.mq.api;

import java.util.Set;

public interface Producer {

    void publishMessageSyn(String topic, String message) throws Exception;


    Set<String> getTopicList() throws Exception;

}
