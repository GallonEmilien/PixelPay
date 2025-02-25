package fr.gallonemilien.block

import net.minecraft.block.*
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.Direction

abstract class FacingBlockWithEntity(settings: Settings) : BlockWithEntity(settings), BlockEntityProvider {

    companion object {
        val FACING: DirectionProperty = Properties.HORIZONTAL_FACING
    }

    private val validDirections = arrayOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)

    private fun getDirection(dir: Direction): Direction =
        if (dir in validDirections) dir else Direction.NORTH

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? =
        this.defaultState.with(FACING, getDirection(ctx.playerLookDirection.opposite))

    override fun rotate(state: BlockState?, rotation: BlockRotation?): BlockState? =
        state?.let { s -> rotation?.let { r -> s.with(FACING, r.rotate(s.get(FACING))) } }

    override fun mirror(state: BlockState?, mirror: BlockMirror?): BlockState? =
        state?.let { s -> mirror?.let { m -> s.rotate(m.getRotation(s.get(FACING))) } }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getRenderType(state: BlockState?): BlockRenderType = BlockRenderType.MODEL
}
