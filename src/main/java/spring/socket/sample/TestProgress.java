package spring.socket.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spring.socket.progressbar.ProgressbarBroker;
import spring.socket.progressbar.model.ProgressbarModel;
import spring.socket.progressbar.DefaultProgressbarBroker;
import spring.socket.progressbar.ProgressbarFactory;

/**
 * Created by hanwen on 16/3/12.
 */
@Component
public class TestProgress {

  @Autowired
  protected ProgressbarFactory progressbarFactory;

  @Scheduled(fixedDelay = 1000 * 60 * 30)
  public void testProgress() throws InterruptedException {

    ProgressbarBroker identify = new DefaultProgressbarBroker("TEST");

    ProgressbarModel bar = progressbarFactory.get(identify, 100);

    bar.init();

    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);
      bar.increase();
    }

    bar.finish();

  }

}
