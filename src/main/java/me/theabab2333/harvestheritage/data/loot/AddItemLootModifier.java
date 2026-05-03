package me.theabab2333.harvestheritage.data.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.ApiStatus;

public class AddItemLootModifier extends LootModifier {
    @ApiStatus.Internal
    public static final MapCodec<AddItemLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> LootModifier.codecStart(inst)
        .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(mod -> mod.item))
        .apply(inst, AddItemLootModifier::new));

    @Getter
    private final Item item;

    protected AddItemLootModifier(LootItemCondition[] conditions, int priority, Item item) {
        super(conditions, priority);
        this.item = item;
    }

    public Item item() {
        return this.item;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(item.getDefaultInstance());
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
