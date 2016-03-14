package spring.socket.progressbar;

import spring.socket.progressbar.model.Progressbar;

/**
 * Created by hanwen on 16/3/11.
 */
public interface ProgressbarFactory {

  /**
   * 得到一个progressbar实例并cache, 一个代理对应一个progressbar
   *
   * @param broker progressbar的代理
   * @param total  progressbar总数
   * @return
   */
  Progressbar get(ProgressbarBroker broker, int total);

  /**
   * 从cache中evict一个progressbar实例
   *
   * @param broker
   * @param total
   */
  void evict(ProgressbarBroker broker, int total);

}
