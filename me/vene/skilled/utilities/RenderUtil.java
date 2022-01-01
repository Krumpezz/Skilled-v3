package me.vene.skilled.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
  public static void drawCheckmark(float x, float y, int hexColor) {
    GL11.glPushMatrix();
    GL11.glEnable(2848);
    GL11.glDisable(3553);
    GL11.glEnable(3042);
    hexColor(hexColor);
    GL11.glLineWidth(2.0F);
    GL11.glBegin(1);
    GL11.glVertex2d((x + 1.0F), (y + 1.0F));
    GL11.glVertex2d((x + 3.0F), (y + 4.0F));
    GL11.glEnd();
    GL11.glBegin(1);
    GL11.glVertex2d((x + 3.0F), (y + 4.0F));
    GL11.glVertex2d((x + 6.0F), (y - 2.0F));
    GL11.glEnd();
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glPopMatrix();
  }
  
  public static void hexColor(int hexColor) {
    float red = (hexColor >> 16 & 0xFF) / 255.0F;
    float green = (hexColor >> 8 & 0xFF) / 255.0F;
    float blue = (hexColor & 0xFF) / 255.0F;
    float alpha = (hexColor >> 24 & 0xFF) / 255.0F;
    GL11.glColor4f(red, green, blue, alpha);
  }
  
  public static void hexESP(int hexColor) {
    float red = (hexColor >> 16 & 0xFF) / 255.0F;
    float green = (hexColor >> 8 & 0xFF) / 255.0F;
    float blue = (hexColor & 0xFF) / 255.0F;
    GL11.glColor4f(red, green, blue, 1.0F);
  }
  
  public static void drawTexturedRectangle(ResourceLocation resourceLocation, double posX, double posY, float width, float height, int color) {
    float u = 1.0F, v = 1.0F, uWidth = 1.0F, vHeight = 1.0F, textureWidth = 1.0F, textureHeight = 1.0F;
    GL11.glPushMatrix();
    GlStateManager.func_179147_l();
    Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
    hexColor(color);
    GL11.glBegin(7);
    GL11.glTexCoord2d((u / textureWidth), (v / textureHeight));
    GL11.glVertex2d(posX, posY);
    GL11.glTexCoord2d((u / textureWidth), ((v + vHeight) / textureHeight));
    GL11.glVertex2d(posX, posY + height);
    GL11.glTexCoord2d(((u + uWidth) / textureWidth), ((v + vHeight) / textureHeight));
    GL11.glVertex2d(posX + width, posY + height);
    GL11.glTexCoord2d(((u + uWidth) / textureWidth), (v / textureHeight));
    GL11.glVertex2d(posX + width, posY);
    GL11.glEnd();
    GlStateManager.func_179084_k();
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
  }
  
  public static void drawOutlinedBoundingBox(AxisAlignedBB bb) {
    GL11.glBegin(1);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
    GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
    GL11.glEnd();
  }
}