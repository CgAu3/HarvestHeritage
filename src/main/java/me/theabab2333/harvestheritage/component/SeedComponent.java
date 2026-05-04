package me.theabab2333.harvestheritage.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

import java.util.List;

public record SeedComponent(Holder<Item> seed, List<Holder<Item>> result) {
    public static final Codec<SeedComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Item.CODEC.fieldOf("seed").forGetter(SeedComponent::seed),
        Item.CODEC.listOf().fieldOf("result").forGetter(SeedComponent::result)
    ).apply(inst, SeedComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SeedComponent> STREAM_CODEC = StreamCodec.composite(
        Item.STREAM_CODEC,
        SeedComponent::seed,
        Item.STREAM_CODEC.apply(ByteBufCodecs.list()),
        SeedComponent::result,
        SeedComponent::new
    );

    public static SeedComponent createSeed(Holder<Item> seed, List<Holder<Item>> result) {
        return new SeedComponent(seed, result);
    }
}
