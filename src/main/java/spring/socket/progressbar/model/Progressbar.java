package spring.socket.progressbar.model;

import java.io.Serializable;

/**
 * Created by hanwen on 16/3/13.
 */
public interface Progressbar extends Serializable {

  /**
   * 初始化progressbar
   */
  void init();

  /**
   * progressbar + 1
   */
  void increase();

  /**
   * progressbar + increase
   *
   * @param increase 增加的数量
   */
  void increase(int increase);

  /**
   * 结束progressbar
   */
  void finish();
}
