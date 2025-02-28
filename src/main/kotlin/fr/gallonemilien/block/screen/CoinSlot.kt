package fr.gallonemilien.block.screen

import fr.gallonemilien.item.coin.Coin
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class CoinSlot(inventory: Inventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {
    override fun canInsert(stack: ItemStack): Boolean {
        return stack.item is Coin
    }
}