package me.theabab2333.harvestheritage.client.render.item;

import com.google.common.base.Suppliers;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import me.theabab2333.harvestheritage.api.item.ISeedItem;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static net.minecraft.client.renderer.item.CuboidItemModelWrapper.computeExtents;

public record SeedPacketItemModel(
    QuadCollection quads, List<ItemTintSource> tints, ModelRenderProperties properties, Matrix4fc matrix4fc, Supplier<Vector3fc[]> extents
) implements ItemModel {

    @Override
    public void update(
        ItemStackRenderState renderState,
        ItemStack stack,
        ItemModelResolver resolver,
        ItemDisplayContext context,
        @Nullable ClientLevel level,
        @Nullable ItemOwner owner,
        int i
    ) {
        if (!(stack.getItem() instanceof ISeedItem seedItem)) {
            return;
        }

        renderState.appendModelIdentityElement(this);

        ItemStackRenderState.LayerRenderState layer = renderState.newLayer();
        IntList intList = layer.tintLayers();
        intList.add(-1);
        layer.setExtents(extents);
        properties.applyToLayer(layer, context);
        layer.prepareQuadList().addAll(quads.getAll());
        renderState.appendModelIdentityElement(intList.getInt(0));

        Holder<Item> holder = seedItem.seed(stack);
        resolver.appendItemLayers(renderState, new ItemStack(holder), context, level, owner, i);
    }

    public record Unbaked(Identifier model, List<ItemTintSource> tints, Optional<Transformation> transformation)
        implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Identifier.CODEC.fieldOf("model").forGetter(Unbaked::model),
            ItemTintSources.CODEC.listOf().optionalFieldOf("tints", List.of()).forGetter(Unbaked::tints),
            Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").forGetter(Unbaked::transformation)
        ).apply(inst, Unbaked::new));

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc matrix4fc) {
            ModelBaker baker = context.blockModelBaker();
            ResolvedModel resolvedModel = baker.getModel(this.model);
            TextureSlots slots = resolvedModel.getTopTextureSlots();
            List<BakedQuad> baseModelQuads = resolvedModel.bakeTopGeometry(slots, baker, BlockModelRotation.IDENTITY).getAll();
            Supplier<Vector3fc[]> extents = Suppliers.memoize(() -> computeExtents(baseModelQuads));


            return new SeedPacketItemModel(
                resolvedModel.bakeTopGeometry(slots, baker, BlockModelRotation.IDENTITY),
                this.tints,
                ModelRenderProperties.fromResolvedModel(baker, resolvedModel, slots),
                Transformation.compose(matrix4fc, this.transformation),
                extents
            );
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(model);
        }
    }
}
