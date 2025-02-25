package fr.gallonemilien.block.custom

import com.mojang.serialization.MapCodec
import fr.gallonemilien.block.FacingBlockWithEntity
import fr.gallonemilien.block.entity.MoneyBlocksEntities
import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MoneyPress : FacingBlockWithEntity(Settings.create().nonOpaque()), BlockEntityProvider {

    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity =
        MoneyPressEntity(pos!!,state!!)

    override fun getCodec(): MapCodec<out BlockWithEntity> =
        createCodec { MoneyPress() }

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (state.block != newState.block) {
            val blockEntity = world.getBlockEntity(pos)
            if (blockEntity is MoneyPressEntity) {
                ItemScatterer.spawn(world, pos, blockEntity)
                world.updateComparators(pos, this)
            }
            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hit: BlockHitResult?
    ): ActionResult {
        if (!world!!.isClient) {
            val screenHandlerFactory = world!!.getBlockEntity(pos) as? MoneyPressEntity
            screenHandlerFactory?.let { player!!.openHandledScreen(it) }
        }
        return ActionResult.SUCCESS
    }


    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return validateTicker(
            type, MoneyBlocksEntities.MONEY_PRESS_ENTITY
        ) { world1, pos, state1, blockEntity -> blockEntity.tick(world1, pos, state1) }
    }

}