package me.vene.skilled.modules.mods.combat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.MouseUtil;
import me.vene.skilled.utilities.ReflectionUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
  private long nextLeftUp;
  
  private long nextLeftDown;
  
  private long nextDrop;
  
  private long nextExhaust;
  
  private double dropRate;
  
  private boolean dropping;
  
  private Random random = new Random();
  
  private Method guiScreenMethod;
  
  private String MinCPS = StringRegistry.register(new String(new char[] { 'M', 'i', 'n', ' ', 'C', 'P', 'S' }));
  
  private String MaxCPS = StringRegistry.register(new String(new char[] { 'M', 'a', 'x', ' ', 'C', 'P', 'S' }));
  
  private String jitterIntensity = StringRegistry.register(new String(new char[] { 
          'J', 'i', 't', 't', 'e', 'r', ' ', 'V', 'a', 'l', 
          'u', 'e' }));
  
  private String inventory = StringRegistry.register(new String(new char[] { 
          'I', 'n', 'v', 'e', 'n', 't', 'o', 'r', 'y', ' ', 
          'F', 'i', 'l', 'l' }));
  
  private String jitt = StringRegistry.register(new String(new char[] { 'J', 'i', 't', 't', 'e', 'r' }));
  
  private String swords = StringRegistry.register(new String(new char[] { 
          'O', 'n', 'l', 'y', ' ', 's', 'w', 'o', 'r', 'd', 
          's', '/', 'a', 'x', 'e', 's' }));
  
  private String breakblocks = StringRegistry.register(new String(new char[] { 
          'B', 'r', 'e', 'a', 'k', ' ', 'b', 'l', 'o', 'c', 
          'k', 's' }));
  
  private NumberValue mincps = new NumberValue(StringRegistry.register(this.MinCPS), 10.0D, 1.0D, 20.0D);
  
  private NumberValue maxcps = new NumberValue(StringRegistry.register(this.MaxCPS), 15.0D, 1.0D, 20.0D);
  
  private NumberValue jitterstren = new NumberValue(StringRegistry.register(this.jitterIntensity), 0.0D, 0.1D, 2.0D);
  
  private BooleanValue jitter = new BooleanValue(StringRegistry.register(this.jitt), false);
  
  private BooleanValue weapon = new BooleanValue(StringRegistry.register(this.swords), true);
  
  private BooleanValue inv = new BooleanValue(StringRegistry.register(this.inventory), true);
  
  private BooleanValue breakOption = new BooleanValue(StringRegistry.register(this.breakblocks), true);
  
  private BooleanValue limitItems = new BooleanValue(StringRegistry.register("Limit to items"), false);
  
  private BooleanValue blockHit = new BooleanValue(StringRegistry.register("Blockhit"), true);
  
  private boolean doBlockHit = false;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private Field damnfield;
  
  public List<Item> limitList = new CopyOnWriteArrayList<Item>();
  
  private boolean removeThings = false;
  
  public AutoClicker() {
    super(StringRegistry.register(new String(new char[] { 
              'A', 'u', 't', 'o', 'c', 'l', 'i', 'c', 'k', 'e', 
              'r' }, )), 0, Category.C);
    addValue(this.mincps);
    addValue(this.maxcps);
    addValue(this.jitterstren);
    addOption(this.jitter);
    addOption(this.inv);
    addOption(this.weapon);
    addOption(this.breakOption);
    addOption(this.limitItems);
    addOption(this.blockHit);
    try {
      String funcshit = new String(new char[] { 
            'f', 'u', 'n', 'c', '_', '7', '3', '8', '6', '4', 
            '_', 'a' });
      this.guiScreenMethod = GuiScreen.class.getDeclaredMethod(StringRegistry.register(funcshit), new Class[] { int.class, int.class, int.class });
    } catch (NoSuchMethodException noSuchMethodException) {}
    try {
      String fieldshit = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '7', 
            '8', '_', 'j' });
      this.damnfield = PlayerControllerMP.class.getDeclaredField(StringRegistry.register(fieldshit));
    } catch (NoSuchFieldException noSuchFieldException) {}
  }
  
  private boolean check(EntityPlayerSP playerSP) {
    return (!this.weapon.getState() || (playerSP.func_71045_bC() != null && (playerSP.func_71045_bC().func_77973_b() instanceof net.minecraft.item.ItemSword || playerSP.func_71045_bC().func_77973_b() instanceof net.minecraft.item.ItemAxe)));
  }
  
  private boolean check2(EntityPlayerSP playerSP) {
    return (playerSP.func_71045_bC() != null && this.limitList.contains(playerSP.func_71045_bC().func_77973_b()));
  }
  
  public void addToList(String itemID) {
    try {
      if (!this.limitList.contains(Item.func_150899_d(Integer.parseInt(itemID))))
        this.limitList.add(Item.func_150899_d(Integer.parseInt(itemID))); 
    } catch (Exception exception) {}
  }
  
  public void removeFromList(String itemID) {
    try {
      this.limitList.remove(Item.func_150899_d(Integer.parseInt(itemID)));
    } catch (Exception exception) {}
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (this.weapon.getState()) {
      if (!this.limitList.contains(Item.func_150899_d(268)))
        this.limitList.add(Item.func_150899_d(268)); 
      if (!this.limitList.contains(Item.func_150899_d(272)))
        this.limitList.add(Item.func_150899_d(272)); 
      if (!this.limitList.contains(Item.func_150899_d(267)))
        this.limitList.add(Item.func_150899_d(267)); 
      if (!this.limitList.contains(Item.func_150899_d(283)))
        this.limitList.add(Item.func_150899_d(283)); 
      if (!this.limitList.contains(Item.func_150899_d(276)))
        this.limitList.add(Item.func_150899_d(276)); 
      if (!this.limitList.contains(Item.func_150899_d(271)))
        this.limitList.add(Item.func_150899_d(271)); 
      if (!this.limitList.contains(Item.func_150899_d(275)))
        this.limitList.add(Item.func_150899_d(275)); 
      if (!this.limitList.contains(Item.func_150899_d(258)))
        this.limitList.add(Item.func_150899_d(258)); 
      if (!this.limitList.contains(Item.func_150899_d(286)))
        this.limitList.add(Item.func_150899_d(286)); 
      if (!this.limitList.contains(Item.func_150899_d(279)))
        this.limitList.add(Item.func_150899_d(279)); 
      this.removeThings = true;
    } else if (this.removeThings) {
      this.limitList.remove(Item.func_150899_d(268));
      this.limitList.remove(Item.func_150899_d(272));
      this.limitList.remove(Item.func_150899_d(267));
      this.limitList.remove(Item.func_150899_d(283));
      this.limitList.remove(Item.func_150899_d(276));
      this.limitList.remove(Item.func_150899_d(271));
      this.limitList.remove(Item.func_150899_d(275));
      this.limitList.remove(Item.func_150899_d(258));
      this.limitList.remove(Item.func_150899_d(286));
      this.limitList.remove(Item.func_150899_d(279));
      this.removeThings = false;
    } 
  }
  
  public void onRenderTick(TickEvent.RenderTickEvent e) {
    if (this.mc.field_71462_r == null && check(this.mc.field_71439_g)) {
      if (this.limitItems.getState() && 
        !check2(this.mc.field_71439_g))
        return; 
      Mouse.poll();
      if (Mouse.isButtonDown(0)) {
        if (!breakingBlock((EntityPlayer)this.mc.field_71439_g) && this.breakOption.getState())
          return; 
        if (this.jitter.getState() && MathUtil.random.nextDouble() > 0.65D) {
          float jitterStrength = (float)this.jitterstren.getValue() * 0.5F;
          this.mc.field_71439_g.field_70177_z = MathUtil.random.nextBoolean() ? (this.mc.field_71439_g.field_70177_z += MathUtil.random.nextFloat() * jitterStrength) : (this.mc.field_71439_g.field_70177_z -= MathUtil.random.nextFloat() * jitterStrength);
          this.mc.field_71439_g.field_70125_A = MathUtil.random.nextBoolean() ? (float)(this.mc.field_71439_g.field_70125_A + MathUtil.random.nextFloat() * jitterStrength * 0.75D) : (float)(this.mc.field_71439_g.field_70125_A - MathUtil.random.nextFloat() * jitterStrength * 0.75D);
        } 
        if (this.nextLeftDown > 0L && this.nextLeftUp > 0L) {
          if (System.currentTimeMillis() > this.nextLeftDown) {
            int attackKeyBind = this.mc.field_71474_y.field_74312_F.func_151463_i();
            KeyBinding.func_74510_a(attackKeyBind, true);
            KeyBinding.func_74507_a(attackKeyBind);
            MouseUtil.sendClick(0, true);
            if (Mouse.isButtonDown(1) && this.blockHit.getState()) {
              this.doBlockHit = true;
              int useKeyBind = this.mc.field_71474_y.field_74313_G.func_151463_i();
              KeyBinding.func_74510_a(useKeyBind, true);
              KeyBinding.func_74507_a(useKeyBind);
              MouseUtil.sendClick(1, true);
            } else {
              this.doBlockHit = false;
            } 
            generateLeftDelay();
          } else if (System.currentTimeMillis() > this.nextLeftUp) {
            int attackKeyBind = this.mc.field_71474_y.field_74312_F.func_151463_i();
            KeyBinding.func_74510_a(attackKeyBind, false);
            MouseUtil.sendClick(0, false);
            if (this.doBlockHit) {
              int useKeyBind = this.mc.field_71474_y.field_74313_G.func_151463_i();
              KeyBinding.func_74510_a(useKeyBind, false);
              MouseUtil.sendClick(1, false);
            } 
          } 
        } else {
          generateLeftDelay();
        } 
      } else {
        this.nextLeftUp = 0L;
        this.nextLeftDown = 0L;
      } 
    } else if (this.mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory || this.mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiChest) {
      if (Mouse.isButtonDown(0) && (Keyboard.isKeyDown(54) || Keyboard.isKeyDown(42))) {
        boolean inventoryFill = this.inv.getState();
        if (!inventoryFill)
          return; 
        if (this.nextLeftUp == 0L || this.nextLeftDown == 0L) {
          generateLeftDelay();
          return;
        } 
        if (System.currentTimeMillis() > this.nextLeftDown) {
          generateLeftDelay();
          clickInventory(this.mc.field_71462_r);
        } 
      } else {
        this.nextLeftUp = 0L;
        this.nextLeftDown = 0L;
      } 
    } 
  }
  
  private void generateLeftDelay() {
    double minCPS = this.mincps.getValue();
    double maxCPS;
    if (minCPS > (maxCPS = this.maxcps.getValue()))
      return; 
    double CPS = minCPS + MathUtil.random.nextDouble() * (maxCPS - minCPS);
    long delay = (int)Math.round(1000.0D / CPS);
    if (System.currentTimeMillis() > this.nextDrop) {
      if (!this.dropping && MathUtil.random.nextInt(100) >= 85) {
        this.dropping = true;
        this.dropRate = 1.1D + MathUtil.random.nextDouble() * 0.15D;
      } else {
        this.dropping = false;
      } 
      this.nextDrop = System.currentTimeMillis() + 500L + MathUtil.random.nextInt(1500);
    } 
    if (this.dropping)
      delay = (long)(delay * this.dropRate); 
    if (System.currentTimeMillis() > this.nextExhaust) {
      if (MathUtil.random.nextInt(100) >= 80)
        delay += 50L + MathUtil.random.nextInt(150); 
      this.nextExhaust = System.currentTimeMillis() + 500L + MathUtil.random.nextInt(1500);
    } 
    this.nextLeftDown = System.currentTimeMillis() + delay;
    this.nextLeftUp = System.currentTimeMillis() + delay / 2L - MathUtil.random.nextInt(10);
  }
  
  private void clickInventory(GuiScreen screen) {
    int var1 = Mouse.getX() * screen.field_146294_l / this.mc.field_71443_c;
    int var2 = screen.field_146295_m - Mouse.getY() * screen.field_146295_m / this.mc.field_71440_d - 1;
    int var3 = 0;
    try {
      this.guiScreenMethod.setAccessible(true);
      this.guiScreenMethod.invoke(screen, new Object[] { Integer.valueOf(var1), Integer.valueOf(var2), Integer.valueOf(var3) });
      this.guiScreenMethod.setAccessible(false);
    } catch (Exception exception) {}
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