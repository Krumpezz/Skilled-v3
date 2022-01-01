package me.vene.skilled.modules.mods.other;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import me.vene.skilled.gui.GUI;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.io.IOUtils;

public class BedwarsStats extends Module {
  public static String unformattedMessage;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private ArrayList<String> playerArray = new ArrayList<String>();
  
  public static String apiKey = "dd5cdbea-0e37-4b23-bccd-d66e8df643c0";
  
  private long nextDelay;
  
  protected long lastMS;
  
  HashMap<String, String> localStorage = new HashMap<String, String>();
  
  private String quit = new String(new char[] { ' ', 'h', 'a', 's', ' ', 'q', 'u', 'i', 't', '!' });
  
  private String joined = new String(new char[] { 
        ' ', 'h', 'a', 's', ' ', 'j', 'o', 'i', 'n', 'e', 
        'd' });
  
  public BedwarsStats() throws IOException {
    super(StringRegistry.register("Bedwars Stats"), 0, Category.O);
  }
  
  public void onEnable() {
    updatebefore();
  }
  
  public static double roundAvoid(double value, int places) {
    double scale = Math.pow(10.0D, places);
    return Math.round(value * scale) / scale;
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
  
  @SubscribeEvent
  public void onChatMessageRecieved(ClientChatReceivedEvent event) {
    if (this.mc.field_71439_g != null || this.mc.field_71439_g != null) {
      String message = event.message.func_150260_c();
      if (message.contains("Your new API key is "))
        apiKey = message.split("Your new API key is ")[1]; 
    } 
  }
  
  @SubscribeEvent
  public void onRender(TickEvent.RenderTickEvent e) throws IOException {
    if (hasTimePassedMS(1000L)) {
      updatebefore();
      this.playerArray.clear();
      for (Object object : this.mc.field_71441_e.field_72996_f) {
        if (!(object instanceof net.minecraft.entity.player.EntityPlayer))
          continue; 
        EntityLivingBase entity = (EntityLivingBase)object;
        String player = entity.func_145748_c_().func_150260_c();
        if (this.localStorage.get(player) != null) {
          this.playerArray.add(this.localStorage.get(player));
          continue;
        } 
        JsonObject obj = new JsonObject();
        JsonParser parser = new JsonParser();
        String uuid = IOUtils.toString(new URL("https://api.mojang.com/users/profiles/minecraft/" + player));
        obj = (JsonObject)parser.parse(uuid);
        uuid = obj.get("id").getAsString();
        this;
        String response = IOUtils.toString(new URL("https://api.hypixel.net/player?key=" + apiKey + "&uuid=" + uuid));
        obj = (JsonObject)parser.parse(response);
        if (obj.get("player") != null) {
          JsonObject playerJson = obj.get("player").getAsJsonObject();
          JsonObject stats = playerJson.get("stats").getAsJsonObject();
          JsonObject bedwars = stats.get("Bedwars").getAsJsonObject();
          double fkdr = 0.0D;
          int finalDeaths = 1;
          int finalKills = 0;
          finalDeaths = bedwars.get("final_deaths_bedwars").getAsInt();
          finalKills = bedwars.get("final_kills_bedwars").getAsInt();
          fkdr = finalKills / finalDeaths;
          JsonObject achievements = playerJson.get("achievements").getAsJsonObject();
          int star = 0;
          if (achievements.get("bedwars_level") != null)
            star = achievements.get("bedwars_level").getAsInt(); 
          this.playerArray.add("[" + star + "] " + player + " FKDR: " + roundAvoid(fkdr, 2));
          this.localStorage.put(player, "[" + star + "] " + player + " FKDR: " + roundAvoid(fkdr, 2));
        } 
      } 
    } 
    for (int i = 0; i < this.playerArray.size(); i++)
      (Minecraft.func_71410_x()).field_71466_p.func_78276_b(((String)this.playerArray.get(i)).toString(), GUI.arrayXPos + 2, GUI.arrayYPos + 9 * i, -1); 
  }
}