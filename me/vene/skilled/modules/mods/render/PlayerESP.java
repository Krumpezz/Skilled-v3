package me.vene.skilled.modules.mods.render;

import java.lang.reflect.Field;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.combat.AntiBot;
import me.vene.skilled.modules.mods.other.ClickGUI;
import me.vene.skilled.utilities.RenderUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class PlayerESP extends Module {
  private Minecraft mc = Minecraft.func_71410_x();
  
  private NumberValue redful;
  
  private NumberValue greenful;
  
  private NumberValue blueful;
  
  private BooleanValue showInvis = new BooleanValue("Show Invisible", true);
  
  public PlayerESP() {
    super(StringRegistry.register("Player ESP"), 0, Category.R);
    addOption(this.showInvis);
    addValue(this.redful = new NumberValue(StringRegistry.register(new String(new char[] { 'R', 'e', 'd' }, )), 35.0D, 1.0D, 255.0D));
    addValue(this.greenful = new NumberValue(StringRegistry.register(new String(new char[] { 'G', 'r', 'e', 'e', 'n' }, )), 245.0D, 1.0D, 255.0D));
    addValue(this.blueful = new NumberValue(StringRegistry.register(new String(new char[] { 'B', 'l', 'u', 'e' }, )), 15.0D, 1.0D, 255.0D));
  }
  
  public void onRenderEvent(RenderWorldLastEvent event) {
    for (Object o : this.mc.field_71441_e.field_72996_f) {
      if (!(o instanceof EntityOtherPlayerMP))
        continue; 
      EntityOtherPlayerMP player = (EntityOtherPlayerMP)o;
      if (AntiBot.bots.contains(player))
        return; 
      if (!this.showInvis.getState() && player.func_82150_aj())
        return; 
      double d = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * event.partialTicks;
      double d2 = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * event.partialTicks;
      double d3 = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * event.partialTicks;
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
      if (player instanceof net.minecraft.entity.player.EntityPlayer)
        drawPlayerESP(d - renderPosX, d2 - renderPosY, d3 - renderPosZ, player.field_70177_z, 1.0F, 0.0F, 0.0F); 
    } 
  }
  
  private void drawOutlinedBoundingBox(AxisAlignedBB aa) {
    Tessellator tessellator = Tessellator.func_178181_a();
    WorldRenderer worldrenderer = tessellator.func_178180_c();
    worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181707_g);
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
    tessellator.func_78381_a();
    worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181707_g);
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
    tessellator.func_78381_a();
    worldrenderer.func_181668_a(1, DefaultVertexFormats.field_181707_g);
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
    worldrenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
    tessellator.func_78381_a();
  }
  
  private void drawPlayerESP(double d, double d1, double d2, float angleToRotate, float r, float g, float b) {
    float x = (float)(System.currentTimeMillis() % 2000L) / 1000.0F;
    float red = 0.5F + 0.5F * MathHelper.func_76126_a(x * 3.1415927F);
    float green = 0.5F + 0.5F * MathHelper.func_76126_a((x + 1.3333334F) * 3.1415927F);
    float blue = 0.5F + 0.5F * MathHelper.func_76126_a((x + 2.6666667F) * 3.1415927F);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glLineWidth(2.0F);
    GL11.glDisable(3553);
    GL11.glDepthMask(false);
    GL11.glEnable(2848);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glEnable(2848);
    RenderUtil.hexESP(ClickGUI.rgb((int)this.redful.getValue(), (int)this.greenful.getValue(), (int)this.blueful.getValue()));
    drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5D, d1 + 0.1D, d2 - 0.5D, d + 0.5D, d1 + 2.0D, d2 + 0.5D));
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.2F);
    GL11.glDepthMask(true);
    GL11.glEnable(3553);
    GL11.glEnable(2929);
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