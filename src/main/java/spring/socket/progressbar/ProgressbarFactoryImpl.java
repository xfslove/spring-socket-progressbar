package spring.socket.progressbar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;
import spring.socket.progressbar.model.ProgressbarModel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by hanwen on 16/3/11.
 */
@Component
public class ProgressbarFactoryImpl implements ProgressbarFactory {

  private MessageSendingOperations messageTemplate;

  private final ConcurrentMap<SimpleKey, ProgressbarModel> barCache = new ConcurrentHashMap<SimpleKey, ProgressbarModel>(256);

  @Override
  public ProgressbarModel get(ProgressbarBroker broker, int total) {
    SimpleKey simpleKey = new SimpleKey(broker);
    if (barCache.containsKey(simpleKey)) {
      return barCache.get(broker);
    }
    ProgressbarModel progressbar = new ProgressbarModel(this, messageTemplate, broker, total);
    barCache.put(simpleKey, progressbar);
    return progressbar;
  }

  @Override
  public void evict(ProgressbarBroker broker, int total) {
    barCache.remove(new SimpleKey(broker));
  }

  @Autowired
  public void setMessageTemplate(MessageSendingOperations messageTemplate) {
    this.messageTemplate = messageTemplate;
  }
}
