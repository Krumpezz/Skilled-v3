package me.vene.skilled.modules.mods.player;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InvMove extends Module {
  private int left = 30;
  
  private int right = 32;
  
  private int backkey = (Minecraft.func_71410_x()).field_71474_y.field_74368_y.func_151463_i();
  
  private int forward = (Minecraft.func_71410_x()).field_71474_y.field_74351_w.func_151463_i();
  
  public InvMove() {
    super(StringRegistry.register("InvMove"), 0, Category.P);
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {}
}