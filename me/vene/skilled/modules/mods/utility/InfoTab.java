package me.vene.skilled.modules.mods.utility;

import java.awt.Color;
import java.lang.reflect.Field;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import me.vene.skilled.gui.GUI;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class InfoTab extends Module {
  private Minecraft mc = Minecraft.func_71410_x();
  
  private BooleanValue tracerBool = new BooleanValue("Tracers", true);
  
  private BooleanValue fireballsBool = new BooleanValue("Fireballs", true);
  
  private BooleanValue arrowsBool = new BooleanValue("Arrows", true);
  
  private BooleanValue snowballsBool = new BooleanValue("Snowballs", false);
  
  private BooleanValue eggsBool = new BooleanValue("Eggs", false);
  
  private Field arrowInGroundField;
  
  private Field modelViewField;
  
  public InfoTab() {
    super("Warnings", 0, Category.U);
    addOption(this.fireballsBool);
    addOption(this.arrowsBool);
    addOption(this.snowballsBool);
    addOption(this.eggsBool);
    try {
      this.arrowInGroundField = EntityArrow.class.getDeclaredField(new String(new char[] { 
              'f', 'i', 'e', 'l', 'd', '_', '7', '0', '1', '9', 
              '3', '_', 'a' }));
    } catch (Exception e) {
      try {
        this.arrowInGroundField = EntityArrow.class.getDeclaredField(new String(new char[] { 'i', 'n', 'G', 'r', 'o', 'u', 'n', 'd' }));
      } catch (Exception exception) {}
    } 
    if (this.arrowInGroundField != null)
      this.arrowInGroundField.setAccessible(true); 
  }
  
  private float[] getViewProjection() throws IllegalAccessException {
    FloatBuffer a = (FloatBuffer)this.modelViewField.get(ActiveRenderInfo.class);
    float[] modelView = new float[16];
    for (int i = 0; i < 16; i++) {
      modelView[i] = a.get(i);
      System.out.println("a " + modelView[i]);
    } 
    return modelView;
  }
  
  public void onRenderText(RenderGameOverlayEvent.Post e) {
    if (this.mc.field_71441_e == null)
      return; 
    try {
      if (GUI.renderInfo) {
        ArrayList<String> alertArray = new ArrayList<String>();
        for (Object object : this.mc.field_71441_e.field_72996_f) {
          if (object instanceof EntityFireball && this.fireballsBool.getState())
            alertArray.add(EnumChatFormatting.RED + "FIREBALL ALERT: " + EnumChatFormatting.GOLD + String.valueOf((int)((EntityFireball)object).func_70032_d((Entity)(Minecraft.func_71410_x()).field_71439_g))); 
          if (object instanceof EntitySnowball && this.snowballsBool.getState())
            alertArray.add(EnumChatFormatting.DARK_AQUA + "SNOWBALL ALERT: " + EnumChatFormatting.GOLD + String.valueOf((int)((EntitySnowball)object).func_70032_d((Entity)(Minecraft.func_71410_x()).field_71439_g))); 
          if (object instanceof EntityEgg && this.eggsBool.getState())
            alertArray.add(EnumChatFormatting.DARK_RED + "EGG ALERT: " + EnumChatFormatting.GOLD + String.valueOf((int)((EntityEgg)object).func_70032_d((Entity)(Minecraft.func_71410_x()).field_71439_g))); 
          try {
            if (object instanceof EntityArrow && this.arrowsBool.getState() && !this.arrowInGroundField.getBoolean(object))
              alertArray.add(EnumChatFormatting.DARK_PURPLE + "ARROW ALERT: " + EnumChatFormatting.GOLD + String.valueOf((int)((EntityArrow)object).func_70032_d((Entity)(Minecraft.func_71410_x()).field_71439_g))); 
          } catch (IllegalAccessException illegalAccessException) {}
        } 
        for (int i = 0; i < alertArray.size(); i++)
          (Minecraft.func_71410_x()).field_71466_p.func_78276_b(alertArray.get(i), GUI.infoXPos + 2, GUI.infoYPos + 15 + i * 9, -1); 
        Entity p = null;
        Color currentColor = null;
        if (this.tracerBool.getState()) {
          for (Object object : this.mc.field_71441_e.field_72996_f) {
            p = null;
            if (object instanceof EntityFireball && this.fireballsBool.getState()) {
              p = (Entity)object;
              currentColor = Color.ORANGE;
            } 
            if (object instanceof EntitySnowball && this.snowballsBool.getState()) {
              p = (Entity)object;
              currentColor = Color.cyan;
            } 
            if (object instanceof EntityEgg && this.eggsBool.getState()) {
              p = (Entity)object;
              currentColor = Color.RED;
            } 
            try {
              if (object instanceof EntityArrow && this.arrowsBool.getState() && !this.arrowInGroundField.getBoolean(object)) {
                p = (Entity)object;
                currentColor = Color.MAGENTA;
              } 
            } catch (IllegalAccessException illegalAccessException) {}
            if (p != null) {
              EntityPlayerSP player = this.mc.field_71439_g;
              GlStateManager.func_179147_l();
              GlStateManager.func_179097_i();
              GlStateManager.func_179090_x();
              GlStateManager.func_179140_f();
              GL11.glEnable(2848);
              GlStateManager.func_179112_b(770, 771);
              GL11.glLineWidth(1.0F);
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
              double x = p.field_70142_S + (p.field_70165_t - p.field_70142_S) * e.partialTicks - renderPosX;
              double y = p.field_70137_T + (p.field_70163_u - p.field_70137_T) * e.partialTicks - renderPosY + 1.0D;
              double z = p.field_70136_U + (p.field_70161_v - p.field_70136_U) * e.partialTicks - renderPosZ;
              GlStateManager.func_179131_c(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), 1.0F);
              GL11.glBegin(1);
              GL11.glVertex3d(0.0D, player.func_70047_e(), 0.0D);
              GL11.glVertex3d(x, y, z);
              GL11.glEnd();
            } 
          } 
          GlStateManager.func_179084_k();
          GlStateManager.func_179126_j();
          GlStateManager.func_179098_w();
          GlStateManager.func_179145_e();
          GL11.glDisable(2848);
          GL11.glLineWidth(1.0F);
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        } 
      } 
    } catch (Exception exception) {}
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