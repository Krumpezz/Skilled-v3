package me.vene.skilled.command.commands;

import me.vene.skilled.SkilledClient;
import me.vene.skilled.command.Command;

public class FriendCommand implements Command {
  public String getCommandName() {
    return "friend";
  }
  
  public void execute(SkilledClient skilledClient, String[] args) {
    try {
      if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove") && args.length == 2)
        return; 
      if (args[1].length() > 16)
        return; 
      if (args[0].equalsIgnoreCase("add")) {
        if (!skilledClient.getFriendManager().isFriend(args[1]))
          skilledClient.getFriendManager().addFriend(args[1]); 
      } else if (args[0].equalsIgnoreCase("remove") && 
        skilledClient.getFriendManager().isFriend(args[1])) {
        skilledClient.getFriendManager().removeFriend(args[1]);
      } 
    } catch (Exception exception) {}
  }
}