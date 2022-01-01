package me.vene.skilled.modules.mods.render;

import java.lang.reflect.Field;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.combat.AntiBot;
import me.vene.skilled.utilities.StringRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {
  private boolean bobbing;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public Tracers() {
    super("Tracers", 0, Category.R);
  }
  
  public void onEnable() {
    this.bobbing = this.mc.field_71474_y.field_74336_f;
    this.mc.field_71474_y.field_74336_f = false;
  }
  
  public void onDisable() {
    this.mc.field_71474_y.field_74336_f = this.bobbing;
  }
  
  public void onRenderEvent(RenderWorldLastEvent event) {
    if (this.mc.field_71474_y.field_74336_f)
      this.mc.field_71474_y.field_74336_f = false; 
    EntityPlayerSP player = this.mc.field_71439_g;
    GL11.glPushMatrix();
    GlStateManager.func_179147_l();
    GlStateManager.func_179097_i();
    GlStateManager.func_179090_x();
    GlStateManager.func_179140_f();
    GL11.glEnable(2848);
    GlStateManager.func_179112_b(770, 771);
    GL11.glLineWidth(1.0F);
    for (EntityPlayer p : this.mc.field_71441_e.field_73010_i) {
      if (AntiBot.bots.contains(p))
        return; 
      if (p.equals(player))
        continue; 
      if (p.field_70128_L)
        continue; 
      String Xstring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '5', '_', 'b' });
      String Ystring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '6', '_', 'c' });
      String Zstring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '3', '_', 'd' });
      double renderPosX = ((Double)GetFieldByReflection(RenderManager.class, this.mc.func_175598_ae(), new String[] { StringRegistry.register(Xstring) })).doubleValue();
      double renderPosY = ((Double)GetFieldByReflection(RenderManager.class, this.mc.func_175598_ae(), new String[] { StringRegistry.register(Ystring) })).doubleValue();
      double renderPosZ = ((Double)GetFieldByReflection(RenderManager.class, this.mc.func_175598_ae(), new String[] { StringRegistry.register(Zstring) })).doubleValue();
      double x = p.field_70142_S + (p.field_70165_t - p.field_70142_S) * event.partialTicks - renderPosX;
      double y = p.field_70137_T + (p.field_70163_u - p.field_70137_T) * event.partialTicks - renderPosY + 1.0D;
      double z = p.field_70136_U + (p.field_70161_v - p.field_70136_U) * event.partialTicks - renderPosZ;
      GlStateManager.func_179131_c(1.0F, 0.0F, 0.0F, 1.0F);
      GL11.glBegin(1);
      GL11.glVertex3d(0.0D, player.func_70047_e(), 0.0D);
      GL11.glVertex3d(x, y, z);
      GL11.glEnd();
    } 
    GlStateManager.func_179084_k();
    GlStateManager.func_179126_j();
    GlStateManager.func_179098_w();
    GlStateManager.func_179145_e();
    GL11.glDisable(2848);
    GL11.glLineWidth(1.0F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
  }
  
  public static <T, E> T GetFieldByReflection(Class<? super E> classToAccess, E instance, String... fieldNames) {
    Field field = null;
    for (String fieldName : fieldNames) {
      try {
        field = classToAccess.getDeclaredField(fieldName);
      } catch (NoSuchFieldException noSuchFieldException) {}
      if (field != null)
        break; 
    } 
    if (field != null) {
      field.setAccessible(true);
      T fieldT = null;
      try {
        fieldT = (T)field.get(instance);
      } catch (IllegalArgumentException illegalArgumentException) {
      
      } catch (IllegalAccessException illegalAccessException) {}
      return fieldT;
    } 
    return null;
  }
}