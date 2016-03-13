package spring.socket.progressbar;

/**
 * Created by hanwen on 16/3/13.
 */
public class DefaultProgressbarBroker implements ProgressbarBroker {

  protected String module;

  public DefaultProgressbarBroker(String module) {
    this.module = module;
  }

  public String getModule() {
    return module;
  }

  @Override
  public String getBrokerDestination() {
    return module;
  }

}
