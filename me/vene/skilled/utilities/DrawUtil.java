package me.vene.skilled.utilities;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class DrawUtil {
  public static void drawCircleFilled(float radius, Vector2f center, Vector4f color) {
    prepare();
    GL11.glBegin(6);
    GL11.glColor4f(color.x, color.y, color.z, color.w);
    GL11.glVertex2f(center.x, center.y);
    for (int i = 0; i < 720; i++) {
      double th = -i * Math.PI / 360.0D;
      float x = (float)(Math.cos(th) * radius);
      float y = (float)(Math.sin(th) * radius);
      GL11.glVertex2f(center.x + x, center.y + y);
    } 
    GL11.glEnd();
    end();
  }
  
  public static void drawCircleEmpty(float radius, Vector2f center, Vector4f color) {
    prepare();
    GL11.glBegin(2);
    GL11.glColor4f(color.x, color.y, color.z, color.w);
    for (int i = 0; i < 720; i++) {
      double th = -i * Math.PI / 360.0D;
      float x = (float)(Math.cos(th) * radius);
      float y = (float)(Math.sin(th) * radius);
      GL11.glVertex2f(center.x + x, center.y + y);
    } 
    GL11.glEnd();
    end();
  }
  
  public static void drawSector(float radius, Vector2f center, Vector4f color, double startAngle, double endAngle) {
    prepare();
    GL11.glBegin(6);
    GL11.glColor4f(color.x, color.y, color.z, color.w);
    GL11.glVertex2f(center.x, center.y);
    float dtheta = (float)(endAngle - startAngle);
    for (int i = 0; i < 720; i++) {
      double th = (-i * dtheta / 720.0F) - startAngle;
      float x = (float)(Math.cos(th) * radius);
      float y = (float)(Math.sin(th) * radius);
      GL11.glVertex2f(center.x + x, center.y + y);
    } 
    GL11.glEnd();
    end();
  }
  
  public static void drawArc(float radius, Vector2f center, Vector4f color, double startAngle, double endAngle) {
    prepare();
    GL11.glBegin(3);
    GL11.glColor4f(color.x, color.y, color.z, color.w);
    float dtheta = (float)(endAngle - startAngle);
    for (int i = 0; i < 720; i++) {
      double th = (-i * dtheta / 720.0F) - startAngle;
      float x = (float)(Math.cos(th) * radius);
      float y = (float)(Math.sin(th) * radius);
      GL11.glVertex2f(center.x + x, center.y + y);
    } 
    GL11.glEnd();
    end();
  }
  
  public static void drawRect(Vector2f pos, float width, float height, Vector4f color) {
    prepare();
    GL11.glBegin(7);
    GL11.glColor4f(color.x, color.y, color.z, color.w);
    GL11.glVertex2f(pos.x + width, pos.y + height);
    GL11.glVertex2f(pos.x + width, pos.y);
    GL11.glVertex2f(pos.x, pos.y);
    GL11.glVertex2f(pos.x, pos.y + height);
    GL11.glEnd();
    end();
  }
  
  public static void drawRoundedRect(Vector2f pos, float width, float height, float radius, Vector4f color) {
    drawRect(new Vector2f(pos.x + radius, pos.y), width - 2.0F * radius, height, color);
    drawRect(new Vector2f(pos.x, pos.y + radius), width, height - 2.0F * radius, color);
    drawSector(radius, new Vector2f(pos.x + radius, pos.y + radius), color, 1.5707963267948966D, Math.PI);
    drawSector(radius, new Vector2f(pos.x - radius + width, pos.y + radius), color, 0.0D, 1.5707963267948966D);
    drawSector(radius, new Vector2f(pos.x - radius + width, pos.y - radius + height), color, 4.71238898038469D, 6.283185307179586D);
    drawSector(radius, new Vector2f(pos.x + radius, pos.y - radius + height), color, Math.PI, 4.71238898038469D);
  }
  
  private static void prepare() {
    GlStateManager.func_179090_x();
    GlStateManager.func_179094_E();
    GlStateManager.func_179123_a();
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
  }
  
  private static void end() {
    GlStateManager.func_179121_F();
    GlStateManager.func_179099_b();
    GlStateManager.func_179098_w();
  }
}