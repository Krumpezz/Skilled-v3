package me.vene.skilled.modules;

import java.io.IOException;
import java.util.ArrayList;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class Module {
  private boolean state = false;
  
  private String name;
  
  private int key;
  
  private Category category;
  
  private ArrayList<BooleanValue> options;
  
  private ArrayList<NumberValue> values;
  
  public Module(String name, int key, Category category) {
    this.name = StringRegistry.register(name);
    this.key = key;
    this.category = category;
    this.options = new ArrayList<BooleanValue>();
    this.values = new ArrayList<NumberValue>();
  }
  
  public String getName() {
    return StringRegistry.register(this.name);
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getKey() {
    return this.key;
  }
  
  public void setKey(int key) {
    this.key = key;
  }
  
  public Category getCategory() {
    return this.category;
  }
  
  public ArrayList<BooleanValue> getOptions() {
    return this.options;
  }
  
  public void addOption(BooleanValue option) {
    this.options.add(option);
  }
  
  public ArrayList<NumberValue> getValues() {
    return this.values;
  }
  
  public void addValue(NumberValue value) {
    this.values.add(value);
  }
  
  public boolean getState() {
    return this.state;
  }
  
  public void toggle() {
    setState(!this.state);
  }
  
  public void setState(boolean enabled) {
    if (this.state == enabled)
      return; 
    this.state = enabled;
    if (enabled) {
      MinecraftForge.EVENT_BUS.register(this);
      FMLCommonHandler.instance().bus().register(this);
      onEnable();
    } else {
      MinecraftForge.EVENT_BUS.unregister(this);
      FMLCommonHandler.instance().bus().unregister(this);
      onDisable();
    } 
  }
  
  public static ArrayList<Module> getCategoryModules(Category cat) {
    ArrayList<Module> modsInCategory = new ArrayList<Module>();
    for (Module mod : ModuleManager.getModules()) {
      if (mod.getCategory() != cat)
        continue; 
      modsInCategory.add(mod);
    } 
    return modsInCategory;
  }
  
  public static Module getModule(Class<? extends Module> clazz) {
    for (Module mod : ModuleManager.getModules()) {
      if (mod.getClass() != clazz)
        continue; 
      return mod;
    } 
    return null;
  }
  
  public void onHurtAnimation() {}
  
  public void onEnable() {}
  
  public void onDisable() {}
  
  public void onLivingUpdate(EntityPlayerSP player) {}
  
  public void onMouseOver(float particalTicks) {}
  
  public void onClientTick(TickEvent.ClientTickEvent e) {}
  
  public void onRenderTick(TickEvent.RenderTickEvent e) {}
  
  public void onRenderText(RenderGameOverlayEvent.Post e) throws IOException {}
  
  public S18PacketEntityTeleport onEntityTeleport(S18PacketEntityTeleport packet) {
    return packet;
  }
  
  public void onRenderEvent(RenderWorldLastEvent e) {}
}