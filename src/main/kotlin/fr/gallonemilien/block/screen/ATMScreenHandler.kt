package fr.gallonemilien.block.screen

import fr.gallonemilien.block.entity.atm.ATMEntity
import fr.gallonemilien.network.BlockPosPayload
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity

class ATMScreenHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    blockEntity: BlockEntity,
) : ScreenHandler(ModScreenHandlers.ATM_SCREEN_HANDLER, syncId) {

    private val inventory: Inventory = (blockEntity as ATMEntity).atmInventory
    val blockEntity: ATMEntity = blockEntity as ATMEntity

    // Client constructor
    constructor(syncId: Int, playerInventory: PlayerInventory, payload: BlockPosPayload) : this(
        syncId,
        playerInventory,
        playerInventory.player.world.getBlockEntity(payload.pos)!!,
    )

    init {
        checkSize(inventory, 1)
        inventory.onOpen(playerInventory.player)

        addSlot(CoinSlot(inventory, 0, 30, 13))

        addPlayerInventory(playerInventory)
        addPlayerHotbar(playerInventory)
    }

    override fun onClosed(player: PlayerEntity?) {
        super.onClosed(player)
        if(player is ServerPlayerEntity) {
            blockEntity.removePlayer()
        }
    }

    override fun quickMove(player: PlayerEntity, invSlot: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[invSlot]

        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()

            if (invSlot < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }

            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }

        return newStack
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return inventory.canPlayerUse(player)
    }

    private fun addPlayerInventory(playerInventory: PlayerInventory) {
        for (i in 0..2) {
            for (l in 0..8) {
                addSlot(Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18))
            }
        }
    }

    private fun addPlayerHotbar(playerInventory: PlayerInventory) {
        for (i in 0..8) {
            addSlot(Slot(playerInventory, i, 8 + i * 18, 142))
        }
    }
}
