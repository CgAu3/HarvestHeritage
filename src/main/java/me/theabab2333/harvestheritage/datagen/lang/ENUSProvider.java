package me.theabab2333.harvestheritage.datagen.lang;

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
        addItem(ModItems.SEED_PACKET, "Seed Packet");
        addBlock(ModBlocks.TEST_BLOCK, "Test Block");
        addBlock(ModBlocks.CROP_STAND_BLOCK, "Crop Stand");
        add("modmenu.nameTranslation.harvestheritage", "Harvest Heritage");
        add(
            "item.harvestheritage.unknown_seed.tooltip",
            "Maybe you need to throw it on the ground and look at it with a magnifying glass..."
        );
        add("item.harvestheritage.grass_shear.tooltip", "Try using this to destroy some grass?");
        add("item.harvestheritage.seed_packet.tooltip.fail", "Sorry, this item does not have a seed component");
        add("item.harvestheritage.seed_packet.tooltip.seed", "Seed: %s");
        add("item.harvestheritage.seed_packet.tooltip.result", "Output: %s");
        add("item.harvestheritage.seed_packet.tooltip.speed", "Growth Speed: %s");
        add("item.harvestheritage.seed_packet.tooltip.output", "Output Amount: %s");
        add("item.harvestheritage.seed_packet.tooltip.iterate", "Iteration Count: %s");
    }
}
