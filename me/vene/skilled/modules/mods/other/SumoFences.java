package me.vene.skilled.modules.mods.other;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class SumoFences extends Module {
  private Timer t;
  
  private final List<String> m = Arrays.asList(new String[] { "Sumo", "Space Mine", "White Crystal" });
  
  private static final List<BlockPos> f_p = Arrays.asList(new BlockPos[] { 
        new BlockPos(9, 65, -2), new BlockPos(9, 65, -1), new BlockPos(9, 65, 0), new BlockPos(9, 65, 1), new BlockPos(9, 65, 2), new BlockPos(9, 65, 3), new BlockPos(8, 65, 3), new BlockPos(8, 65, 4), new BlockPos(8, 65, 5), new BlockPos(7, 65, 5), 
        new BlockPos(7, 65, 6), new BlockPos(7, 65, 7), new BlockPos(6, 65, 7), new BlockPos(5, 65, 7), new BlockPos(5, 65, 8), new BlockPos(4, 65, 8), new BlockPos(3, 65, 8), new BlockPos(3, 65, 9), new BlockPos(2, 65, 9), new BlockPos(1, 65, 9), 
        new BlockPos(0, 65, 9), new BlockPos(-1, 65, 9), new BlockPos(-2, 65, 9), new BlockPos(-3, 65, 9), new BlockPos(-3, 65, 8), new BlockPos(-4, 65, 8), new BlockPos(-5, 65, 8), new BlockPos(-5, 65, 7), new BlockPos(-6, 65, 7), new BlockPos(-7, 65, 7), 
        new BlockPos(-7, 65, 6), new BlockPos(-7, 65, 5), new BlockPos(-8, 65, 5), new BlockPos(-8, 65, 4), new BlockPos(-8, 65, 3), new BlockPos(-9, 65, 3), new BlockPos(-9, 65, 2), new BlockPos(-9, 65, 1), new BlockPos(-9, 65, 0), new BlockPos(-9, 65, -1), 
        new BlockPos(-9, 65, -2), new BlockPos(-9, 65, -3), new BlockPos(-8, 65, -3), new BlockPos(-8, 65, -4), new BlockPos(-8, 65, -5), new BlockPos(-7, 65, -5), new BlockPos(-7, 65, -6), new BlockPos(-7, 65, -7), new BlockPos(-6, 65, -7), new BlockPos(-5, 65, -7), 
        new BlockPos(-5, 65, -8), new BlockPos(-4, 65, -8), new BlockPos(-3, 65, -8), new BlockPos(-3, 65, -9), new BlockPos(-2, 65, -9), new BlockPos(-1, 65, -9), new BlockPos(0, 65, -9), new BlockPos(1, 65, -9), new BlockPos(2, 65, -9), new BlockPos(3, 65, -9), 
        new BlockPos(3, 65, -8), new BlockPos(4, 65, -8), new BlockPos(5, 65, -8), new BlockPos(5, 65, -7), new BlockPos(6, 65, -7), new BlockPos(7, 65, -7), new BlockPos(7, 65, -6), new BlockPos(7, 65, -5), new BlockPos(8, 65, -5), new BlockPos(8, 65, -4), 
        new BlockPos(8, 65, -3), new BlockPos(9, 65, -3) });
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private IBlockState f = Blocks.field_180407_aO.func_176223_P();
  
  private int ticks = 0;
  
  public SumoFences() {
    super("Sumo Fences", 0, Category.O);
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    this.ticks++;
    if (this.ticks % 10 == 0) {
      this.ticks = 0;
      if (is())
        for (BlockPos p : f_p) {
          for (int i = 0; i < 3.0D; i++) {
            BlockPos p2 = new BlockPos(p.func_177958_n(), p.func_177956_o() + i, p.func_177952_p());
            if (this.mc.field_71441_e.func_180495_p(p2).func_177230_c() == Blocks.field_150350_a)
              this.mc.field_71441_e.func_175656_a(p2, this.f); 
          } 
        }  
    } 
  }
  
  public List<String> getPlayersFromScoreboard() {
    List<String> lines = new ArrayList<String>();
    if (this.mc.field_71441_e == null)
      return lines; 
    Scoreboard scoreboard = this.mc.field_71441_e.func_96441_U();
    if (scoreboard == null)
      return lines; 
    ScoreObjective objective = scoreboard.func_96539_a(1);
    if (objective != null) {
      Collection<Score> scores = scoreboard.func_96534_i(objective);
      List<Score> list = new ArrayList<Score>();
      Iterator<Score> var5 = scores.iterator();
      while (var5.hasNext()) {
        Score score = var5.next();
        if (score != null && score.func_96653_e() != null && !score.func_96653_e().startsWith("#"))
          list.add(score); 
      } 
      if (list.size() > 15) {
        scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
      } else {
        scores = list;
      } 
      var5 = scores.iterator();
      while (var5.hasNext()) {
        Score score = var5.next();
        ScorePlayerTeam team = scoreboard.func_96509_i(score.func_96653_e());
        lines.add(ScorePlayerTeam.func_96667_a((Team)team, score.func_96653_e()));
      } 
    } 
    return lines;
  }
  
  public void swing() {
    EntityPlayerSP p = this.mc.field_71439_g;
    int armSwingEnd = p.func_70644_a(Potion.field_76422_e) ? (6 - 1 + p.func_70660_b(Potion.field_76422_e).func_76458_c()) : (p.func_70644_a(Potion.field_76419_f) ? (6 + (1 + p.func_70660_b(Potion.field_76419_f).func_76458_c()) * 2) : 6);
    if (!p.field_82175_bq || p.field_110158_av >= armSwingEnd / 2 || p.field_110158_av < 0) {
      p.field_110158_av = -1;
      p.field_82175_bq = true;
    } 
  }
  
  @SubscribeEvent
  public void m(MouseEvent e) {
    if (e.buttonstate && (e.button == 0 || e.button == 1) && is()) {
      MovingObjectPosition mop = this.mc.field_71476_x;
      if (mop != null && mop.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
        int x = mop.func_178782_a().func_177958_n();
        int z = mop.func_178782_a().func_177952_p();
        for (BlockPos pos : f_p) {
          if (pos.func_177958_n() == x && pos.func_177952_p() == z) {
            e.setCanceled(true);
            if (e.button == 0)
              swing(); 
            Mouse.poll();
            break;
          } 
        } 
      } 
    } 
  }
  
  private boolean is() {
    for (String l : getPlayersFromScoreboard()) {
      String s = l;
      if (s.startsWith("Map:")) {
        if (this.m.contains(s.substring(5)))
          return true; 
        continue;
      } 
      if (s.equals("Mode: Sumo Duel"))
        return true; 
    } 
    return false;
  }
}