package fr.gallonemilien.block.entity.moneypress

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

class MoneyPressInventory(val inventory: DefaultedList<ItemStack>) {
    companion object {
        const val INPUT_SLOT = 0
        const val OUTPUT_SLOT = 1
    }

    fun getInputStack(): ItemStack = inventory[INPUT_SLOT]
    fun getOutputStack(): ItemStack = inventory[OUTPUT_SLOT]

    fun removeInputItem(amount: Int = 1) {
        inventory[INPUT_SLOT].decrement(amount)
    }

    fun addOutputItem(item: ItemStack) {
        val outputStack = getOutputStack()
        if (outputStack.isEmpty) {
            inventory[OUTPUT_SLOT] = item
        } else {
            outputStack.increment(item.count)
        }
    }

    fun canInsertItemIntoOutputSlot(item: Item): Boolean {
        return getOutputStack().item == item || getOutputStack().isEmpty
    }

    fun canInsertAmountIntoOutputSlot(result: ItemStack): Boolean {
        return getOutputStack().count + result.count <= getOutputStack().maxCount
    }

    fun isOutputSlotEmptyOrReceivable(): Boolean {
        return getOutputStack().isEmpty || getOutputStack().count < getOutputStack().maxCount
    }
}
