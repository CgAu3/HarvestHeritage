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
        addItem(ModItems.UNKNOWN_SEED, "未知种子");
        addItem(ModItems.MAGNIFYING_GLASS, "放大镜");
        addItem(ModItems.KNOWN_SEED, "已知种子");
        addBlock(ModBlocks.TEST_BLOCK, "测试方块");
        add("modmenu.nameTranslation.harvestheritage", "Harvest Heritage");
        add("item.harvestheritage.unknown_seed.tooltip", "或许需要丢在地上用放大镜看看...");
        add("item.harvestheritage.grass_shear.tooltip", "用这个破坏点草试试?");
        add("item.harvestheritage.grass_shear.tooltip", "用这个破坏点草试试?");
    }
}
