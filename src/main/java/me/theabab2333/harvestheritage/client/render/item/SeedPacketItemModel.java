package me.theabab2333.harvestheritage.client.render.item;

import com.google.common.base.Suppliers;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.theabab2333.harvestheritage.api.item.ISeedItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
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

        ItemStackRenderState.LayerRenderState baseLayer = renderState.newLayer();
        baseLayer.setExtents(extents);
        baseLayer.setLocalTransform(matrix4fc);
        properties.applyToLayer(baseLayer, context);
        baseLayer.prepareQuadList().addAll(quads.getAll());

        Holder<Item> holder = seedItem.seed(stack);
        if (holder.value() == Items.AIR) return;

        if (context == ItemDisplayContext.GUI) {
            resolver.appendItemLayers(renderState, new ItemStack(holder), context, level, owner, i);
        } else {
            addSeedLayer(renderState, holder.value(), context);
        }
    }

    private void addSeedLayer(ItemStackRenderState renderState, Item seedItem, ItemDisplayContext context) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(seedItem);
        Identifier textureId = itemId.withPath("item/" + itemId.getPath());

        AtlasManager atlasManager = Minecraft.getInstance().getAtlasManager();
        TextureAtlasSprite sprite = atlasManager.get(new SpriteId(TextureAtlas.LOCATION_BLOCKS, textureId));

        if (sprite.contents().name().equals(MissingTextureAtlasSprite.getLocation())) {
            sprite = atlasManager.get(new SpriteId(TextureAtlas.LOCATION_ITEMS, textureId));
            if (sprite.contents().name().equals(MissingTextureAtlasSprite.getLocation())) {
                return;
            }
        }

        var baseQuads = quads.getAll();
        if (baseQuads.isEmpty()) return;
        BakedQuad baseQuad = baseQuads.get(0);
        BakedQuad.MaterialInfo material = baseQuad.materialInfo();

        ItemStackRenderState.LayerRenderState seedLayer = renderState.newLayer();
        seedLayer.setExtents(extents);
        seedLayer.setLocalTransform(new Matrix4f(matrix4fc).translate(0.0f, 0.0f, 0.05f));
        properties.applyToLayer(seedLayer, context);

        float margin = (1.0f - 12.0f / 16.0f) / 2.0f;
        seedLayer.prepareQuadList().add(new BakedQuad(
            new Vector3f(margin, margin, 0.5f),
            new Vector3f(1.0f - margin, margin, 0.5f),
            new Vector3f(1.0f - margin, 1.0f - margin, 0.5f),
            new Vector3f(margin, 1.0f - margin, 0.5f),
            UVPair.pack(sprite.getU(0.0f), sprite.getV(1.0f)),
            UVPair.pack(sprite.getU(1.0f), sprite.getV(1.0f)),
            UVPair.pack(sprite.getU(1.0f), sprite.getV(0.0f)),
            UVPair.pack(sprite.getU(0.0f), sprite.getV(0.0f)),
            baseQuad.direction(),
            material
        ));
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
