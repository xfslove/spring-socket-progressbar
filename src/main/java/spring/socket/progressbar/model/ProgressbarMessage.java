package spring.socket.progressbar.model;

import java.io.Serializable;

/**
 * 给client的progressbar消息
 * Created by hanwen on 16/3/12.
 */
public class ProgressbarMessage implements Serializable {

  protected int total;

  protected int current;

  public ProgressbarMessage(int total, int current) {
    this.total = total;
    this.current = current;
  }

  public int getTotal() {
    return total;
  }

  public int getCurrent() {
    return current;
  }
}
