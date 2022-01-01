package me.vene.skilled.modules.mods.utility;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Refill extends Module {
  private static Minecraft mc = Minecraft.func_71410_x();
  
  private NumberValue minDelay = new NumberValue(StringRegistry.register("Min Delay"), 75.0D, 0.0D, 200.0D);
  
  private NumberValue maxDelay = new NumberValue(StringRegistry.register("Max Delay"), 125.0D, 0.0D, 200.0D);
  
  private BooleanValue pots = new BooleanValue("Pots", true);
  
  private BooleanValue soup = new BooleanValue("Soup", true);
  
  private long nextDelay;
  
  protected long lastMS;
  
  public Refill() {
    super(StringRegistry.register("Refill"), 0, Category.U);
    addOption(this.pots);
    addOption(this.soup);
    addValue(this.maxDelay);
    addValue(this.minDelay);
  }
  
  public void newDelay() {
    this.nextDelay = (long)(MathUtil.random.nextFloat() * (this.maxDelay.getValue() - this.minDelay.getValue()) + this.minDelay.getValue());
  }
  
  private boolean isSoup(ItemStack stack) {
    return (stack != null && stack.func_77973_b() == Items.field_151009_A);
  }
  
  private boolean isPot(ItemStack stack) {
    if (stack != null && stack.func_77973_b() == Items.field_151068_bn && ItemPotion.func_77831_g(stack.func_77952_i()))
      for (Object o : ((ItemPotion)stack.func_77973_b()).func_77832_l(stack)) {
        if (((PotionEffect)o).func_76456_a() == Potion.field_76432_h.field_76415_H)
          return true; 
      }  
    return false;
  }
  
  private boolean isHotbarFull() {
    for (int i = 36; i < 45; i++) {
      this;
      Slot slot = mc.field_71439_g.field_71069_bz.func_75139_a(i);
      if (slot == null)
        return false; 
      ItemStack stack = slot.func_75211_c();
      if (stack == null)
        return false; 
    } 
    return true;
  }
  
  public void onEnable() {
    this;
    mc.func_147114_u().func_147297_a((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    this;
    this;
    mc.func_147108_a((GuiScreen)new GuiInventory((EntityPlayer)mc.field_71439_g));
    updatebefore();
  }
  
  public void onDisable() {
    this;
    if (mc.field_71462_r != null) {
      this;
      mc.func_147108_a(null);
    } 
  }
  
  public long getCurrentMS() {
    return System.nanoTime() / 1000000L;
  }
  
  public boolean hasTimePassedMS(long MS) {
    return (getCurrentMS() >= this.lastMS + MS);
  }
  
  public void updatebefore() {
    this.lastMS = getCurrentMS();
  }
  
  public void onClientTick(TickEvent.ClientTickEvent e) {
    if (!getState())
      return; 
    if (isHotbarFull()) {
      setState(false);
      return;
    } 
    this;
    if (mc.field_71439_g.field_71070_bA != null) {
      this;
      if (mc.field_71439_g.field_71069_bz != null) {
        this;
        if (mc.field_71439_g.field_71069_bz.func_75138_a() != null) {
          this;
          if (mc.field_71442_b != null) {
            this;
            if (mc.field_71462_r != null) {
              this;
              if (mc.field_71462_r instanceof GuiInventory) {
                int i = 0;
                this;
                for (; i < mc.field_71439_g.field_71069_bz.func_75138_a().size(); i++) {
                  this;
                  Slot slot = mc.field_71439_g.field_71069_bz.func_75139_a(i);
                  if (slot != null) {
                    ItemStack stack = slot.func_75211_c();
                    if (stack != null && 
                      i < 36 && (
                      !this.pots.getState() || this.soup.getState() || isPot(stack)) && (
                      this.pots.getState() || !this.soup.getState() || isSoup(stack)) && (
                      !this.pots.getState() || !this.soup.getState() || isPot(stack) || isSoup(stack)) && (
                      this.pots.getState() || this.soup.getState())) {
                      if (hasTimePassedMS(this.nextDelay)) {
                        this;
                        this;
                        this;
                        ItemStack newstack = mc.field_71442_b.func_78753_a(mc.field_71439_g.field_71069_bz.field_75152_c, i, 0, 1, (EntityPlayer)mc.field_71439_g);
                        newDelay();
                        updatebefore();
                      } 
                      return;
                    } 
                  } 
                } 
                setState(false);
                return;
              } 
            } 
            setState(false);
            return;
          } 
        } 
      } 
    } 
    setState(false);
  }
}