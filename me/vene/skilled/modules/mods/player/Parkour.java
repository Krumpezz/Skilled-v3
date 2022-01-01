package me.vene.skilled.modules.mods.player;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Parkour extends Module {
  private BlockPos currentBlock;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private static boolean jumpDown = false;
  
  public Parkour() {
    super(StringRegistry.register("Parkour"), 0, Category.P);
  }
  
  @SubscribeEvent
  public void onKeyPress(InputEvent.KeyInputEvent event) {
    if (Keyboard.isKeyDown(this.mc.field_71474_y.field_74314_A.func_151463_i()) && Minecraft.func_71410_x() != null && (Minecraft.func_71410_x()).field_71439_g != null && (Minecraft.func_71410_x()).field_71441_e != null) {
      jumpDown = true;
    } else {
      jumpDown = false;
    } 
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (this.mc.field_71441_e != null) {
      if (this.mc.field_71439_g.field_70167_r - this.mc.field_71439_g.field_70163_u > 0.4D)
        return; 
      int jumpKey = this.mc.field_71474_y.field_74314_A.func_151463_i();
      KeyBinding.func_74510_a(jumpKey, false);
      KeyBinding.func_74507_a(jumpKey);
      double blockX = this.mc.field_71439_g.field_70165_t;
      double blockY = this.mc.field_71439_g.field_70163_u - 1.0D;
      double blockZ = this.mc.field_71439_g.field_70161_v;
      BlockPos thisBlock = new BlockPos(MathHelper.func_76128_c(blockX), MathHelper.func_76128_c(blockY), MathHelper.func_76128_c(blockZ));
      if (this.currentBlock == null || !isSameBlock(thisBlock, this.currentBlock))
        this.currentBlock = thisBlock; 
      if (this.mc.field_71441_e.func_175623_d(this.currentBlock) && 
        this.mc.field_71439_g.field_70122_E && !this.mc.field_71439_g.func_70093_af() && !jumpDown) {
        KeyBinding.func_74510_a(jumpKey, true);
        KeyBinding.func_74507_a(jumpKey);
      } 
    } 
  }
  
  private boolean isSameBlock(BlockPos pos1, BlockPos pos2) {
    return (pos1.func_177958_n() == pos2.func_177958_n() && pos1.func_177956_o() == pos2.func_177956_o() && pos1.func_177952_p() == pos2.func_177952_p());
  }
}