package me.vene.skilled.utilities;

public class TimerUtil {
  private long lastTime;
  
  public TimerUtil() {
    reset();
  }
  
  public boolean hasReached(float milliseconds) {
    return ((float)(getCurrentMS() - this.lastTime) >= milliseconds);
  }
  
  public boolean hasReached(float lastTime, float milliseconds) {
    return ((float)getCurrentMS() - lastTime >= milliseconds);
  }
  
  public void reset() {
    this.lastTime = getCurrentMS();
  }
  
  public static long getCurrentMS() {
    return System.nanoTime() / 1000000L;
  }
}