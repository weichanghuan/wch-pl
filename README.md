"# wch-pl" 
pl:push and listener
# introduce
## 介绍jar 
### pl-main 是公共api和一些默认实现
#### pl-spring-boot-starter 是集成spring
### mq-api 是mq的api
### mq-rocket 是rocketmq的实现
### pl-main-test 是pl-main的测试
### pl-spring-boot-starter 是pl-spring-boot-starter的测试

# QuickStart

## 准备
### add pom
    <dependency>
      <groupId>com.hadsund</groupId>
      <artifactId>pl-spring-boot-starter</artifactId>
      <version>0.1.0</version>
    </dependency>
### 定义listener
@PLEventListener(registry = DefRegistry.class)  
public class TransferListener extends Listener<TransferEvent<String>> {

    public String matchEventQueue() {
        return "transfer";
    }

    public void listen(TransferEvent<String> event) {
        System.out.println(event.getContent());
    }

    public boolean match(TransferEvent<String> event) {
        if (matchEventQueue().equals(event.eventQueue())) {
            return true;
        }
        return false;
    }
}

### 定义event
public class TransferEvent<T> implements Event{

    public String getRegistryName() {
        return "defRegistry";
    }

    public String eventQueue() {
        return "transfer";
    }

    public String eventUsed() {
        return EVENTINAPPLICATION;
    }

    public boolean isAsynHandle() {
        return false;
    }

    private String eventId;

    private String eventType;

    private T content;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

### 使用默认发送者
#### 注入
    @Autowired
    @Qualifier("defPusher")
    private Pusher pusher;

#### 发送demo
        TransferEvent<String> event = new TransferEvent<>();
        event.setEventId("eventId");
        event.setEventType("eventType");
        event.setContent("转账给XXXX30亿");
        pusher.synSend(event);
        
#### 配置
  `hkposs:
    rocketmq:
      name-server: 172.31.10.20:9876;172.31.10.217:9876`        

#### 友情提醒：注意spring扫包注入，若自定义扫包路径，请把listener包也扫描掉，交由spring管理



# 进阶使用，请看源码