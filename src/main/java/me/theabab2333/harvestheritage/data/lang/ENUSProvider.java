package me.theabab2333.harvestheritage.data.lang;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ENUSProvider extends LanguageProvider {

    public ENUSProvider(PackOutput output) {
        super(output, HarvestHeritage.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.GRASS_SHEAR, "Grass Shear");
        addItem(ModItems.UNKNOWN_SEED, "Unknown Seed");
        addItem(ModItems.MAGNIFYING_GLASS, "Magnifying Glass");
        addItem(ModItems.KNOWN_SEED, "Known Seed");
        addBlock(ModBlocks.TEST_BLOCK, "Test Block");
        add("modmenu.nameTranslation.harvestheritage", "Harvest Heritage");
        add("item.harvestheritage.unknown_seed.tooltip", "Maybe you need to throw it on the ground and look at it with a magnifying glass...");
        add("item.harvestheritage.grass_shear.tooltip", "Try using this to destroy some grass?");
    }
}
