/* Confirmed that safewalk will be added in Skilled v4 */

package me.vene.skilled.modules.mods.player;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Safewalk extends Module {
  private Minecraft mc = Minecraft.func_71410_x();
  
  public Safewalk() {
    super(StringRegistry.register("Safewalk"), 0, Category.P);
  }
  
  @SubscribeEvent
  public void p(TickEvent.PlayerTickEvent e) {
    if (!e())
      return; 
    if (this.mc.field_71439_g.field_70122_E)
      if (eob()) {
        sh(d = true);
        c = true;
      } else if (d) {
        sh(d = false);
      }  
    if (c && this.mc.field_71439_g.field_71075_bZ.field_75100_b) {
      sh(false);
      c = false;
    } 
  }
  
  private void sh(boolean sh) {
    KeyBinding.func_74510_a(this.mc.field_71474_y.field_74311_E.func_151463_i(), sh);
  }
  
  public boolean e() {
    return (this.mc.field_71439_g != null && this.mc.field_71441_e != null);
  }
  
  public boolean eob() {
    double x = this.mc.field_71439_g.field_70165_t;
    double y = this.mc.field_71439_g.field_70163_u - 1.0D;
    double z = this.mc.field_71439_g.field_70161_v;
    BlockPos p = new BlockPos(MathHelper.func_76128_c(x), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z));
    return this.mc.field_71441_e.func_175623_d(p);
  }
  
  private static boolean c = false;
  
  private static boolean d = false;
}