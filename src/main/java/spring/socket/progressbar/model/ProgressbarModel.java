package spring.socket.progressbar.model;

import org.springframework.messaging.core.MessageSendingOperations;
import spring.socket.progressbar.ProgressbarBroker;
import spring.socket.progressbar.ProgressbarFactory;

/**
 * Created by hanwen on 16/3/11.
 */
public class ProgressbarModel implements Progressbar {

  private final ProgressbarFactory progressbarFactory;

  private final MessageSendingOperations messageTemplate;

  private final ProgressbarBroker broker;

  private final int total;

  private int current = 0;

  public ProgressbarModel(
      ProgressbarFactory progressbarFactory,
      MessageSendingOperations messageTemplate,
      ProgressbarBroker broker,
      int total
  ) {
    this.progressbarFactory = progressbarFactory;
    this.messageTemplate = messageTemplate;
    this.broker = broker;
    this.total = total;
  }

  @Override
  public void init() {
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current));
  }

  @Override
  public void increase() {
    current++;
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current));
  }

  @Override
  public void increase(int increase) {
    current += increase;
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current));
  }

  @Override
  public void finish() {
    messageTemplate.convertAndSend(getFinishDestination(), "FINISH");
    progressbarFactory.evict(broker, total);
  }

  private String getProgressDestination() {
    return "/queue/" + broker.getBrokerDestination() + "/progress";
  }

  private String getFinishDestination() {
    return "/queue/" + broker.getBrokerDestination() + "/finish";
  }
}
