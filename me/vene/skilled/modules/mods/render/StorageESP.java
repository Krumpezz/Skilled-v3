package me.vene.skilled.modules.mods.render;

import java.util.Iterator;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.Box;
import me.vene.skilled.utilities.OGLRender;
import me.vene.skilled.utilities.ReflectionUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class StorageESP extends Module {
  private BooleanValue enderchestOpt = new BooleanValue("Ender chest", false);
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public StorageESP() {
    super(StringRegistry.register("Storage ESP"), 0, Category.R);
    addOption(this.enderchestOpt);
  }
  
  public void onRenderEvent(RenderWorldLastEvent event) {
    Iterator<TileEntity> iterator = this.mc.field_71441_e.field_147482_g.iterator();
    while (iterator.hasNext()) {
      TileEntity tileEntity;
      if (tileEntity = iterator.next() instanceof TileEntity && 
        !shouldDraw(tileEntity))
        continue; 
      RenderManager renderthing = this.mc.func_175598_ae();
      String Xstring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '5', '_', 'b' });
      String Ystring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '6', '_', 'c' });
      String Zstring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '3', '_', 'd' });
      double renderPosX = Double.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(Xstring), renderthing, RenderManager.class).toString()).doubleValue();
      double renderPosY = Double.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(Ystring), renderthing, RenderManager.class).toString()).doubleValue();
      double renderPosZ = Double.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(Zstring), renderthing, RenderManager.class).toString()).doubleValue();
      double x = tileEntity.func_174877_v().func_177958_n() - renderPosX;
      double y = tileEntity.func_174877_v().func_177956_o() - renderPosY;
      double z = tileEntity.func_174877_v().func_177952_p() - renderPosZ;
      float[] color = getColor(tileEntity);
      Box box = new Box(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
      if (tileEntity instanceof TileEntityChest) {
        TileEntityChest chest = TileEntityChest.class.cast(tileEntity);
        if (chest.field_145992_i != null) {
          box = new Box(x + 0.0625D, y, z - 0.9375D, x + 0.9375D, y + 0.875D, z + 0.9375D);
        } else if (chest.field_145991_k != null) {
          box = new Box(x + 0.9375D, y, z + 0.0625D, x - 0.9375D, y + 0.875D, z + 0.9375D);
        } else {
          if (chest.field_145992_i != null || chest.field_145991_k != null || chest.field_145990_j != null || chest.field_145988_l != null)
            continue; 
          box = new Box(x + 0.0625D, y, z + 0.0625D, x + 0.9375D, y + 0.875D, z + 0.9375D);
        } 
      } else if (tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest && this.enderchestOpt.getState()) {
        box = new Box(x + 0.0625D, y, z + 0.0625D, x + 0.9375D, y + 0.875D, z + 0.9375D);
      } 
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(1.0F);
      GL11.glDisable(3553);
      GL11.glDepthMask(false);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glEnable(2848);
      GL11.glTranslated(x, y, z);
      GL11.glTranslated(-x, -y, -z);
      GL11.glColor4f(color[0], color[1], color[2], 0.15F);
      OGLRender.drawBox(box);
      GL11.glColor4f(color[0], color[1], color[2], 1.0F);
      OGLRender.drawOutlinedBox(box);
      GL11.glDepthMask(true);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glPopMatrix();
    } 
  }
  
  private boolean shouldDraw(TileEntity tileEntity) {
    return (tileEntity instanceof TileEntityChest || tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest);
  }
  
  private float[] getColor(TileEntity tileEntity) {
    if (tileEntity instanceof TileEntityChest)
      return new float[] { 0.1F, 0.8F, 0.1F }; 
    if (tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest)
      return new float[] { 1.0F, 0.0F, 1.0F }; 
    return new float[] { 1.0F, 1.0F, 1.0F };
  }
}