package fr.gallonemilien.block

import com.mojang.serialization.MapCodec
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class ATM : BlockWithEntity(Settings.create().solid()), BlockEntityProvider {


    companion object {
        val FACING : DirectionProperty = Properties.HORIZONTAL_FACING
    }

    private val validDirections = arrayOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
    private fun getDirection(dir: Direction): Direction =
        if (validDirections.contains(dir)) { dir } else { Direction.NORTH }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? =
        this.defaultState.with(FACING, getDirection(ctx.playerLookDirection.opposite))


    override fun rotate(state: BlockState?, rotation: BlockRotation?): BlockState? =
        state?.let { s -> rotation?.let { r -> s.with(FACING, r.rotate(s.get(FACING))) } }


    override fun mirror(state: BlockState?, mirror: BlockMirror?): BlockState? =
        state?.let { s -> mirror?.let { m -> s.rotate(m.getRotation(s.get(FACING))) } }


    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getCodec(): MapCodec<out BlockWithEntity> =
        createCodec { ATM() }

    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? =
        pos?.let { p -> state?.let { s -> ATMEntityType(p,s) } }

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return validateTicker(type, MoneyBlocksEntityTypes.ATM_BLOCK_ENTITY) { world, pos, state, entity ->
            entity.tick(world, pos, state, entity as ATMEntityType)
        }
    }

    private val SHAPE : VoxelShape = Block.createCuboidShape(0.0,0.0,0.0,16.0,16.0,16.0)
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape = SHAPE

    override fun getRenderType(state: BlockState?): BlockRenderType =
        BlockRenderType.MODEL

}

class ATMEntityType(pos: BlockPos, state: BlockState) : BlockEntity(MoneyBlocksEntityTypes.ATM_BLOCK_ENTITY, pos, state) {
    fun tick(world: World?, blockPos: BlockPos?, blockState: BlockState?, atmEntityType: ATMEntityType?) {
        world?.players?.forEach {
            it.playSound(SoundEvents.BLOCK_LAVA_POP)
        }
    }
}