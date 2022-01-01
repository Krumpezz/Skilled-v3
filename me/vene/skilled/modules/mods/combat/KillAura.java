package me.vene.skilled.modules.mods.combat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.MouseUtil;
import me.vene.skilled.utilities.ReflectionUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KillAura extends Module {
  private boolean Blocking;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private BooleanValue mobs = new BooleanValue("Mobs", false);
  
  private BooleanValue silent = new BooleanValue("Silent", true);
  
  private BooleanValue antiBot = new BooleanValue("Antibot", true);
  
  private BooleanValue teams = new BooleanValue("Teams", true);
  
  private BooleanValue autoBlock = new BooleanValue("Autoblock", false);
  
  private Field damnfield;
  
  private NumberValue reachValue = new NumberValue("Reach", 4.25D, 3.0D, 6.0D);
  
  private NumberValue minAps = new NumberValue("Min APS", 7.0D, 1.0D, 20.0D);
  
  private NumberValue maxAps = new NumberValue("Max APS", 14.0D, 1.0D, 20.0D);
  
  private ArrayList<EntityLivingBase> entityList = new ArrayList<EntityLivingBase>();
  
  protected long lastMS;
  
  public KillAura() {
    super(StringRegistry.register("KillAura"), 0, Category.C);
    try {
      String fieldshit = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '7', 
            '8', '_', 'j' });
      this.damnfield = PlayerControllerMP.class.getDeclaredField(StringRegistry.register(fieldshit));
    } catch (NoSuchFieldException noSuchFieldException) {}
    addValue(this.reachValue);
    addValue(this.maxAps);
    addValue(this.minAps);
    addOption(this.silent);
    addOption(this.autoBlock);
    addOption(this.mobs);
    addOption(this.teams);
  }
  
  public boolean isOnSameTeam(EntityLivingBase entity) {
    if (entity.func_96124_cp() != null && this.mc.field_71439_g.func_96124_cp() != null) {
      char c1 = entity.func_145748_c_().func_150254_d().charAt(1);
      char c2 = this.mc.field_71439_g.func_145748_c_().func_150254_d().charAt(1);
      return (c1 == c2);
    } 
    return false;
  }
  
  public void onEnable() {
    updatebefore();
  }
  
  public void onDisable() {
    if (this.Blocking && this.autoBlock.getState()) {
      int useKeyBind = this.mc.field_71474_y.field_74313_G.func_151463_i();
      KeyBinding.func_74510_a(useKeyBind, false);
      MouseUtil.sendClick(1, false);
      this.Blocking = false;
    } 
  }
  
  private boolean hasTimePassedMS(long MS) {
    return (getCurrentMS() >= this.lastMS + MS);
  }
  
  private long getCurrentMS() {
    return System.nanoTime() / 1000000L;
  }
  
  private void updatebefore() {
    this.lastMS = getCurrentMS();
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    this.entityList.clear();
    if (this.mc.field_71441_e == null || this.mc.field_71439_g == null)
      return; 
    for (Object object : this.mc.field_71441_e.field_72996_f) {
      if (!(object instanceof EntityLivingBase) && this.mobs.getState())
        continue; 
      if (!(object instanceof EntityPlayer) && !this.mobs.getState())
        continue; 
      if (((EntityLivingBase)object).func_70032_d((Entity)this.mc.field_71439_g) > this.reachValue.getValue())
        continue; 
      if ((EntityLivingBase)object == this.mc.field_71439_g || ((EntityLivingBase)object).field_70128_L || ((EntityLivingBase)object).func_110143_aJ() <= 0.0F || ((EntityLivingBase)object).func_82150_aj())
        continue; 
      if (isOnSameTeam((EntityLivingBase)object) && this.teams.getState())
        continue; 
      this.entityList.add((EntityLivingBase)object);
    } 
    if (!this.entityList.isEmpty()) {
      EntityLivingBase target = this.entityList.get(0);
      if (this.autoBlock.getState() && this.mc.field_71439_g.func_71045_bC() != null)
        if (this.mc.field_71439_g.func_71045_bC().func_77973_b() instanceof net.minecraft.item.ItemSword)
          this.Blocking = true;  
      if (this.silent.getState()) {
        this.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C05PacketPlayerLook((float)(getRotationsNeeded((Entity)target)[0] + MathUtil.random.nextFloat() * 0.95D + 0.05D), (float)(getRotationsNeeded((Entity)target)[1] + MathUtil.random.nextFloat() * 0.95D + 0.05D), true));
      } else {
        this.mc.field_71439_g.field_70177_z = (float)(getRotationsNeeded((Entity)target)[0] + MathUtil.random.nextFloat() * 0.95D + 0.05D);
        this.mc.field_71439_g.field_70125_A = (float)(getRotationsNeeded((Entity)target)[1] + MathUtil.random.nextFloat() * 0.95D + 0.05D);
      } 
      double currentAps = MathUtil.random.nextFloat() * (this.maxAps.getValue() - this.minAps.getValue()) + this.minAps.getValue();
      if (hasTimePassedMS((int)Math.round(1000.0D / currentAps))) {
        this.mc.field_71439_g.func_71038_i();
        this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C02PacketUseEntity((Entity)target, C02PacketUseEntity.Action.ATTACK));
        updatebefore();
      } 
    } else {
      this.Blocking = false;
    } 
    checkBlock();
  }
  
  private void checkBlock() {
    if (this.Blocking) {
      int useKeyBind = this.mc.field_71474_y.field_74313_G.func_151463_i();
      KeyBinding.func_74510_a(useKeyBind, true);
      KeyBinding.func_74507_a(useKeyBind);
      MouseUtil.sendClick(1, true);
    } else {
      int useKeyBind = this.mc.field_71474_y.field_74313_G.func_151463_i();
      KeyBinding.func_74510_a(useKeyBind, false);
      MouseUtil.sendClick(1, false);
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
  
  private boolean breakingBlock(EntityPlayer player) {
    if (player instanceof net.minecraft.client.entity.EntityPlayerSP) {
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