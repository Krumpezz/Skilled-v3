package me.vene.skilled.utilities;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ClientUtil {
  public static MovingObjectPosition getMouseOver(double distance, double expand, float partialTicks) {
    if (Minecraft.func_71410_x().func_175606_aa() != null && (Minecraft.func_71410_x()).field_71441_e != null) {
      Entity entity = null;
      Vec3 var6 = Minecraft.func_71410_x().func_175606_aa().func_174824_e(partialTicks);
      Vec3 var7 = Minecraft.func_71410_x().func_175606_aa().func_70676_i(partialTicks);
      Vec3 var8 = var6.func_72441_c(var7.field_72450_a * distance, var7.field_72448_b * distance, var7.field_72449_c * distance);
      Vec3 var9 = null;
      float var10 = 1.0F;
      List<Entity> var11 = (Minecraft.func_71410_x()).field_71441_e.func_72839_b(Minecraft.func_71410_x().func_175606_aa(), Minecraft.func_71410_x().func_175606_aa().func_174813_aQ().func_72321_a(var7.field_72450_a * distance, var7.field_72448_b * distance, var7.field_72449_c * distance).func_72314_b(var10, var10, var10));
      double var12 = distance;
      for (int var14 = 0; var14 < var11.size(); var14++) {
        Entity var15 = var11.get(var14);
        if (var15.func_70067_L()) {
          float var16 = var15.func_70111_Y();
          AxisAlignedBB var17 = var15.func_174813_aQ().func_72314_b(var16, var16, var16);
          var17 = var17.func_72314_b(expand, expand, expand);
          MovingObjectPosition var18 = var17.func_72327_a(var6, var8);
          if (var17.func_72318_a(var6)) {
            if (0.0D < var12 || var12 == 0.0D) {
              entity = var15;
              var9 = (var18 == null) ? var6 : var18.field_72307_f;
              var12 = 0.0D;
            } 
          } else {
            double var19;
            if (var18 != null && ((var19 = var6.func_72438_d(var18.field_72307_f)) < var12 || var12 == 0.0D))
              if (var15 == (Minecraft.func_71410_x().func_175606_aa()).field_70154_o && !entity.canRiderInteract()) {
                if (var12 == 0.0D) {
                  entity = var15;
                  var9 = var18.field_72307_f;
                } 
              } else {
                entity = var15;
                var9 = var18.field_72307_f;
                var12 = var19;
              }  
          } 
        } 
      } 
      if (var12 < distance && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70685_l((Entity)(Minecraft.func_71410_x()).field_71439_g))
        return new MovingObjectPosition(entity, var9); 
    } 
    return null;
  }
}