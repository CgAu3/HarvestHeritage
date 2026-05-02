package me.theabab2333.harvestheritage.data.lang;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModBlocks;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ZHCNProvider extends LanguageProvider {
    public ZHCNProvider(PackOutput output) {
        super(output, HarvestHeritage.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.GRASS_SHEAR, "除草剪");
        addBlock(ModBlocks.EXAMPLE_BLOCK, "测试方块");
        add("modmenu.nameTranslation.harvestheritage", "Harvest Heritage");
    }
}
