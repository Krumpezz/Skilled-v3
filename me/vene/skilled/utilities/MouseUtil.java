package me.vene.skilled.utilities;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.lwjgl.input.Mouse;

public class MouseUtil {
  private static Field buttonStateField;
  
  private static Field buttonField;
  
  private static Field buttonsField;
  
  public static void sendClick(int button, boolean state) {
    MouseEvent mouseEvent = new MouseEvent();
    buttonField.setAccessible(true);
    try {
      buttonField.set(mouseEvent, Integer.valueOf(button));
    } catch (IllegalAccessException illegalAccessException) {}
    buttonField.setAccessible(false);
    buttonStateField.setAccessible(true);
    try {
      buttonStateField.set(mouseEvent, Boolean.valueOf(state));
    } catch (IllegalAccessException illegalAccessException) {}
    buttonStateField.setAccessible(false);
    MinecraftForge.EVENT_BUS.post((Event)mouseEvent);
    try {
      buttonsField.setAccessible(true);
      ByteBuffer buffer = (ByteBuffer)buttonsField.get((Object)null);
      buttonsField.setAccessible(false);
      buffer.put(button, (byte)(state ? 1 : 0));
    } catch (IllegalAccessException illegalAccessException) {}
  }
  
  static {
    try {
      buttonField = MouseEvent.class.getDeclaredField(StringRegistry.register(new String(new char[] { 'b', 'u', 't', 't', 'o', 'n' })));
    } catch (NoSuchFieldException noSuchFieldException) {}
    try {
      buttonStateField = MouseEvent.class.getDeclaredField(StringRegistry.register(new String(new char[] { 
                'b', 'u', 't', 't', 'o', 'n', 's', 't', 'a', 't', 
                'e' })));
    } catch (NoSuchFieldException noSuchFieldException) {}
    try {
      buttonsField = Mouse.class.getDeclaredField(StringRegistry.register(new String(new char[] { 'b', 'u', 't', 't', 'o', 'n', 's' })));
    } catch (NoSuchFieldException noSuchFieldException) {}
  }
}