package me.vene.skilled.utilities;

import java.lang.reflect.Field;

public class ReflectionUtil {
  public static Object getFieldValue(String field_, Object object, Class<?> clazz) {
    Field field = null;
    try {
      field = clazz.getDeclaredField(field_);
      boolean access = field.isAccessible();
      if (!access)
        field.setAccessible(true); 
      Object obj = field.get(object);
      if (!access)
        field.setAccessible(false); 
      return obj;
    } catch (Exception exception) {
      return null;
    } 
  }
}