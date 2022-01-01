package me.vene.skilled.modules.mods.utility;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.NumberValue;

public class Cheststealer extends Module {
  private NumberValue minDelay = new NumberValue(StringRegistry.register("Min Delay"), 75.0D, 0.0D, 200.0D);
  
  private NumberValue maxDelay = new NumberValue(StringRegistry.register("Max Delay"), 125.0D, 0.0D, 200.0D);
  
  private long nextDelay;
  
  protected long lastMS;
  
  public Cheststealer() {
    super(StringRegistry.register("Chest Stealer"), 0, Category.U);
    addValue(this.maxDelay);
    addValue(this.minDelay);
  }
  
  public void newDelay() {
    this.nextDelay = (long)(MathUtil.random.nextFloat() * (this.maxDelay.getValue() - this.minDelay.getValue()) + this.minDelay.getValue());
  }
}