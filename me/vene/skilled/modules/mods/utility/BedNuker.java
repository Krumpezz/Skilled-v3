package me.vene.skilled.modules.mods.utility;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BedNuker extends Module {
  private BlockPos m;
  
  private final long per = 600L;
  
  private NumberValue range = new NumberValue("Range", 6.0D, 1.0D, 6.0D);
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private static int ticks;
  
  public BedNuker() {
    super(StringRegistry.register("BedNuker"), 0, Category.U);
    addValue(this.range);
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    ticks++;
    if (ticks % 20 == 0 && this.mc.field_71439_g != null && this.mc.field_71441_e != null) {
      ticks = 0;
      for (int y = (int)this.range.getValue(), ra = y; y >= -ra; y--) {
        for (int x = -ra; x <= ra; x++) {
          for (int z = -ra; z <= ra; z++) {
            BlockPos p = new BlockPos(this.mc.field_71439_g.field_70165_t + x, this.mc.field_71439_g.field_70163_u + y, this.mc.field_71439_g.field_70161_v + z);
            boolean bed = (this.mc.field_71441_e.func_180495_p(p).func_177230_c() == Blocks.field_150324_C);
            if (this.m == p) {
              if (!bed)
                this.m = null; 
            } else if (bed) {
              breakBlock(p);
              this.m = p;
              break;
            } 
          } 
        } 
      } 
    } 
  }
  
  private void breakBlock(BlockPos p) {
    this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p, EnumFacing.NORTH));
    this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p, EnumFacing.NORTH));
  }
}