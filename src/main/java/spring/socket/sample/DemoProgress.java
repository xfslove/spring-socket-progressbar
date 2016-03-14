package spring.socket.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.socket.progressbar.ProgressbarFactory;
import spring.socket.progressbar.model.DefaultProgressbarBroker;
import spring.socket.progressbar.model.Progressbar;
import spring.socket.progressbar.model.ProgressbarBroker;

/**
 * Created by hanwen on 16/3/12.
 */
@Component
public class DemoProgress {

  @Autowired
  protected ProgressbarFactory progressbarFactory;

  public void progress() throws Exception {

    ProgressbarBroker broker = new DefaultProgressbarBroker("DEMO");

    Progressbar bar = progressbarFactory.get(broker, 100);

    // 一些执行时间长的代码
    bar.init();

    for (int i = 0; i < 100; i++) {
      Thread.sleep(100);
      bar.increase();
    }

    bar.finish();

  }

}
