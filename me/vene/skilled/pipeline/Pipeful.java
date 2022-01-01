package me.vene.skilled.pipeline;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.vene.skilled.SkilledClient;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.world.World;

public class Pipeful extends ChannelDuplexHandler {
  private Minecraft mc = Minecraft.func_71410_x();
  
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof S19PacketEntityStatus) {
      S19PacketEntityStatus packet = (S19PacketEntityStatus)msg;
      if (packet.func_149160_c() == 2) {
        Entity entity = packet.func_149161_a((World)this.mc.field_71441_e);
        if (entity != null && entity instanceof EntityOtherPlayerMP && System.currentTimeMillis() - SkilledClient.getInstance().getEventManager().getLastAttack() <= 500L && ((EntityOtherPlayerMP)entity).getDisplayNameString().equals(SkilledClient.getInstance().getEventManager().getLastAttackedEntityName()))
          for (Module module : ModuleManager.getModules()) {
            if (module.getState())
              module.onHurtAnimation(); 
          }  
      } 
    } else if (msg instanceof S18PacketEntityTeleport) {
      S18PacketEntityTeleport packet = (S18PacketEntityTeleport)msg;
      for (Module module : ModuleManager.getModules()) {
        if (module.getState())
          try {
            packet = module.onEntityTeleport(packet);
          } catch (Exception exception) {} 
      } 
      msg = packet;
    } 
    super.channelRead(ctx, msg);
  }
  
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    super.write(ctx, msg, promise);
  }
}