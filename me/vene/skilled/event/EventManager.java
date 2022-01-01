package me.vene.skilled.event;

import java.io.IOException;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.ModuleManager;
import me.vene.skilled.renderer.NiggerRenderer;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.utilities.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventManager implements IEventListener {
  private Minecraft mc = Minecraft.func_71410_x();
  
  private long lastClick;
  
  private boolean selfDestructing = false;
  
  private long lastAttack;
  
  public static TimerUtil timerUtil = new TimerUtil();
  
  public static String lastAttackedEntityName = StringRegistry.register("None.");
  
  private String packetmeme = StringRegistry.register(new String(new char[] { 
          'p', 'a', 'c', 'k', 'e', 't', '_', 'h', 'a', 'n', 
          'd', 'l', 'e', 'r' }));
  
  public void invoke(Event event) {
    if (!this.selfDestructing) {
      if (event instanceof InputEvent.KeyInputEvent)
        onKeyPress((InputEvent.KeyInputEvent)event); 
      if (event instanceof TickEvent.ClientTickEvent)
        onClientTick((TickEvent.ClientTickEvent)event); 
      if (event instanceof TickEvent.RenderTickEvent)
        onRender((TickEvent.RenderTickEvent)event); 
      if (event instanceof LivingEvent.LivingUpdateEvent)
        onLivingUpdate((LivingEvent.LivingUpdateEvent)event); 
      if (event instanceof MouseEvent)
        onMouse((MouseEvent)event); 
      if (event instanceof AttackEntityEvent)
        onAttack((AttackEntityEvent)event); 
      if (event instanceof RenderGameOverlayEvent.Post)
        renderTextEvent((RenderGameOverlayEvent.Post)event); 
      if (event instanceof RenderWorldLastEvent)
        onRenderEvent((RenderWorldLastEvent)event); 
    } 
  }
  
  private void onRenderEvent(RenderWorldLastEvent e) {
    if (!this.selfDestructing && 
      this.mc.field_71441_e != null)
      for (Module mod : ModuleManager.getModules()) {
        if (mod.getState())
          mod.onRenderEvent(e); 
      }  
  }
  
  private void onKeyPress(InputEvent.KeyInputEvent e) {
    try {
      if (!this.selfDestructing) {
        if (Keyboard.getEventKey() == 0)
          return; 
        if (!Keyboard.getEventKeyState())
          return; 
        for (Module mod : ModuleManager.getModules()) {
          if (mod.getKey() == Keyboard.getEventKey())
            mod.setState(!mod.getState()); 
        } 
      } 
    } catch (Exception exception) {}
  }
  
  private void onClientTick(TickEvent.ClientTickEvent e) {
    if (!this.selfDestructing)
      for (Module mod : ModuleManager.getModules()) {
        if (mod.getState())
          mod.onClientTick(e); 
      }  
  }
  
  private void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
    if (!this.selfDestructing) {
      if (!(e.entity instanceof EntityPlayerSP))
        return; 
      for (Module mod : ModuleManager.getModules()) {
        if (mod.getState())
          mod.onLivingUpdate((EntityPlayerSP)e.entity); 
      } 
    } 
  }
  
  private void onRender(TickEvent.RenderTickEvent e) {
    if (!this.selfDestructing) {
      if (this.mc.field_71441_e != null) {
        EntityRenderer entityRenderer = this.mc.field_71460_t;
        if (!(entityRenderer instanceof NiggerRenderer))
          this.mc.field_71460_t = (EntityRenderer)new NiggerRenderer(this.mc, this.mc.func_110442_L()); 
      } 
      if (this.mc.field_71441_e != null)
        for (Module mod : ModuleManager.getModules()) {
          if (mod.getState())
            mod.onRenderTick(e); 
        }  
    } 
  }
  
  public void setSelfDestructing(boolean selfDestructing) {
    this.selfDestructing = selfDestructing;
  }
  
  public boolean getSelfDestructing() {
    return this.selfDestructing;
  }
  
  private void onMouse(MouseEvent e) {
    if (!this.selfDestructing && 
      e.buttonstate && e.button == 0)
      this.lastClick = System.currentTimeMillis(); 
  }
  
  public long getLastClick() {
    return this.lastClick;
  }
  
  private void renderTextEvent(RenderGameOverlayEvent.Post e) {
    if (!this.selfDestructing) {
      if (e.type != RenderGameOverlayEvent.ElementType.TEXT)
        return; 
      if (this.mc.field_71441_e != null)
        for (Module mod : ModuleManager.getModules()) {
          if (mod.getState())
            try {
              mod.onRenderText(e);
            } catch (IOException iOException) {} 
        }  
    } 
  }
  
  private void onAttack(AttackEntityEvent e) {
    this.lastAttack = System.currentTimeMillis();
    if (e.target instanceof EntityOtherPlayerMP) {
      EntityOtherPlayerMP playerMP = (EntityOtherPlayerMP)e.target;
      lastAttackedEntityName = StringRegistry.register(playerMP.getDisplayNameString());
      timerUtil.reset();
    } 
  }
  
  public long getLastAttack() {
    return this.lastAttack;
  }
  
  public String getLastAttackedEntityName() {
    return lastAttackedEntityName;
  }
}