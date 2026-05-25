package me.theabab2333.harvestheritage.client.render.blockentity.state;

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class ActivatorBlockRenderState extends BlockEntityRenderState {
    public float animTime = 0;
    public BlockModelRenderState block = new BlockModelRenderState();
    public ItemStackRenderState itemModel = new ItemStackRenderState();
}
