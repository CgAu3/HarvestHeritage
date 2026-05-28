package me.theabab2333.harvestheritage.datagen.lang;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
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
        addItem(ModItems.GRAPE, "Grape");
        addItem(ModItems.ZZZZ, "zzzz");
        addItem(ModItems.SCAFFOLDING_CROP_STAND_BLOCK_ITEM, "Hanging crop racks");
        addItem(ModItems.FRIED_SEEDSACK, "Fried Seed Sack");
        addItem(ModItems.KFC, "Crazy Thursday");
        addBlock(ModBlocks.ACTIVAOR_BLOCK, "Strange Activator");
        addBlock(ModBlocks.TEST_BLOCK, "Test Block");
        addBlock(ModBlocks.CROP_STAND_BLOCK, "Crop Stand");
        addBlock(ModBlocks.SCAFFOLDING_CROP_STAND_BLOCK, "Hanging crop racks");
        add(
            "item.harvestheritage.zzzz.tooltip",
            "It's so weird. There are already plenty of mods with similar content, so should I still play it?"
        );
        add("item.harvestheritage.magnifying_glass.tooltip", "Can you see crop attributes and seeds...?");
        add("modmenu.nameTranslation.harvestheritage", "Harvest Heritage");
        add("creativetab.harvestheritage.seed_packet", "Harvest Heritage：Seed Packet");
        add("gui.harvestheritage.tooltip.shift", "Hold down [Shift] to view information");
        add(
            "item.harvestheritage.unknown_seed.tooltip",
            "Maybe you need to throw it on the ground and look at it with a magnifying glass..."
        );
        add("item.harvestheritage.grass_shear.tooltip", "Try using this to destroy some grass?");
        add("item.harvestheritage.seed.tooltip.fail", "Sorry, this item does not have a seed component");
        add("item.harvestheritage.seed.tooltip.seed", "Seed: %s");
        add("item.harvestheritage.seed.tooltip.stage", "Seed Growth Stage：%s");
        add("item.harvestheritage.seed_packet.tooltip.result", "Output: %s");
        add("item.harvestheritage.seed_packet.tooltip.speed", "Growth Speed: %s");
        add("item.harvestheritage.seed_packet.tooltip.output", "Output Amount: %s");
        add("block.harvestheritage.crop_stand.tooltip.stage", "Current Growth Stage：%s");
        add("block.harvestheritage.crop_stand.tooltip.1", "Crop stand, can generally be planted on farmland");
        add("block.harvestheritage.crop_stand.tooltip.2", "Right-click the crop stand with a seed packet to plant crops");
        add(
            "block.harvestheritage.crop_stand.tooltip.3",
            "When crops mature, use grass shears to get seeds, or right-click directly to harvest the produce"
        );
        add(
            "block.harvestheritage.crop_stand.tooltip.4",
            "When crops are mature, if there are other mature crop stands two blocks away in the north, south, east, or west directions, crossbreeding may occur at the empty crop stand in the middle..."
        );
        add("block.harvestheritage.crop_stand.tooltip.5", "Of course, you can also breed within the same type");
        add(
            "block.harvestheritage.crop_stand.tooltip.6",
            "Crop attributes depend on both crop stands; there is a chance of increase and a chance of decrease"
        );
        add("block.harvestheritage.scaffolding_crop_stand.tooltip.1", "Right-click scaffolding with a seed packet to convert");
        add(
            "block.harvestheritage.scaffolding_crop_stand.tooltip.2",
            "Cannot crossbreed, but can be harvested. Suitable for large-scale planting; recommended a few blocks above the ground"
        );
        add("block.harvestheritage.activator.tooltip", "Random ticks are about to accelerate");
        add("jei.harvestheritage.find", "Let me see see");
        add("jei.harvestheritage.hybrid", "Hybrid");
    }
}
