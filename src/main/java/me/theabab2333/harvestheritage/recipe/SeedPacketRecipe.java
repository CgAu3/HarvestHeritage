package me.theabab2333.harvestheritage.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.TransmuteRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Getter
public class SeedPacketRecipe extends CustomRecipe {
    public static final MapCodec<SeedPacketRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
        (i) -> i.group(
                Ingredient.CODEC.fieldOf("known_seed").forGetter(SeedPacketRecipe::getKnownSeed),
                Ingredient.CODEC.fieldOf("accept_paper").forGetter(SeedPacketRecipe::getAcceptPaper),
                ItemStackTemplate.CODEC.fieldOf("result").forGetter(SeedPacketRecipe::getResult)
            )
            .apply(i, SeedPacketRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SeedPacketRecipe> STREAM_CODEC =
        StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            SeedPacketRecipe::getKnownSeed,
            Ingredient.CONTENTS_STREAM_CODEC,
            SeedPacketRecipe::getAcceptPaper,
            ItemStackTemplate.STREAM_CODEC,
            SeedPacketRecipe::getResult,
            SeedPacketRecipe::new
        );
    public static final RecipeSerializer<@NotNull SeedPacketRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
    private final Ingredient knownSeed;
    private final Ingredient acceptPaper;
    private final ItemStackTemplate result;

    public SeedPacketRecipe(Ingredient knownSeed, Ingredient acceptPaper, ItemStackTemplate result) {
        this.knownSeed = knownSeed;
        this.acceptPaper = acceptPaper;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2) {
            return false;
        } else {
            boolean hasPaper = false;
            boolean hasSeed = false;

            for (int slot = 0; slot < input.size(); slot++) {
                ItemStack itemStack = input.getItem(slot);
                if (!itemStack.isEmpty()) {
                    if (this.acceptPaper.test(itemStack)) {
                        hasPaper = true;
                        continue;
                    }

                    if (this.knownSeed.test(itemStack)) {
                        hasSeed = true;
                        continue;
                    }

                    if (!this.knownSeed.test(itemStack) && !this.knownSeed.test(itemStack)) {
                        return false;
                    }
                }
            }

            return hasPaper && hasSeed;
        }
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack itemStack = input.getItem(slot);
            if (!itemStack.isEmpty()) {
                if (this.knownSeed.test(itemStack)) {
                    return TransmuteRecipe.createWithOriginalComponents(this.result, itemStack);
                }
            }
        }

        return this.result.create();
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }
}
