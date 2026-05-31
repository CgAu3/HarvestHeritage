package me.theabab2333.harvestheritage.datagen.advancement;

import me.theabab2333.harvestheritage.HarvestHeritage;
import me.theabab2333.harvestheritage.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementSubProvider {
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        Advancement.Builder.advancement()
            .display(
                ModItems.KFC,
                Component.translatable("advancement.harvestheritage.kfc.title").withColor(0xf8d4d2),
                Component.translatable("advancement.harvestheritage.kfc.description").withColor(0xf8d4d2),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "consume_kfc",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                    provider.lookupOrThrow(Registries.ITEM),
                    ModItems.KFC.asItem()
                )
            )
            .save(consumer, HarvestHeritage.of("kfc"));

        Advancement.Builder.advancement()
            .display(
                ModItems.FRIED_SEEDSACK,
                Component.translatable("advancement.harvestheritage.fride_seedsack.title"),
                Component.translatable("advancement.harvestheritage.fride_seedsack.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .addCriterion(
                "consume_fride_seedsack",
                ConsumeItemTrigger.TriggerInstance.usedItem(
                    provider.lookupOrThrow(Registries.ITEM),
                    ModItems.FRIED_SEEDSACK.asItem()
                )
            )
            .save(consumer, HarvestHeritage.of("fride_seedsack"));
    }
}
