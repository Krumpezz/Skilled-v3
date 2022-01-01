package me.vene.skilled.utilities;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MathUtil {
  public static final Random random = new Random();
  
  private static final double EPSILON = 1.0E-12D;
  
  public static double map(double valueCoord1, double startCoord1, double endCoord1, double startCoord2, double endCoord2) {
    if (Math.abs(endCoord1 - startCoord1) < 1.0E-12D)
      throw new ArithmeticException("/ 0"); 
    double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
    return ratio * (valueCoord1 - startCoord1) + startCoord2;
  }
  
  public static double clamp(double value, double min, double max) {
    return Math.min(max, Math.max(min, value));
  }
  
  public static double getDistanceBetweenAngles(float angle1, float angle2) {
    float d = Math.abs(angle1 - angle2) % 360.0F;
    if (d > 180.0F)
      d = 360.0F - d; 
    return d;
  }
  
  public static float[] getAngle(Entity entity) {
    double x = entity.field_70165_t - (Minecraft.func_71410_x()).field_71439_g.field_70165_t;
    double z = entity.field_70161_v - (Minecraft.func_71410_x()).field_71439_g.field_70161_v;
    double y = (entity instanceof net.minecraft.entity.monster.EntityEnderman) ? (entity.field_70163_u - (Minecraft.func_71410_x()).field_71439_g.field_70163_u) : (entity.field_70163_u + entity.func_70047_e() - 0.4D - (Minecraft.func_71410_x()).field_71439_g.field_70163_u + (Minecraft.func_71410_x()).field_71439_g.func_70047_e() - 0.4D);
    double helper = MathHelper.func_76133_a(x * x + z * z);
    float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
    float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));
    if (z < 0.0D && x < 0.0D) {
      newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
    } else if (z < 0.0D && x > 0.0D) {
      newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));
    } 
    return new float[] { newPitch, newYaw };
  }
}