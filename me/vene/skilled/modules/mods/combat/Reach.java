package me.vene.skilled.modules.mods.combat;

import java.lang.reflect.Field;
import java.util.List;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.ClientUtil;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Reach extends Module {
  private long lastRangeChange;
  
  private double currentRange;
  
  private String MinDistance = StringRegistry.register(new String(new char[] { 
          'M', 'i', 'n', ' ', 'D', 'i', 's', 't', 'a', 'n', 
          'c', 'e' }));
  
  private String MaxDistance = StringRegistry.register(new String(new char[] { 
          'M', 'a', 'x', ' ', 'D', 'i', 's', 't', 'a', 'n', 
          'c', 'e' }));
  
  private NumberValue mindist = new NumberValue(StringRegistry.register(this.MinDistance), 3.1D, 3.0D, 6.0D);
  
  private NumberValue maxdist = new NumberValue(StringRegistry.register(this.MaxDistance), 3.2D, 3.0D, 6.0D);
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private BooleanValue misplaceOpt = new BooleanValue(StringRegistry.register("Misplace"), false);
  
  private BooleanValue onlySprinting = new BooleanValue(StringRegistry.register("Only sprinting"), false);
  
  private BooleanValue verticalCheck = new BooleanValue(StringRegistry.register("Vertical Check"), false);
  
  private String packetXstring = new String(new char[] { 
        'f', 'i', 'e', 'l', 'd', '_', '1', '4', '9', '4', 
        '5', '6', '_', 'b' });
  
  private String packetZstring = new String(new char[] { 
        'f', 'i', 'e', 'l', 'd', '_', '1', '4', '9', '4', 
        '5', '4', '_', 'd' });
  
  private Field packetXField;
  
  private Field packetZField;
  
  public Reach() {
    super(StringRegistry.register(new String(new char[] { 'R', 'e', 'a', 'c', 'h' }, )), 0, Category.C);
    addValue(this.mindist);
    addValue(this.maxdist);
    addOption(this.misplaceOpt);
    addOption(this.onlySprinting);
    try {
      this.packetXField = S18PacketEntityTeleport.class.getDeclaredField(StringRegistry.register(this.packetXstring));
      this.packetZField = S18PacketEntityTeleport.class.getDeclaredField(StringRegistry.register(this.packetZstring));
    } catch (NoSuchFieldException noSuchFieldException) {}
  }
  
  public void onMouseOver(float particalTicks) {
    if (this.misplaceOpt.getState())
      return; 
    if (this.mc.field_71439_g == null || this.mc.field_71441_e == null || this.mc.field_71462_r != null)
      return; 
    if (this.mc.field_71439_g.func_70115_ae())
      return; 
    if (this.onlySprinting.getState() && !this.mc.field_71439_g.func_70051_ag())
      return; 
    try {
      String server = (this.mc.func_147104_D()).field_78845_b;
      double minRange = this.mindist.getValue();
      double maxRange = this.maxdist.getValue();
      if (System.currentTimeMillis() > this.lastRangeChange + 1000L) {
        this.lastRangeChange = System.currentTimeMillis();
        this.currentRange = minRange + MathUtil.random.nextDouble() * (maxRange - minRange);
      } 
      double expand = 0.0D;
      MovingObjectPosition object = ClientUtil.getMouseOver(this.currentRange, 0.0D, particalTicks);
      if (object != null)
        this.mc.field_71476_x = object; 
    } catch (Exception e) {
      double expand = 0.0D;
      MovingObjectPosition object = ClientUtil.getMouseOver(this.currentRange, 0.0D, particalTicks);
      if (object != null)
        this.mc.field_71476_x = object; 
    } 
  }
  
  public S18PacketEntityTeleport onEntityTeleport(S18PacketEntityTeleport packet) {
    Entity entity = this.mc.field_71441_e.func_73045_a(packet.func_149451_c());
    if (entity instanceof net.minecraft.entity.player.EntityPlayer && this.misplaceOpt.getState()) {
      double x = packet.func_149449_d() / 32.0D;
      double z = packet.func_149446_f() / 32.0D;
      double minRange = this.mindist.getValue();
      double maxRange = this.maxdist.getValue();
      double distance = (minRange + MathUtil.random.nextDouble() * (maxRange - minRange)) / 3.0D;
      double f = distance;
      if (f == 0.0D) {
        onEntityTeleport(packet);
        return packet;
      } 
      double c = Math.hypot(this.mc.field_71439_g.field_70165_t - x, this.mc.field_71439_g.field_70161_v - z);
      if (f > c)
        f -= c; 
      float r = getAngle(x, z);
      if (a(this.mc.field_71439_g.field_70177_z, r) > 180.0D) {
        onEntityTeleport(packet);
        return packet;
      } 
      if (MathUtil.getDistanceBetweenAngles(this.mc.field_71439_g.field_70177_z, r = getAngle(x, z)) > 90.0D)
        return packet; 
      double a = Math.cos(Math.toRadians((r + 90.0F)));
      double b = Math.sin(Math.toRadians((r + 90.0F)));
      x -= a * f;
      z -= b * f;
      try {
        this.packetXField.setAccessible(true);
        this.packetXField.set(packet, Integer.valueOf(MathHelper.func_76128_c(x * 32.0D)));
        this.packetXField.setAccessible(false);
        this.packetZField.setAccessible(true);
        this.packetZField.set(packet, Integer.valueOf(MathHelper.func_76128_c(z * 32.0D)));
        this.packetZField.setAccessible(false);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return packet;
  }
  
  private double a(float a, float b) {
    float d = Math.abs(a - b) % 360.0F;
    if (d > 180.0F)
      d = 360.0F - d; 
    return d;
  }
  
  private float getAngle(double posX, double posZ) {
    double x = posX - this.mc.field_71439_g.field_70165_t;
    double z = posZ - this.mc.field_71439_g.field_70161_v;
    float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
    if (z < 0.0D && x < 0.0D) {
      newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
    } else if (z < 0.0D && x > 0.0D) {
      newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));
    } 
    return newYaw;
  }
  
  private Object[] getEntity(double distance, double expand, float partialTicks) {
    if (this.mc.func_175606_aa() != null && this.mc.field_71441_e != null) {
      Entity entity = null;
      Vec3 var6 = this.mc.func_175606_aa().func_174824_e(partialTicks);
      Vec3 var7 = this.mc.func_175606_aa().func_70676_i(partialTicks);
      Vec3 var8 = var6.func_72441_c(var7.field_72450_a * distance, var7.field_72448_b * distance, var7.field_72449_c * distance);
      Vec3 var9 = null;
      float var10 = 1.0F;
      List<Entity> var11 = this.mc.field_71441_e.func_72839_b(this.mc.func_175606_aa(), this.mc.func_175606_aa().func_174813_aQ().func_72321_a(var7.field_72450_a * distance, var7.field_72448_b * distance, var7.field_72449_c * distance).func_72314_b(1.0D, 1.0D, 1.0D));
      double var12 = distance;
      for (int var13 = 0; var13 < var11.size(); var13++) {
        Entity var14 = var11.get(var13);
        if (var14.func_70067_L()) {
          float var15 = var14.func_70111_Y();
          AxisAlignedBB var16 = var14.func_174813_aQ().func_72314_b(var15, var15, var15);
          var16 = var16.func_72314_b(expand, expand, expand);
          MovingObjectPosition var17 = var16.func_72327_a(var6, var8);
          if (var16.func_72318_a(var6)) {
            if (0.0D < var12 || var12 == 0.0D) {
              entity = var14;
              var9 = (var17 == null) ? var6 : var17.field_72307_f;
              var12 = 0.0D;
            } 
          } else {
            double var18;
            if (var17 != null && ((var18 = var6.func_72438_d(var17.field_72307_f)) < var12 || var12 == 0.0D))
              if (var14 == (this.mc.func_175606_aa()).field_70154_o && !entity.canRiderInteract()) {
                if (var12 == 0.0D) {
                  entity = var14;
                  var9 = var17.field_72307_f;
                } 
              } else {
                entity = var14;
                var9 = var17.field_72307_f;
                var12 = var18;
              }  
          } 
        } 
      } 
      if (var12 < distance && (entity instanceof EntityLivingBase || entity instanceof net.minecraft.entity.item.EntityItemFrame) && ((EntityLivingBase)entity).func_70685_l((Entity)(Minecraft.func_71410_x()).field_71439_g))
        return new Object[] { entity, var9 }; 
    } 
    return null;
  }
}