package me.vene.skilled.modules.mods.combat;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BlockVelocity extends Module {
  private BooleanValue horizontalBlocks = new BooleanValue(StringRegistry.register("Horizontal Blocks"), true);
  
  private BooleanValue verticalBlocks = new BooleanValue(StringRegistry.register("Vertical Blocks"), false);
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private BlockPos firstBlock = null;
  
  private BlockPos secondBlock = null;
  
  private BlockPos thirdBlock = null;
  
  private BlockPos fourthBlock = null;
  
  private BlockPos fifthBlock = null;
  
  public BlockVelocity() {
    super(StringRegistry.register("BlockVelocity"), 0, Category.C);
    addOption(this.horizontalBlocks);
    addOption(this.verticalBlocks);
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if ((Minecraft.func_71410_x()).field_71439_g == null)
      return; 
    if ((Minecraft.func_71410_x()).field_71441_e == null)
      return; 
    if ((Minecraft.func_71410_x()).field_71439_g.field_70737_aN == (Minecraft.func_71410_x()).field_71439_g.field_70738_aO && (Minecraft.func_71410_x()).field_71439_g.field_70738_aO > 0) {
      double x = this.mc.field_71439_g.field_70165_t;
      double y = this.mc.field_71439_g.field_70163_u;
      double z = this.mc.field_71439_g.field_70161_v;
      this.firstBlock = new BlockPos(MathHelper.func_76128_c(x + 1.0D), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z));
      this.secondBlock = new BlockPos(MathHelper.func_76128_c(x - 1.0D), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z));
      this.thirdBlock = new BlockPos(MathHelper.func_76128_c(x), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z + 1.0D));
      this.fourthBlock = new BlockPos(MathHelper.func_76128_c(x), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z - 1.0D));
      this.fifthBlock = new BlockPos(MathHelper.func_76128_c(x), MathHelper.func_76128_c(y + 2.0D), MathHelper.func_76128_c(z));
      if (this.horizontalBlocks.getState()) {
        this.mc.field_71441_e.func_175656_a(this.firstBlock, Blocks.field_150484_ah.func_176223_P());
        this.mc.field_71441_e.func_175656_a(this.secondBlock, Blocks.field_150484_ah.func_176223_P());
        this.mc.field_71441_e.func_175656_a(this.thirdBlock, Blocks.field_150484_ah.func_176223_P());
        this.mc.field_71441_e.func_175656_a(this.fourthBlock, Blocks.field_150484_ah.func_176223_P());
      } 
      if (this.verticalBlocks.getState())
        this.mc.field_71441_e.func_175656_a(this.fifthBlock, Blocks.field_150484_ah.func_176223_P()); 
    } else if (this.firstBlock != null) {
      this.mc.field_71441_e.func_175656_a(this.firstBlock, Blocks.field_150350_a.func_176223_P());
      this.mc.field_71441_e.func_175656_a(this.secondBlock, Blocks.field_150350_a.func_176223_P());
      this.mc.field_71441_e.func_175656_a(this.thirdBlock, Blocks.field_150350_a.func_176223_P());
      this.mc.field_71441_e.func_175656_a(this.fourthBlock, Blocks.field_150350_a.func_176223_P());
      this.mc.field_71441_e.func_175656_a(this.fifthBlock, Blocks.field_150350_a.func_176223_P());
    } 
  }
}