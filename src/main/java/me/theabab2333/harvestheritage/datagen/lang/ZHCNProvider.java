package me.theabab2333.harvestheritage.datagen.lang;

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
        addItem(ModItems.SEED_PACKET, "种子袋");
        addBlock(ModBlocks.TEST_BLOCK, "测试方块");
        addBlock(ModBlocks.CROP_STAND_BLOCK, "作物架");
        add("modmenu.nameTranslation.harvestheritage", "Harvest Heritage");
        add("item.harvestheritage.unknown_seed.tooltip", "或许需要丢在地上用放大镜看看...");
        add("item.harvestheritage.grass_shear.tooltip", "用这个破坏点草试试?");
        add("item.harvestheritage.seed_packet.tooltip.fail", "抱歉，这个物品没有种子组件");
        add("item.harvestheritage.seed_packet.tooltip.seed", "种子：%s");
        add("item.harvestheritage.seed_packet.tooltip.result", "产出：%s");
        add("item.harvestheritage.seed_packet.tooltip.speed", "生长速度：%s");
        add("item.harvestheritage.seed_packet.tooltip.output", "产出数量：%s");
        add("item.harvestheritage.seed_packet.tooltip.iterate", "迭代次数：%s");
    }
}
