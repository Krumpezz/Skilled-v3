package me.vene.skilled.values;

import java.math.BigDecimal;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.StringRegistry;

public class NumberValue {
  private String name;
  
  private double value;
  
  private double max;
  
  private double min;
  
  public NumberValue(String name, double value, double min, double max) {
    this.name = StringRegistry.register(name);
    this.value = value;
    this.max = max;
    this.min = min;
  }
  
  public String getName() {
    return StringRegistry.register(this.name);
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setValue(double newValue) {
    this.value = MathUtil.clamp(newValue, this.min, this.max);
    if (newValue < getMin())
      this.value = getMin(); 
  }
  
  public double getValue() {
    return round(this.value, 2);
  }
  
  public double getMin() {
    return this.min;
  }
  
  public double getMax() {
    return this.max;
  }
  
  public void increase() {
    if (round(this.value, 2) < this.max)
      this.value++; 
  }
  
  public void decrease() {
    if (round(this.value, 2) > this.min)
      this.value--; 
  }
  
  private double round(double doubleValue, int numOfDecimals) {
    BigDecimal bigDecimal = new BigDecimal(doubleValue);
    bigDecimal = bigDecimal.setScale(numOfDecimals, 4);
    return bigDecimal.doubleValue();
  }
}