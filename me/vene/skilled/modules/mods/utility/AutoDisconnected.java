package me.vene.skilled.modules.mods.utility;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.realms.RealmsMth;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoDisconnect extends Module {
  private BlockPos localBed = null;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public AutoDisconnect() {
    super("Auto Disconnect", 0, Category.U);
  }
  
  public static boolean bot(Entity en) {
    if (en.func_70005_c_().startsWith("))
      return true; 
    String n = en.func_145748_c_().func_150260_c();
    if (n.contains("))
      return n.contains("[NPC] "); 
    if (n.isEmpty() && en.func_70005_c_().isEmpty())
      return true; 
    if (n.length() == 10) {
      int num = 0;
      int let = 0;
      char[] var4 = n.toCharArray();
      for (char c : var4) {
        if (Character.isLetter(c)) {
          if (Character.isUpperCase(c))
            return false; 
          let++;
        } else {
          if (!Character.isDigit(c))
            return false; 
          num++;
        } 
      } 
      return (num >= 2 && let >= 2);
    } 
    return false;
  }
  
  public boolean isOnSameTeam(EntityLivingBase entity) {
    if (entity.func_96124_cp() != null && this.mc.field_71439_g.func_96124_cp() != null) {
      char c1 = entity.func_145748_c_().func_150254_d().charAt(1);
      char c2 = this.mc.field_71439_g.func_145748_c_().func_150254_d().charAt(1);
      return (c1 == c2);
    } 
    return false;
  }
  
  double distance(double x, double y) {
    return RealmsMth.sqrt(Math.pow(x, 2.0D) + Math.pow(y, 2.0D));
  }
  
  double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
    return distance(y1 - y2, distance(x1 - x2, z1 - z2));
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (Math.abs(this.mc.field_71439_g.field_70169_q - this.mc.field_71439_g.field_70165_t) > 10.0D) {
      updateBed();
      this.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("Teleport detected"));
    } 
    if (this.localBed == null)
      return; 
    this.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("ALERT ASDFASDF"));
    for (Object entity : this.mc.field_71441_e.field_72996_f) {
      if (!(entity instanceof EntityPlayer))
        continue; 
      if (bot((Entity)entity)) {
        this.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("BOT ALERT ASDFASDF"));
        continue;
      } 
      if (isOnSameTeam((EntityLivingBase)entity)) {
        this.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("same team ALERT ASDFASDF"));
        continue;
      } 
      this.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("SAME ASDFASDF"));
      if (distance(((EntityPlayer)entity).field_70165_t, ((EntityPlayer)entity).field_70163_u, ((EntityPlayer)entity).field_70161_v, this.localBed.func_177958_n(), this.localBed.func_177956_o(), this.localBed.func_177952_p()) > 30.0D) {
        this.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("distanmce ALERT ASDFASDF"));
        continue;
      } 
      this.mc.field_71439_g.func_71165_d("/l b");
    } 
  }
  
  private void updateBed() {
    for (int y = 30, ra = y; y >= -ra; y--) {
      for (int x = -ra; x <= ra; x++) {
        for (int z = -ra; z <= ra; z++) {
          BlockPos p = new BlockPos(this.mc.field_71439_g.field_70165_t + x, this.mc.field_71439_g.field_70163_u + y, this.mc.field_71439_g.field_70161_v + z);
          boolean bed = (this.mc.field_71441_e.func_180495_p(p).func_177230_c() == Blocks.field_150324_C);
          if (bed) {
            this.localBed = p;
            this.mc.field_71439_g.func_145747_a((IChatComponent)new ChatComponentText("FOUND BED"));
            break;
          } 
        } 
      } 
    } 
  }
}