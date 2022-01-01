/* Yeah, don't know why on this world is this here */

package me.vene.skilled.renderer;

import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;

public class NiggerRenderer extends EntityRenderer {
  public NiggerRenderer(Minecraft minekraf, IResourceManager resourceManager) {
    super(minekraf, resourceManager);
  }
  
  public void func_78473_a(float dabfloat) {
    super.func_78473_a(dabfloat);
    for (Module mod : ModuleManager.getModules()) {
      if (mod.getState())
        mod.onMouseOver(dabfloat); 
    } 
  }
}