package spring.socket.progressbar;

/**
 * progressbar的代理, 因为一个代理地址对应一个progressbar
 * Created by hanwen on 16/3/13.
 */
public interface ProgressbarBroker {

  /**
   * 得到代理地址
   * @return
   */
  String getBrokerDestination();
}
