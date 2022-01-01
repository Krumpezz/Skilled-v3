package me.vene.skilled.modules.mods.combat;

import java.lang.reflect.Field;
import me.vene.skilled.SkilledClient;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.ReflectionUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AimAssist extends Module {
  private String horizontal = StringRegistry.register(new String(new char[] { 'H', 'o', 'r', 'i', 'z', 'o', 'n', 't', 'a', 'l' }));
  
  private String vertical = StringRegistry.register(new String(new char[] { 'V', 'e', 'r', 't', 'i', 'c', 'a', 'l' }));
  
  private String range = StringRegistry.register(new String(new char[] { 'D', 'i', 's', 't', 'a', 'n', 'c', 'e' }));
  
  private String onlyclick = StringRegistry.register(new String(new char[] { 'C', 'l', 'i', 'c', 'k', ' ', 'A', 'i', 'm' }));
  
  private String angle = StringRegistry.register(new String(new char[] { 'F', 'O', 'V' }));
  
  private String swords = StringRegistry.register("Weapon");
  
  private NumberValue hor = new NumberValue(StringRegistry.register(this.horizontal), 5.0D, 0.0D, 10.0D);
  
  private NumberValue ver = new NumberValue(StringRegistry.register(this.vertical), 2.0D, 0.0D, 10.0D);
  
  private NumberValue dist = new NumberValue(StringRegistry.register(this.range), 6.0D, 2.0D, 8.0D);
  
  private NumberValue fov = new NumberValue(StringRegistry.register(this.angle), 73.0D, 10.0D, 180.0D);
  
  private BooleanValue weapon = new BooleanValue(StringRegistry.register(this.swords), true);
  
  private BooleanValue click = new BooleanValue(StringRegistry.register(this.onlyclick), true);
  
  private BooleanValue strafeIncrease = new BooleanValue(StringRegistry.register("Strafe increase"), false);
  
  private BooleanValue breakBlocksCheck = new BooleanValue(StringRegistry.register("Break Block Check"), true);
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private Field damnfield;
  
  public AimAssist() {
    super(StringRegistry.register(new String(new char[] { 'A', 'i', 'm', 'A', 's', 's', 'i', 's', 't' }, )), 0, Category.C);
    addValue(this.hor);
    addValue(this.ver);
    addValue(this.dist);
    addValue(this.fov);
    addOption(this.breakBlocksCheck);
    addOption(this.weapon);
    addOption(this.click);
    addOption(this.strafeIncrease);
    try {
      String fieldshit = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '7', 
            '8', '_', 'j' });
      this.damnfield = PlayerControllerMP.class.getDeclaredField(StringRegistry.register(fieldshit));
    } catch (NoSuchFieldException noSuchFieldException) {}
  }
  
  private void faceEntity(EntityLivingBase entity) {
    float[] rotations = getRotationsNeeded((Entity)entity);
    if (rotations != null) {
      double theSpeed;
      if (this.strafeIncrease.getState() && this.mc.field_71439_g.field_70702_br != 0.0F) {
        theSpeed = this.hor.getValue() / 3.0D * 1.25D;
      } else {
        theSpeed = this.hor.getValue() / 3.0D;
      } 
      this.mc.field_71439_g.field_70177_z = (float)limitAngleChange(this.mc.field_71439_g.field_70177_z, rotations[0] + MathUtil.random.nextFloat() * 0.95D + 0.05D, theSpeed);
      this.mc.field_71439_g.field_70125_A = (float)limitAngleChange(this.mc.field_71439_g.field_70125_A, rotations[1] + MathUtil.random.nextFloat() * 0.95D + 0.05D, this.ver.getValue() / 4.5D);
    } 
  }
  
  private float[] getRotationsNeeded(Entity entity) {
    double diffY;
    if (entity == null)
      return null; 
    double diffX = entity.field_70165_t - this.mc.field_71439_g.field_70165_t;
    double diffZ = entity.field_70161_v - this.mc.field_71439_g.field_70161_v;
    if (entity instanceof EntityLivingBase) {
      EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
      diffY = entityLivingBase.field_70163_u + entityLivingBase.func_70047_e() * 0.6D - this.mc.field_71439_g.field_70163_u + this.mc.field_71439_g.func_70047_e();
    } else {
      diffY = ((entity.func_174813_aQ()).field_72338_b + (entity.func_174813_aQ()).field_72337_e) / 2.0D - this.mc.field_71439_g.field_70163_u + this.mc.field_71439_g.func_70047_e();
    } 
    double dist = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
    float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
    return new float[] { this.mc.field_71439_g.field_70177_z + 
        MathHelper.func_76142_g(yaw - this.mc.field_71439_g.field_70177_z), this.mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - this.mc.field_71439_g.field_70125_A) };
  }
  
  private EntityLivingBase getTargetEntity() {
    double maxDistance = 360.0D;
    EntityLivingBase target = null;
    for (Object object : this.mc.field_71441_e.field_72996_f) {
      if (!(object instanceof EntityLivingBase))
        continue; 
      EntityLivingBase entity = (EntityLivingBase)object;
      if (!canTarget(entity))
        continue; 
      float yaw = MathUtil.getAngle((Entity)entity)[1];
      double yawDistance = MathUtil.getDistanceBetweenAngles(yaw, this.mc.field_71439_g.field_70177_z);
      if (maxDistance <= yawDistance)
        continue; 
      target = entity;
    } 
    return target;
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (event.phase != TickEvent.Phase.END || this.mc.field_71439_g == null || this.mc.field_71441_e == null || this.mc.field_71462_r != null)
      return; 
    if (!breakingBlock((EntityPlayer)this.mc.field_71439_g) && this.breakBlocksCheck.getState())
      return; 
    if (this.mc.field_71439_g.field_70128_L)
      return; 
    if (!Mouse.isButtonDown(0) && this.click.getState() && System.currentTimeMillis() - SkilledClient.getInstance().getEventManager().getLastClick() >= 150L)
      return; 
    EntityLivingBase entityLivingBase = getTargetEntity();
    if (entityLivingBase != null && check(this.mc.field_71439_g))
      faceEntity(entityLivingBase); 
  }
  
  private boolean check(EntityPlayerSP playerSP) {
    return (!this.weapon.getState() || (playerSP.func_71045_bC() != null && (playerSP.func_71045_bC().func_77973_b() instanceof net.minecraft.item.ItemSword || playerSP.func_71045_bC().func_77973_b() instanceof net.minecraft.item.ItemAxe)));
  }
  
  private boolean canTarget(EntityLivingBase entity) {
    if (entity == this.mc.field_71439_g || !this.mc.field_71439_g.func_70685_l((Entity)entity))
      return false; 
    float distance = entity.func_70032_d((Entity)this.mc.field_71439_g);
    float range = (float)this.dist.getValue();
    if (distance > range || distance < 1.0F)
      return false; 
    float yaw = MathUtil.getAngle((Entity)entity)[1];
    double yawDistance = MathUtil.getDistanceBetweenAngles(yaw, this.mc.field_71439_g.field_70177_z);
    double maxDistance = this.fov.getValue();
    if (yawDistance > maxDistance)
      return false; 
    if (!(entity instanceof EntityPlayer))
      return false; 
    if (SkilledClient.getInstance().getFriendManager().isFriend(((EntityPlayer)entity).getDisplayNameString()))
      return false; 
    if (entity.func_82150_aj()) {
      EntityPlayer entityPlayer = (EntityPlayer)entity;
      if (hasArmor(entityPlayer))
        return true; 
      return false;
    } 
    return true;
  }
  
  private boolean hasArmor(EntityPlayer player) {
    ItemStack[] armor = player.field_71071_by.field_70460_b;
    return (player.func_71045_bC() != null || (armor != null && (armor[0] != null || armor[1] != null || armor[2] != null || armor[3] != null)));
  }
  
  private double limitAngleChange(double current, double intended, double speed) {
    double change = intended - current;
    if (change > speed) {
      change = speed;
    } else if (change < -speed) {
      change = -speed;
    } 
    return current + change;
  }
  
  private boolean breakingBlock(EntityPlayer player) {
    if (player instanceof EntityPlayerSP) {
      PlayerControllerMP controller = (Minecraft.func_71410_x()).field_71442_b;
      String fieldmeme = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '7', 
            '8', '_', 'j' });
      boolean hasBlock = Boolean.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(fieldmeme), controller, PlayerControllerMP.class).toString()).booleanValue();
      try {
        this.damnfield.setAccessible(true);
        this.damnfield.getBoolean(controller);
        this.damnfield.setAccessible(false);
      } catch (Exception exception) {}
      if (hasBlock)
        return false; 
    } 
    return true;
  }
}