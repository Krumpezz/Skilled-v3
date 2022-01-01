package me.vene.skilled.modules.mods.utility;

import java.awt.AWTException;
import java.awt.Robot;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.utilities.TimerUtil;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ThrowPot extends Module {
  private int currentItem;
  
  private int vl;
  
  private int stage;
  
  private TimerUtil timer;
  
  private NumberValue Delay;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public ThrowPot() {
    super(StringRegistry.register("ThrowPot"), 0, Category.U);
    this.currentItem = 0;
    this.vl = 0;
    this.stage = 0;
    this.timer = new TimerUtil();
    addValue(this.Delay = new NumberValue(StringRegistry.register("Delay"), 50.0D, 25.0D, 1000.0D));
  }
  
  public void onEnable() {
    this.currentItem = this.mc.field_71439_g.field_71071_by.field_70461_c;
    if (this.mc.field_71439_g.func_110143_aJ() <= 6.0F) {
      this.vl = 2;
    } else {
      if (this.mc.field_71439_g.func_110143_aJ() > 12.0F) {
        toggle();
        return;
      } 
      this.vl = 1;
    } 
    if (switchItem()) {
      this.stage = 1;
    } else {
      toggle();
    } 
  }
  
  public void onDisable() {
    this.stage = 0;
    this.vl = 0;
    this.mc.field_71439_g.field_71071_by.field_70461_c = this.currentItem;
  }
  
  private boolean isPot(ItemStack stack) {
    if (stack != null && stack.func_77973_b() == Items.field_151068_bn && ItemPotion.func_77831_g(stack.func_77952_i()))
      for (Object o : ((ItemPotion)stack.func_77973_b()).func_77832_l(stack)) {
        if (((PotionEffect)o).func_76456_a() == Potion.field_76432_h.field_76415_H)
          return true; 
      }  
    return false;
  }
  
  private boolean isValidItem(ItemStack stack) {
    return isPot(stack);
  }
  
  private boolean switchItem() {
    for (int i = 0; i < 9; i++) {
      ItemStack stack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
      if (isValidItem(stack)) {
        this.mc.field_71439_g.field_71071_by.field_70461_c = i;
        return true;
      } 
    } 
    return false;
  }
  
  private void count() {
    this.vl--;
    if (this.vl > 0) {
      if (switchItem()) {
        this.stage = 1;
      } else {
        toggle();
      } 
    } else {
      toggle();
    } 
  }
  
  public void onClientTick(TickEvent.ClientTickEvent e) {
    if (this.stage == 1 && this.timer.hasReached((float)this.Delay.getValue())) {
      if (!isValidItem(this.mc.field_71439_g.field_71071_by.func_70448_g())) {
        toggle();
        return;
      } 
      Robot bot = null;
      try {
        bot = new Robot();
      } catch (AWTException aWTException) {}
      bot.mousePress(4);
      bot.mouseRelease(4);
      this.stage = 2;
      this.timer.reset();
    } 
    if (this.stage == 2 && this.timer.hasReached((float)this.Delay.getValue())) {
      count();
      this.timer.reset();
    } else if (this.stage == 3 && this.timer.hasReached((float)this.Delay.getValue())) {
      count();
      this.timer.reset();
    } 
  }
}