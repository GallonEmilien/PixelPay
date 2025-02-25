package fr.gallonemilien.block.screen

import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import fr.gallonemilien.network.BlockPosPayload
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

class MoneyPressScreenHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    blockEntity: BlockEntity,
    private val propertyDelegate: PropertyDelegate
) : ScreenHandler(ModScreenHandlers.MONEYPRESS_SCREEN_HANDLER, syncId) {

    private val inventory: Inventory = blockEntity as Inventory
    val blockEntity: MoneyPressEntity = blockEntity as MoneyPressEntity

    // Client constructor
    constructor(syncId: Int, playerInventory: PlayerInventory, payload: BlockPosPayload) : this(
        syncId,
        playerInventory,
        playerInventory.player.world.getBlockEntity(payload.pos)!!,
        ArrayPropertyDelegate(2)
    )

    init {
        checkSize(inventory, 2)
        inventory.onOpen(playerInventory.player)

        addSlot(Slot(inventory, 0, 80, 11))
        addSlot(Slot(inventory, 1, 80, 59))

        addPlayerInventory(playerInventory)
        addPlayerHotbar(playerInventory)

        addProperties(propertyDelegate)
    }

    fun isCrafting(): Boolean = propertyDelegate.get(0) > 0

    fun getScaledProgress(): Int {
        val progress = propertyDelegate.get(0)
        val maxProgress = propertyDelegate.get(1) // Max Progress
        val progressArrowSize = 26 // Width in pixels of your arrow

        return if (maxProgress != 0 && progress != 0) (progress * progressArrowSize) / maxProgress else 0
    }

    override fun quickMove(player: PlayerEntity, invSlot: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[invSlot]

        if (slot != null && slot.hasStack()) {
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
