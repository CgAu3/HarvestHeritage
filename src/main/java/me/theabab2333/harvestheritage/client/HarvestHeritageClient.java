package me.theabab2333.harvestheritage.client;

import me.theabab2333.harvestheritage.HarvestHeritage;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = HarvestHeritage.MODID, dist = Dist.CLIENT)
public class HarvestHeritageClient {
    public HarvestHeritageClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
