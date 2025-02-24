package fr.gallonemilien.block.custom

import com.mojang.serialization.MapCodec
import fr.gallonemilien.block.entity.MoneyPressEntity
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class MoneyPress : BlockWithEntity(Settings.create().nonOpaque()) {

    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity =
        MoneyPressEntity(pos!!,state!!)

    override fun getCodec(): MapCodec<out BlockWithEntity> =
        createCodec { MoneyPress() }

    override fun getRenderType(state: BlockState?): BlockRenderType =
        BlockRenderType.MODEL
}