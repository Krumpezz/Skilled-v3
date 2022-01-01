package me.vene.skilled.modules.mods.render;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.combat.AntiBot;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Nametags extends Module {
  private Minecraft mc = Minecraft.func_71410_x();
  
  private BooleanValue showHealth = new BooleanValue("Show Health", true);
  
  private BooleanValue showDistance = new BooleanValue("Show Distance", false);
  
  private BooleanValue showInvis = new BooleanValue("Show Invisible", true);
  
  private NumberValue scale = new NumberValue("Scale", 1.0D, 0.1D, 5.0D);
  
  public Nametags() {
    super(StringRegistry.register("Nametags"), 0, Category.R);
    addOption(this.showHealth);
    addOption(this.showInvis);
    addOption(this.showDistance);
  }
  
  @SubscribeEvent
  public void onRenderEvent(RenderLivingEvent.Specials.Pre event) {
    if (event.entity instanceof EntityPlayer && event.entity != this.mc.field_71439_g && event.entity.field_70725_aQ == 0) {
      EntityPlayer entity = (EntityPlayer)event.entity;
      if (AntiBot.bots.contains(entity))
        return; 
      if (!this.showInvis.getState() && entity.func_82150_aj())
        return; 
      event.setCanceled(true);
      String playerName = "";
      if (this.showDistance.getState())
        playerName = playerName + EnumChatFormatting.GREEN.toString() + "[" + EnumChatFormatting.WHITE.toString() + (int)this.mc.field_71439_g.func_70032_d((Entity)entity) + EnumChatFormatting.GREEN.toString() + "] "; 
      playerName = playerName + entity.func_145748_c_().func_150254_d();
      if (this.showHealth.getState()) {
        double health = (entity.func_110143_aJ() / entity.func_110138_aP());
        String healthString = ((health < 0.3D) ? EnumChatFormatting.RED.toString() : ((health < 0.5D) ? EnumChatFormatting.GOLD.toString() : ((health < 0.7D) ? EnumChatFormatting.YELLOW.toString() : EnumChatFormatting.GREEN.toString()))) + rnd(entity.func_110143_aJ(), 1);
        playerName = playerName + " " + healthString;
      } 
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)event.x + 0.0F, (float)event.y + entity.field_70131_O + 0.5F, (float)event.z);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-(this.mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((this.mc.func_175598_ae()).field_78732_j, 1.0F, 0.0F, 0.0F);
      float f1 = 0.02666667F;
      GlStateManager.func_179152_a(-0.02666667F, -0.02666667F, 0.02666667F);
      if (entity.func_70093_af())
        GlStateManager.func_179109_b(0.0F, 9.374999F, 0.0F); 
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      int i = (int)this.scale.getValue();
      int j = (int)((this.mc.field_71466_p.func_78256_a(playerName) / 2) * this.scale.getValue());
      GlStateManager.func_179090_x();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      worldrenderer.func_181662_b((-j - 1), (-1 + i), 0.0D).func_181666_a(entity.func_70093_af() ? 100.0F : 0.0F, 0.0F, 0.0F, 1.0F).func_181675_d();
      worldrenderer.func_181662_b((-j - 1), (8 + i), 0.0D).func_181666_a(entity.func_70093_af() ? 100.0F : 0.0F, 0.0F, 0.0F, 1.0F).func_181675_d();
      worldrenderer.func_181662_b((j + 1), (8 + i), 0.0D).func_181666_a(entity.func_70093_af() ? 100.0F : 0.0F, 0.0F, 0.0F, 1.0F).func_181675_d();
      worldrenderer.func_181662_b((j + 1), (-1 + i), 0.0D).func_181666_a(entity.func_70093_af() ? 100.0F : 0.0F, 0.0F, 0.0F, 1.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      this.mc.field_71466_p.func_78276_b(playerName, (int)((-this.mc.field_71466_p.func_78256_a(playerName) / 2) * this.scale.getValue()), i, -1);
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
    } 
  }
  
  public static double rnd(double n, int d) {
    if (d == 0)
      return Math.round(n); 
    double p = Math.pow(10.0D, d);
    return Math.round(n * p) / p;
  }
}