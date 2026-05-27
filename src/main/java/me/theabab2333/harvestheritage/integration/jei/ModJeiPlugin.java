package me.theabab2333.harvestheritage.integration.jei;

import me.theabab2333.harvestheritage.HarvestHeritage;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.Identifier;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return HarvestHeritage.of("jei_plugin");
    }
}
