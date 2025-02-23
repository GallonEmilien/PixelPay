package fr.gallonemilien.block


import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity

import net.minecraft.sound.SoundEvents

import net.minecraft.util.math.BlockPos

import net.minecraft.world.World

class ATMEntity(pos: BlockPos, state: BlockState) : BlockEntity(MoneyBlocksEntityTypes.ATM_BLOCK_ENTITY, pos, state) {
    fun tick(world: World?, blockPos: BlockPos?, blockState: BlockState?, atmEntityType: ATMEntity?) {
        world?.players?.forEach {
            it.playSound(SoundEvents.BLOCK_LAVA_POP)
        }
    }
}