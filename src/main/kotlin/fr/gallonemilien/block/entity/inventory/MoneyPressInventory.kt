package fr.gallonemilien.block.entity.inventory

import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import fr.gallonemilien.recipe.MoneyPressRecipeInput
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

class MoneyPressInventory(inventory: DefaultedList<ItemStack>, val moneyPressEntity: MoneyPressEntity) : PixelPayInventory(inventory) {
    val moneyPressRecipeInput : MoneyPressRecipeInput = MoneyPressRecipeInput(this)
}
