package ddp.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloRestController {

  @RequestMapping("/hello")
  public String hello(){
    return "国际化";
  }
}
