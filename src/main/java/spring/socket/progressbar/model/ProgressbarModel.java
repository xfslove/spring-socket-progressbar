package spring.socket.progressbar.model;

import org.springframework.messaging.core.MessageSendingOperations;
import spring.socket.progressbar.ProgressbarFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * Created by hanwen on 16/3/11.
 */
public class ProgressbarModel implements Progressbar {

  private final transient ProgressbarFactory progressbarFactory;

  private final transient MessageSendingOperations messageTemplate;

  private final transient ProgressbarBroker broker;

  private final int total;

  private AtomicInteger current = new AtomicInteger(0);

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
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current.get()));
  }

  @Override
  public void increase() {
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current.incrementAndGet()));
  }

  @Override
  public void increase(final int increase) {
    messageTemplate.convertAndSend(getProgressDestination(), new ProgressbarMessage(total, current.updateAndGet(new IntUnaryOperator() {
      @Override
      public int applyAsInt(int operand) {
        return current.get() + increase;
      }
    })));
  }

  @Override
  public void finish() {
    synchronized (current) {
      messageTemplate.convertAndSend(getFinishDestination(), "FINISH");
      progressbarFactory.evict(broker, total);
    }
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
