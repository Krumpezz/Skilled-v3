package me.vene.skilled.modules;

import java.util.ArrayList;
import me.vene.skilled.modules.mods.combat.AimAssist;
import me.vene.skilled.modules.mods.combat.AutoClicker;
import me.vene.skilled.modules.mods.combat.Delay17;
import me.vene.skilled.modules.mods.combat.HitBoxes;
import me.vene.skilled.modules.mods.combat.KillAura;
import me.vene.skilled.modules.mods.combat.Reach;
import me.vene.skilled.modules.mods.combat.Sprint;
import me.vene.skilled.modules.mods.combat.Velocity;
import me.vene.skilled.modules.mods.main.CombatGUI;
import me.vene.skilled.modules.mods.main.OtherGUI;
import me.vene.skilled.modules.mods.main.PlayerGUI;
import me.vene.skilled.modules.mods.main.RenderGUI;
import me.vene.skilled.modules.mods.main.UtilityGUI;
import me.vene.skilled.modules.mods.other.ClickGUI;
import me.vene.skilled.modules.mods.other.SelfDestruct;
import me.vene.skilled.modules.mods.other.SumoFences;
import me.vene.skilled.modules.mods.player.BlockFly;
import me.vene.skilled.modules.mods.player.FastPlace;
import me.vene.skilled.modules.mods.player.Fly;
import me.vene.skilled.modules.mods.player.SpeedBridge;
import me.vene.skilled.modules.mods.player.TimerModule;
import me.vene.skilled.modules.mods.player.WTap;
import me.vene.skilled.modules.mods.render.BlockESP;
import me.vene.skilled.modules.mods.render.Chams;
import me.vene.skilled.modules.mods.render.Fullbright;
import me.vene.skilled.modules.mods.render.Nametags;
import me.vene.skilled.modules.mods.render.PlayerESP;
import me.vene.skilled.modules.mods.render.StorageESP;
import me.vene.skilled.modules.mods.render.Tracers;
import me.vene.skilled.modules.mods.utility.Array;
import me.vene.skilled.modules.mods.utility.BedNuker;
import me.vene.skilled.modules.mods.utility.InfoTab;
import me.vene.skilled.modules.mods.utility.Refill;
import me.vene.skilled.modules.mods.utility.ThrowPot;

public class ModuleManager {
  private static ArrayList<Module> modules = new ArrayList<Module>();
  
  public static ArrayList<Module> getModules() {
    return modules;
  }
  
  static {
    modules.add(new ClickGUI());
    modules.add(new CombatGUI());
    modules.add(new AimAssist());
    modules.add(new AutoClicker());
    modules.add(new Array());
    modules.add(new BlockESP());
    modules.add(new Chams());
    modules.add(new FastPlace());
    modules.add(new HitBoxes());
    modules.add(new InfoTab());
    modules.add(new OtherGUI());
    modules.add(new PlayerESP());
    modules.add(new PlayerGUI());
    modules.add(new Reach());
    modules.add(new RenderGUI());
    modules.add(new SelfDestruct());
    modules.add(new SpeedBridge());
    modules.add(new StorageESP());
    modules.add(new ThrowPot());
    modules.add(new Tracers());
    modules.add(new UtilityGUI());
    modules.add(new Velocity());
    modules.add(new WTap());
    modules.add(new Fullbright());
    modules.add(new Sprint());
    modules.add(new Nametags());
    modules.add(new TimerModule());
    modules.add(new BedNuker());
    modules.add(new Refill());
    modules.add(new KillAura());
    modules.add(new Fly());
    modules.add(new SumoFences());
    modules.add(new BlockFly());
    modules.add(new Delay17());
  }
  
  public static void clearShit() {
    modules.clear();
  }
}