package ddp.web.configure;

import ddp.web.client.mtqq.MqttPushClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Autowired
    private MqttPushClient mqttPushClient;

    @Value("${mtqq.username}")
    private String username;

    @Value("${mtqq.password}")
    private String password;

    @Value("${mtqq.host-url}")
    private String hostUrl;

    @Value("${mtqq.client-id}")
    private String clientId;

    @Value("${mtqq.default-topic}")
    private String defaultTopic;

    @Value("${mtqq.qos}")
    private Integer qos;

    @Value("${mtqq.timeout}")
    private Integer timeout;

    @Value("${mtqq.keepalive}")
    private Integer keepalive;

    @Bean
    public MqttPushClient getMqttPushClient(){
        mqttPushClient.connect(hostUrl,clientId,username,password,timeout,keepalive);
        mqttPushClient.subscribe(defaultTopic + "/#",qos);
        return mqttPushClient;
    }
}
