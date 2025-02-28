package fr.gallonemilien.block.entity.atm


import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.block.entity.MoneyBlocksEntities
import fr.gallonemilien.block.entity.inventory.ATMInventory
import fr.gallonemilien.block.screen.ATMScreenHandler
import fr.gallonemilien.network.BlockPosPayload
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text

import net.minecraft.util.collection.DefaultedList

import net.minecraft.util.math.BlockPos

class ATMEntity(pos: BlockPos, state: BlockState) : BlockEntity(MoneyBlocksEntities.ATM_BLOCK_ENTITY, pos, state),
    ExtendedScreenHandlerFactory<BlockPosPayload> {

    companion object {
        private val TITLE: Text = Text.translatable("container.$MOD_ID.atm")
    }

    val atmInventory: ATMInventory = ATMInventory(DefaultedList.ofSize(1, ItemStack.EMPTY))
    var playerOpeningATM: ServerPlayerEntity? = null

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory?, player: PlayerEntity?): ScreenHandler {
        if (player is ServerPlayerEntity)
            playerOpeningATM = player
        return ATMScreenHandler(syncId, playerInventory!!, this)
    }

    fun getPlayerOpeningATM(): ServerPlayerEntity? = playerOpeningATM

    fun removePlayer() {
        playerOpeningATM = null
    }

    fun isOpenable() : Boolean =
        playerOpeningATM == null



    override fun getDisplayName(): Text = TITLE

    override fun getScreenOpeningData(player: ServerPlayerEntity?): BlockPosPayload = BlockPosPayload(pos)

}