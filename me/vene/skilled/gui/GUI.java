package me.vene.skilled.gui;

import java.util.ArrayList;
import java.util.Iterator;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector4f;
import me.vene.skilled.SkilledClient;
import me.vene.skilled.gui.component.Component;
import me.vene.skilled.gui.component.Frame;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.main.CombatGUI;
import me.vene.skilled.modules.mods.main.OtherGUI;
import me.vene.skilled.modules.mods.main.PlayerGUI;
import me.vene.skilled.modules.mods.main.RenderGUI;
import me.vene.skilled.modules.mods.main.UtilityGUI;
import me.vene.skilled.modules.mods.other.ClickGUI;
import me.vene.skilled.modules.mods.utility.Array;
import me.vene.skilled.modules.mods.utility.InfoTab;
import me.vene.skilled.utilities.RenderUtil;
import me.vene.skilled.utilities.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUI extends GuiScreen {
  private ArrayList<Frame> frames = new ArrayList<Frame>();
  
  public static int arrayXPos = 0;
  
  public static int arrayYPos = 0;
  
  public static int mouseX;
  
  public static int mouseY;
  
  public static boolean renderShit = false;
  
  public static boolean renderInfo = false;
  
  public static int infoXPos = 0;
  
  public static int infoYPos = 0;
  
  public static Frame frameful = null;
  
  private Frame frameYes = null;
  
  private Frame consoleYes = null;
  
  private boolean firstOpen = false;
  
  private boolean firstYes = false;
  
  private boolean firstFul = false;
  
  private boolean firstMain = false;
  
  private boolean firstFriend = false;
  
  private boolean firstRender = false;
  
  private boolean firstUtil = false;
  
  public GUI() {
    int frameX = 5;
    for (Category category : Category.values()) {
      Frame frame = new Frame(category);
      frame.setX(frameX);
      this.frames.add(frame);
      frameX += frame.getWidth() + 1;
    } 
  }
  
  public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
    TextUtil font = (SkilledClient.getInstance()).bebas_font;
    func_146276_q_();
    String brian = new String(new char[] { 
          'S', 'k', 'i', 'l', 'l', 'e', 'd', ' ', 'C', 'l', 
          'i', 'e', 'n', 't', ' ', 'v', '3', ' ', 'B', 'U', 
          'I', 'L', 'D', ' ', 'S', 'n', 'a', 'k', 'e' });
    GL11.glPushMatrix();
    GL11.glScalef(2.0F, 2.0F, 2.0F);
    GL11.glPopMatrix();
    GUI.mouseX = mouseX;
    GUI.mouseY = mouseY;
    for (Frame frame : this.frames) {
      frame.updatePosition(mouseX, mouseY);
      for (Component comp : frame.getComponents())
        comp.updateComponent(mouseX, mouseY); 
      if (frame.category.getID() == 7) {
        if (!this.firstMain) {
          this.firstMain = true;
          frame.setOpen(true);
        } 
        this.frameYes = frame;
        frame.renderFrame();
        RenderUtil.drawTexturedRectangle(new ResourceLocation("1341faxdvf3"), (frame.getX() + frame.getWidth() / 2 - 40), (frame.getY() - 2), 80.0F, 17.0F, ClickGUI.getColor());
      } 
      if (frame.category.getID() == 0 && 
        Module.getModule(CombatGUI.class).getState()) {
        frame.renderFrame();
        RenderUtil.drawTexturedRectangle(new ResourceLocation("5hajfah432"), (frame.getX() + 1), (frame.getY() - 2), 22.0F, 14.0F, ClickGUI.getColor());
      } 
      if (frame.category.getID() == 2 && 
        Module.getModule(OtherGUI.class).getState())
        frame.renderFrame(); 
      if (frame.category.getID() == 3 && 
        Module.getModule(PlayerGUI.class).getState()) {
        frame.renderFrame();
        RenderUtil.drawTexturedRectangle(new ResourceLocation("7wjiu482ab"), (frame.getX() + 1), (frame.getY() - 2), 14.0F, 14.0F, ClickGUI.getColor());
      } 
      if (frame.category.getID() == 4) {
        if (!this.firstRender) {
          this.firstRender = true;
          frame.setX(this.frameYes.getX() + 358);
          frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 60);
        } 
        if (Module.getModule(RenderGUI.class).getState()) {
          frame.renderFrame();
          RenderUtil.drawTexturedRectangle(new ResourceLocation("3fkahj23314"), (frame.getX() + 1), (frame.getY() - 2), 18.0F, 14.0F, ClickGUI.getColor());
        } 
      } 
      if (frame.category.getID() == 5) {
        if (!this.firstUtil) {
          this.firstUtil = true;
          frame.setX(this.frameYes.getX() + 358);
          frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 160);
        } 
        if (Module.getModule(UtilityGUI.class).getState()) {
          frame.renderFrame();
          RenderUtil.drawTexturedRectangle(new ResourceLocation("19t8u984d"), (frame.getX() + 1), (frame.getY() - 2), 14.0F, 14.0F, ClickGUI.getColor());
        } 
      } 
      if (frame.category.getID() == 1) {
        if (!this.firstYes) {
          this.firstYes = true;
          frame.setX(this.frameYes.getX());
          frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 190);
          frame.setOpen(true);
        } 
        arrayXPos = frame.getX();
        arrayYPos = frame.getY();
        renderShit = frame.isOpen();
        if (renderShit && !Module.getModule(Array.class).getState()) {
          GL11.glPushMatrix();
          (Minecraft.func_71410_x()).field_71466_p.func_78276_b("Arraylist is disabled!", arrayXPos + 2, arrayYPos + 16, -1);
          GL11.glPopMatrix();
        } 
        frame.renderFrame();
      } 
      if (frame.category.getID() == 6) {
        if (!this.firstOpen) {
          this.firstOpen = true;
          frame.setX(this.frameYes.getX());
          frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 120);
        } 
        if (Module.getModule(InfoTab.class).getState()) {
          infoXPos = frame.getX();
          infoYPos = frame.getY();
          renderInfo = frame.isOpen();
          frameful = frame;
        } 
        frame.renderFrame();
      } 
      if (frame.category.getID() == 8) {
        if (!this.firstFul) {
          this.firstFul = true;
          this.consoleYes = frame;
          frame.setOpen(true);
          frame.setX(this.frameYes.getX());
          frame.setY(this.frameYes.getY() + this.frameYes.getHeight() + 80);
        } 
        frame.renderFrame();
      } 
      if (frame.category.getID() == 9) {
        if (!this.firstFriend) {
          this.firstFriend = true;
          frame.setOpen(true);
          frame.setX(this.consoleYes.getX() + this.consoleYes.getWidth() + 5);
          frame.setY(this.consoleYes.getY());
        } 
        if (frame.isOpen()) {
          GL11.glPushMatrix();
          if (SkilledClient.getInstance().getFriendManager().getFriendsList().isEmpty())
            (Minecraft.func_71410_x()).field_71466_p.func_78276_b("You dont have friends!", frame.getX() + 2, frame.getY() + 15, -1); 
          Iterator<String> i = SkilledClient.getInstance().getFriendManager().getFriendsList().iterator();
          int y = 18;
          while (i.hasNext()) {
            String friend = i.next();
            (Minecraft.func_71410_x()).field_71466_p.func_78276_b(friend, frame.getX() + 2, frame.getY() + y, -1);
            y += 10;
          } 
          GL11.glPopMatrix();
        } 
        frame.renderFrame();
      } 
    } 
    font.renderCenteredText("Test", new Vector2f((this.field_146294_l / 2), (this.field_146295_m / 2)), new Vector4f(255.0F, 255.0F, 0.0F, 0.0F), 20.0D);
  }
  
  protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
    for (Frame frame : this.frames) {
      if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
        frame.setDrag(true);
        frame.dragX = mouseX - frame.getX();
        frame.dragY = mouseY - frame.getY();
      } 
      if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1)
        frame.setOpen(!frame.isOpen()); 
      if (!frame.isOpen() || frame.getComponents().isEmpty())
        continue; 
      for (Component component : frame.getComponents())
        component.mouseClicked(mouseX, mouseY, mouseButton); 
    } 
  }
  
  protected void func_73869_a(char typedChar, int keyCode) {
    for (Frame frame : this.frames) {
      if (!frame.isOpen() || keyCode == 1 || frame.getComponents().isEmpty())
        continue; 
      for (Component component : frame.getComponents())
        component.keyTyped(typedChar, keyCode); 
    } 
    if (keyCode == 1)
      this.field_146297_k.func_147108_a(null); 
  }
  
  protected void func_146286_b(int mouseX, int mouseY, int state) {
    for (Frame frame : this.frames)
      frame.setDrag(false); 
  }
  
  public boolean func_73868_f() {
    return false;
  }
}