package me.vene.skilled.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringRegistry {
  private static List<String> strings = new ArrayList<String>();
  
  private static List<String> memelist = new ArrayList<String>();
  
  public static String register(String string) {
    if (!strings.contains(string))
      strings.add(string); 
    return string;
  }
  
  public static void registerObject(Object object) {
    if (object instanceof String) {
      register((String)object);
      return;
    } 
    for (Field f : object.getClass().getDeclaredFields()) {
      try {
        if (f.getType().equals(String.class))
          register(getField(object, f)); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private static <T> T getField(Object object, Field f) {
    try {
      boolean modified = !f.isAccessible();
      if (modified)
        f.setAccessible(true); 
      T o = (T)f.get(object);
      if (modified)
        f.setAccessible(false); 
      return o;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public static void cleanup() {
    try {
      Field field = String.class.getDeclaredField(new String(new char[] { 'v', 'a', 'l', 'u', 'e' }));
      field.setAccessible(true);
      for (Iterator<String> var1 = strings.iterator(); var1.hasNext(); s = null) {
        String s = var1.next();
        if (s != null) {
          char[] c = (char[])field.get(s);
          for (int i = 0; i < s.length(); i++)
            c[i] = Character.MIN_VALUE; 
        } 
      } 
      for (Iterator<String> var2 = memelist.iterator(); var2.hasNext(); meme = null) {
        String meme = var2.next();
        if (meme != null) {
          char[] xd = (char[])field.get(meme);
          for (int men = 0; men < meme.length(); men++)
            xd[men] = Character.MIN_VALUE; 
        } 
      } 
      strings.clear();
      memelist.clear();
    } catch (Exception exception) {}
    System.gc();
    System.runFinalization();
    System.gc();
    System.runFinalization();
    System.gc();
    System.runFinalization();
    System.gc();
    System.runFinalization();
  }
}