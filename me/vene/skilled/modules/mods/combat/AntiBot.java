ackage me.vene.skilled.modules.mods.combat;

import com.google.common.collect.Ordering;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiBot extends Module {
  public static List<EntityPlayer> bots = new ArrayList<EntityPlayer>();
  
  private static Minecraft mc = Minecraft.func_71410_x();
  
  public AntiBot() {
    super("AntiBot", 0, Category.C);
  }
  
  @SubscribeEvent
  public void onTick(TickEvent.PlayerTickEvent e) {
    if (mc.field_71439_g == null || mc.field_71441_e == null)
      return; 
    for (int k = 0; k < mc.field_71441_e.field_73010_i.size(); k++) {
      EntityPlayer ent = mc.field_71441_e.field_73010_i.get(k);
      List<EntityPlayer> tabList = getTabPlayerList();
      if (!bots.contains(ent) && !tabList.contains(ent)) {
        bots.add(ent);
      } else if (bots.contains(ent) && tabList.contains(ent)) {
        bots.remove(ent);
      } 
    } 
  }
  
  public void onDisable() {
    bots.clear();
  }
  
  private List<EntityPlayer> getTabPlayerList() {
    List<EntityPlayer> list;
    (list = new ArrayList<EntityPlayer>()).clear();
    Ordering<NetworkPlayerInfo> field_175252_a = field_175252_a();
    if (field_175252_a == null)
      return list; 
    List players = field_175252_a.sortedCopy(mc.field_71439_g.field_71174_a.func_175106_d());
    for (Object o : players) {
      NetworkPlayerInfo info = (NetworkPlayerInfo)o;
      if (info == null)
        continue; 
      list.add(mc.field_71441_e.func_72924_a(info.func_178845_a().getName()));
    } 
    return list;
  }
  
  private Ordering<NetworkPlayerInfo> field_175252_a() {
    try {
      Class<GuiPlayerTabOverlay> c = GuiPlayerTabOverlay.class;
      Field f = c.getField("field_175252_a");
      f.setAccessible(true);
      return (Ordering<NetworkPlayerInfo>)f.get(GuiPlayerTabOverlay.class);
    } catch (Exception e) {
      return null;
    } 
  }
}
