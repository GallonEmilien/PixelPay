package fr.gallonemilien.block.entity.moneypress

import fr.gallonemilien.item.PixelPayItems
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object MoneyPressRecipe {
    private val RESULT_ITEM = ItemStack(PixelPayItems.COPPER_COIN)

    fun matches(inventory: MoneyPressInventory): Boolean {
        return inventory.getInputStack().item == Items.COPPER_INGOT &&
                inventory.canInsertAmountIntoOutputSlot(RESULT_ITEM) &&
                inventory.canInsertItemIntoOutputSlot(RESULT_ITEM.item)
    }

    fun getResult(): ItemStack = RESULT_ITEM
}
