package ddp.web.controller;

import ddp.beans.test.HelloUserBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "框架测试类",value = "HelloRestController")
@RestController
@RequestMapping("/test")
public class HelloRestController {

  @ApiOperation(value = "hello",notes = "欢迎测试")
  @ApiModelProperty
  @GetMapping("/hello")
  public HelloUserBean hello(@ApiParam(value = "访问人姓名",required =true) @RequestParam("userName") String userName){
    HelloUserBean bean = new HelloUserBean();
    bean.setUserName("国际化"+userName);
    return bean;
  }
}
