package me.vene.skilled.command.commands;

import me.vene.skilled.SkilledClient;
import me.vene.skilled.command.Command;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.combat.AutoClicker;

public class LimitCommand implements Command {
  public String getCommandName() {
    return "item";
  }
  
  public void execute(SkilledClient skilledClient, String[] args) {
    try {
      if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove") && args.length == 2)
        return; 
      AutoClicker autoClicker = (AutoClicker)Module.getModule(AutoClicker.class);
      if (args[0].equalsIgnoreCase("add")) {
        autoClicker.addToList(args[1]);
      } else if (args[0].equalsIgnoreCase("remove")) {
        autoClicker.removeFromList(args[1]);
      } 
    } catch (Exception exception) {}
  }
}