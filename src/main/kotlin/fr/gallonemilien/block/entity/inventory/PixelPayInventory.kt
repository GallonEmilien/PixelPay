package fr.gallonemilien.block.entity.inventory

import fr.gallonemilien.block.entity.ImplementedInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

abstract class PixelPayInventory(val inventory: DefaultedList<ItemStack>) : ImplementedInventory {
    override fun getItems(): DefaultedList<ItemStack> = this.inventory

    override fun isEmpty(): Boolean {
        return getItems().all { it.isEmpty }
    }
}