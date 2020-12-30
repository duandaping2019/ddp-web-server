package ddp.web.controller.test;

import ddp.beans.BaseResponse;
import ddp.service.tools.MessageSourceUtils;
import ddp.web.client.mtqq.MqttIBean;
import ddp.web.client.mtqq.MqttPushClient;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/mqtt")
public class MqttRestController {

    @Autowired
    private MqttPushClient mqttPushClient;

    @Value("${mtqq.default-topic}")
    private String defaultTopic;

    @Value("${mtqq.qos}")
    private Integer qos;

    @RequestMapping(value = "/publishTopic")
    public BaseResponse<Object> publishTopic(@ApiParam(value = "消息内容对象", required = true) @RequestBody MqttIBean mqttIBean,
                                             @ApiParam(value = "语言请求参数", required = false) Locale locale){
        mqttPushClient.publish(qos,false,defaultTopic + "/test", mqttIBean.getMsg());
        return BaseResponse.success(MessageSourceUtils.getSourceFromCache("opt_succ", locale), "发布成功");
    }



}
