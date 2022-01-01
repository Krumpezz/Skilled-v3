package me.vene.skilled.modules.mods.player;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SpeedBridge extends Module {
  private boolean sneaking = false;
  
  private boolean safewalkSneaking = false;
  
  private boolean playerIsSneaking = false;
  
  private BlockPos currentBlock;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private BooleanValue onlyBlocks = new BooleanValue(StringRegistry.register("Only Blocks"), false);
  
  private BlockPos lastBlock;
  
  public SpeedBridge() {
    super(StringRegistry.register("Speed Bridge"), 0, Category.P);
    addOption(this.onlyBlocks);
  }
  
  public void onDisable() {
    KeyBinding.func_74510_a(this.mc.field_71474_y.field_74311_E.func_151463_i(), false);
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (this.mc.field_71441_e != null) {
      if (this.mc.field_71439_g.field_70167_r - this.mc.field_71439_g.field_70163_u > 0.4D)
        return; 
      if (this.mc.field_71439_g.field_71075_bZ.field_75100_b)
        return; 
      if (this.onlyBlocks.getState() && (
        this.mc.field_71439_g.func_71045_bC() == null || !(this.mc.field_71439_g.func_71045_bC().func_77973_b() instanceof net.minecraft.item.ItemBlock)))
        return; 
      double blockX = this.mc.field_71439_g.field_70165_t;
      double blockY = this.mc.field_71439_g.field_70163_u - 1.0D;
      double blockZ = this.mc.field_71439_g.field_70161_v;
      BlockPos thisBlock = new BlockPos(MathHelper.func_76128_c(blockX), MathHelper.func_76128_c(blockY), MathHelper.func_76128_c(blockZ));
      if (this.currentBlock == null || !isSameBlock(thisBlock, this.currentBlock))
        this.currentBlock = thisBlock; 
      if (this.mc.field_71441_e.func_175623_d(this.currentBlock)) {
        setSafewalkSneaking(true);
      } else {
        setSafewalkSneaking(false);
      } 
      checkSneak();
    } 
  }
  
  private void doSafewalk() {
    if (isSneaking() != this.mc.field_71439_g.func_70093_af())
      KeyBinding.func_74510_a(this.mc.field_71474_y.field_74311_E.func_151463_i(), isSneaking()); 
  }
  
  private boolean isSneaking() {
    return this.sneaking;
  }
  
  private boolean isSameBlock(BlockPos pos1, BlockPos pos2) {
    return (pos1.func_177958_n() == pos2.func_177958_n() && pos1.func_177956_o() == pos2.func_177956_o() && pos1.func_177952_p() == pos2.func_177952_p());
  }
  
  public void setSafewalkSneaking(boolean safewalkSneaking) {
    this.safewalkSneaking = safewalkSneaking;
  }
  
  private void checkSneak() {
    if (!this.playerIsSneaking) {
      if (this.mc.field_71439_g.field_70122_E) {
        this.sneaking = this.safewalkSneaking;
        doSafewalk();
      } else {
        KeyBinding.func_74510_a(this.mc.field_71474_y.field_74311_E.func_151463_i(), false);
      } 
    } else {
      KeyBinding.func_74510_a(this.mc.field_71474_y.field_74311_E.func_151463_i(), true);
    } 
  }
  
  public void setFakedSneakingState(boolean sneaking) {
    if (this.mc.field_71439_g != null) {
      C0BPacketEntityAction.Action action = sneaking ? C0BPacketEntityAction.Action.START_SNEAKING : C0BPacketEntityAction.Action.STOP_SNEAKING;
      this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)this.mc.field_71439_g, action));
      this.mc.field_71439_g.field_71158_b.field_78899_d = sneaking;
    } 
  }
}