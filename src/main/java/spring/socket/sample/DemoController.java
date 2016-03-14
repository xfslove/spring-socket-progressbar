package spring.socket.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hanwen on 16/3/14.
 */
@Controller
public class DemoController {

  @Autowired
  protected DemoProgress demoProgress;

  @RequestMapping("/demo")
  @ResponseBody
  public void demo() throws Exception {
    demoProgress.progress();
  }
}
