package fr.gallonemilien.block.entity.moneypress

import fr.gallonemilien.block.entity.ImplementedInventory
import fr.gallonemilien.recipe.MoneyPressRecipeInput
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

class MoneyPressInventory(val inventory: DefaultedList<ItemStack>, val moneyPressEntity: MoneyPressEntity) : ImplementedInventory {

    val moneyPressRecipeInput : MoneyPressRecipeInput = MoneyPressRecipeInput(this)

    override fun getItems(): DefaultedList<ItemStack> = this.inventory

    override fun isEmpty(): Boolean {
        return getItems().all { it.isEmpty }
    }
}
