package spring.socket.progressbar.model;

import org.springframework.messaging.core.MessageSendingOperations;
import spring.socket.progressbar.ProgressbarFactory;

/**
 * Created by hanwen on 16/3/11.
 */
public class ProgressbarModel implements Progressbar {

  private final ProgressbarFactory progressbarFactory;

  private final MessageSendingOperations messageTemplate;

  private final ProgressbarBroker broker;

  private final int total;

  private volatile int current = 0;

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
  public synchronized void init() {
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current));
  }

  @Override
  public synchronized void increase() {
    current++;
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current));
  }

  @Override
  public synchronized void increase(int increase) {
    current += increase;
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current));
  }

  @Override
  public synchronized void finish() {
    messageTemplate.convertAndSend(getFinishDestination(), "FINISH");
    progressbarFactory.evict(broker, total);
  }

  private String getProgressDestination() {
    String destination = broker.getBrokerDestination();
    if (destination.startsWith("/")) {
      destination = destination.substring(1, destination.length());
    }
    if (destination.endsWith("/")) {
      destination = destination.substring(destination.length() - 1, destination.length());
    }
    return "/queue/" + destination + "/progress";
  }

  private String getFinishDestination() {
    String destination = broker.getBrokerDestination();
    if (destination.startsWith("/")) {
      destination = destination.substring(1, destination.length());
    }
    if (destination.endsWith("/")) {
      destination = destination.substring(destination.length() - 1, destination.length());
    }
    return "/queue/" + destination + "/finish";
  }
}
