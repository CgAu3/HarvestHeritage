package me.theabab2333.harvestheritage.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

public record SeedComponent(Holder<Item> seed) {
    public static final Codec<SeedComponent> CODEC = RecordCodecBuilder.create(inst ->
        inst.group(
            Item.CODEC.fieldOf("seed").forGetter(SeedComponent::seed)
        ).apply(inst, SeedComponent::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SeedComponent> STREAM_CODEC = StreamCodec.composite(
        Item.STREAM_CODEC, SeedComponent::seed,
        SeedComponent::new
    );

    public static SeedComponent setSeed(Holder<Item> seed) {
        return new SeedComponent(seed);
    }
}
