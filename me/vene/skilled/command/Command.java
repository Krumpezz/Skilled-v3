package me.vene.skilled.command;

import me.vene.skilled.SkilledClient;

public interface Command {
  String getCommandName();
  
  void execute(SkilledClient paramSkilledClient, String[] paramArrayOfString);
}