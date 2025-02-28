package fr.gallonemilien.block


import com.mojang.serialization.MapCodec
import fr.gallonemilien.block.entity.atm.ATMEntity
import fr.gallonemilien.block.entity.MoneyBlocksEntities
import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class ATM : FacingBlockWithEntity(Settings.create().nonOpaque().solid().strength(2.0f).hardness(2.0f)), BlockEntityProvider {


    override fun getCodec(): MapCodec<out BlockWithEntity> =
        createCodec { ATM() }

    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity? =
        pos?.let { p -> state?.let { s -> ATMEntity(p,s) } }


    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hit: BlockHitResult?
    ): ActionResult {
        if (!world!!.isClient) {
            val screenHandlerFactory = world.getBlockEntity(pos) as? ATMEntity
            if(screenHandlerFactory != null && screenHandlerFactory.isOpenable()) {
                screenHandlerFactory.let { player!!.openHandledScreen(it) }
            }
        }
        return ActionResult.SUCCESS
    }

    private val SHAPE : VoxelShape = Block.createCuboidShape(0.0,0.0,0.0,16.0,16.0,16.0)
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape = SHAPE

}
