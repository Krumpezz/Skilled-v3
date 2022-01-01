package me.vene.skilled.autogg;

import java.io.IOException;
import java.net.URL;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.IOUtils;

public class AutoGG {
  public static String unformattedMessage;
  
  private String[] triggers;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public AutoGG() throws IOException {
    String rawTriggers = IOUtils.toString(new URL("https://gist.githubusercontent.com/minemanpi/72c38b0023f5062a5f3eba02a5132603/raw/triggers.txt"));
    this.triggers = rawTriggers.split("\n");
  }
  
  @SubscribeEvent
  public void onChat(ClientChatReceivedEvent event) throws InterruptedException {
    this;
    unformattedMessage = event.message.func_150260_c();
    this;
    this;
    unformattedMessage = EnumChatFormatting.func_110646_a(unformattedMessage);
    for (int i = 0; i < this.triggers.length; i++) {
      this;
      if (unformattedMessage.contains(this.triggers[i])) {
        this.mc.field_71439_g.func_71165_d("/achat gg");
        break;
      } 
    } 
  }
}