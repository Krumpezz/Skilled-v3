package me.vene.skilled.modules.mods.other;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChatSpammer extends Module {
  protected long lastMS;
  
  private boolean hasTimePassedMS(double MS) {
    return (getCurrentMS() >= this.lastMS + MS);
  }
  
  private long getCurrentMS() {
    return System.nanoTime() / 1000000L;
  }
  
  private void updatebefore() {
    this.lastMS = getCurrentMS();
  }
  
  private NumberValue delay = new NumberValue("Delay", 5.0D, 0.0D, 60.0D);
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public void onEnable() {
    updatebefore();
  }
  
  public ChatSpammer() {
    super(StringRegistry.register("Chat Spammer"), 0, Category.O);
    addValue(this.delay);
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (this.mc.field_71439_g != null || this.mc.field_71441_e != null)
      if (hasTimePassedMS(this.delay.getValue() * 1000.0D))
        this.mc.field_71439_g.func_71165_d("");  
  }
}