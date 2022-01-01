package me.vene.skilled.modules.mods.player;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fly extends Module {
  private Minecraft mc = Minecraft.func_71410_x();
  
  private NumberValue speed = new NumberValue("Speed", 3.0D, 1.0D, 10.0D);
  
  public Fly() {
    super("Fly", 0, Category.P);
    addValue(this.speed);
  }
  
  public void onDisable() {
    if (this.mc.field_71439_g.field_71075_bZ.field_75100_b)
      this.mc.field_71439_g.field_71075_bZ.field_75100_b = false; 
    this.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05F);
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    this.mc.field_71439_g.field_70181_x = 0.0D;
    this.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05F * (float)this.speed.getValue());
    this.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
  }
}