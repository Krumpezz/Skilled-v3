package me.vene.skilled.modules.mods.render;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.RenderUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class BlockESP extends Module {
  private static final int DELAY = 600;
  
  private List<BlockESPBlock> blockList = new ArrayList<BlockESPBlock>();
  
  private int searchTicks = 0;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private NumberValue range = new NumberValue("Range", 40.0D, 20.0D, 60.0D);
  
  private BooleanValue emerald = new BooleanValue("Emerald Ore", true);
  
  private BooleanValue diamond = new BooleanValue("Diamond Ore", true);
  
  private BooleanValue gold = new BooleanValue("Gold Ore", true);
  
  private BooleanValue lapis = new BooleanValue("Lapis Lazuli Ore", true);
  
  private BooleanValue redstone = new BooleanValue("Redstone Ore", true);
  
  private BooleanValue iron = new BooleanValue("Iron Ore", true);
  
  private BooleanValue coal = new BooleanValue("Coal Ore", true);
  
  private BooleanValue obsd = new BooleanValue("Obsidian", true);
  
  public BlockESP() {
    super("Block ESP", 0, Category.R);
    addValue(this.range);
    addOption(this.emerald);
    addOption(this.diamond);
    addOption(this.gold);
    addOption(this.lapis);
    addOption(this.redstone);
    addOption(this.iron);
    addOption(this.coal);
    addOption(this.obsd);
  }
  
  public void onEnable() {
    search();
  }
  
  public void onDisable() {
    this.blockList.clear();
    this.searchTicks = 0;
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (this.mc.field_71441_e == null) {
      this.searchTicks = 0;
      return;
    } 
    if (++this.searchTicks == 600) {
      this.searchTicks = 0;
      search();
    } 
  }
  
  public void onRenderEvent(RenderWorldLastEvent event) {
    if (this.blockList.isEmpty())
      return; 
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glLineWidth(1.0F);
    for (BlockESPBlock block : this.blockList) {
      Color color = block.getColor();
      GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
      String Xstring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '5', '_', 'b' });
      String Ystring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '6', '_', 'c' });
      String Zstring = new String(new char[] { 
            'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '2', 
            '3', '_', 'd' });
      double renderPosX = ((Double)GetFieldByReflection(RenderManager.class, this.mc.func_175598_ae(), new String[] { StringRegistry.register(Xstring) })).doubleValue();
      double renderPosY = ((Double)GetFieldByReflection(RenderManager.class, this.mc.func_175598_ae(), new String[] { StringRegistry.register(Ystring) })).doubleValue();
      double renderPosZ = ((Double)GetFieldByReflection(RenderManager.class, this.mc.func_175598_ae(), new String[] { StringRegistry.register(Zstring) })).doubleValue();
      double x = block.getX() - renderPosX;
      double y = block.getY() - renderPosY;
      double z = block.getZ() - renderPosZ;
      RenderUtil.drawOutlinedBoundingBox(AxisAlignedBB.func_178781_a(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
    } 
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
  }
  
  private void search() {
    this.blockList.clear();
    EntityPlayerSP player = this.mc.field_71439_g;
    WorldClient world = this.mc.field_71441_e;
    int range = (int)this.range.getValue();
    for (int y = range; y >= -range; y--) {
      for (int x = range; x >= -range; x--) {
        for (int z = range; z >= -range; z--) {
          int posX = (int)(player.field_70165_t + x);
          int posY = (int)(player.field_70163_u + y);
          int posZ = (int)(player.field_70161_v + z);
          Block block = world.func_180495_p(new BlockPos(posX, posY, posZ)).func_177230_c();
          int id = Block.func_149682_b(block);
          if (id == 129 && this.emerald.getState()) {
            this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.GREEN));
          } else if (id == 56 && this.diamond.getState()) {
            this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.CYAN));
          } else if (id == 14 && this.gold.getState()) {
            this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.YELLOW));
          } else if (id == 21 && this.lapis.getState()) {
            this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.BLUE));
          } else if ((id == 73 || id == 74) && this.redstone.getState()) {
            this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.RED));
          } else if (id == 15 && this.iron.getState()) {
            this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.PINK));
          } else if (id == 16 && this.coal.getState()) {
            this.blockList.add(new BlockESPBlock(posX, posY, posZ, Color.BLACK));
          } else if (id == 49 && this.obsd.getState()) {
            this.blockList.add(new BlockESPBlock(posX, posY, posZ, new Color(255, 0, 255)));
          } 
        } 
      } 
    } 
  }
  
  public static <T, E> T GetFieldByReflection(Class<? super E> classToAccess, E instance, String... fieldNames) {
    Field field = null;
    for (String fieldName : fieldNames) {
      try {
        field = classToAccess.getDeclaredField(fieldName);
      } catch (NoSuchFieldException noSuchFieldException) {}
      if (field != null)
        break; 
    } 
    if (field != null) {
      field.setAccessible(true);
      T fieldT = null;
      try {
        fieldT = (T)field.get(instance);
      } catch (IllegalArgumentException illegalArgumentException) {
      
      } catch (IllegalAccessException illegalAccessException) {}
      return fieldT;
    } 
    return null;
  }
  
  private class BlockESPBlock {
    private int x;
    
    private int y;
    
    private int z;
    
    private Color color;
    
    BlockESPBlock(int x, int y, int z, Color color) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.color = color;
    }
    
    private int getX() {
      return this.x;
    }
    
    private int getY() {
      return this.y;
    }
    
    private int getZ() {
      return this.z;
    }
    
    private Color getColor() {
      return this.color;
    }
  }
}