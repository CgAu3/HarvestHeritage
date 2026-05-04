package me.theabab2333.harvestheritage.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

import java.util.List;

public record SeedPacketComponent(Holder<Item> seed, List<Holder<Item>> result, int speed, int output) {
    public static final Codec<SeedPacketComponent> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Item.CODEC.fieldOf("seed").forGetter(SeedPacketComponent::seed),
        Item.CODEC.listOf().fieldOf("result").forGetter(SeedPacketComponent::result),
        Codec.INT.fieldOf("speed").forGetter(SeedPacketComponent::speed),
        Codec.INT.fieldOf("output").forGetter(SeedPacketComponent::output)
    ).apply(inst, SeedPacketComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SeedPacketComponent> STREAM_CODEC = StreamCodec.composite(
        Item.STREAM_CODEC,
        SeedPacketComponent::seed,
        Item.STREAM_CODEC.apply(ByteBufCodecs.list()),
        SeedPacketComponent::result,
        ByteBufCodecs.INT,
        SeedPacketComponent::speed,
        ByteBufCodecs.INT,
        SeedPacketComponent::output,
        SeedPacketComponent::new
    );

    public static SeedPacketComponent createSeedPacket(Holder<Item> seed, List<Holder<Item>> result, int speed, int output) {
        return new SeedPacketComponent(seed, result, speed, output);
    }

    public static SeedPacketComponent undateSeedPacket(SeedPacketComponent component, int speed, int output) {
        return new SeedPacketComponent(component.seed(), component.result, speed, output);
    }
}
