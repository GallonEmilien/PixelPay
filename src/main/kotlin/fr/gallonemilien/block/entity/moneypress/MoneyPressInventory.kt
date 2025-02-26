package fr.gallonemilien.block.entity.moneypress

import fr.gallonemilien.block.entity.ImplementedInventory
import fr.gallonemilien.item.PixelPayItems
import fr.gallonemilien.recipe.MoneyPressRecipe
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.input.RecipeInput
import net.minecraft.util.collection.DefaultedList
import java.util.*

class MoneyPressInventory(val inventory: DefaultedList<ItemStack>, val moneyPressEntity: MoneyPressEntity) : ImplementedInventory, RecipeInput {

    private val recipes: Map<List<Item>, ItemStack> = mapOf(
        listOf(Items.COPPER_INGOT, Items.IRON_INGOT, Items.COPPER_INGOT) to ItemStack(PixelPayItems.COPPER_COIN,1),
        listOf(Items.GOLD_INGOT, PixelPayItems.COPPER_COIN, Items.GOLD_INGOT) to ItemStack(PixelPayItems.GOLD_COIN,1),
        listOf(Items.DIAMOND, PixelPayItems.GOLD_COIN, Items.DIAMOND) to ItemStack(PixelPayItems.DIAMOND_COIN,1),
        listOf(Items.EMERALD, PixelPayItems.DIAMOND_COIN, Items.EMERALD) to ItemStack(PixelPayItems.EMERALD_COIN,1),
        listOf(Items.NETHER_STAR, PixelPayItems.EMERALD_COIN, Items.NETHER_STAR) to ItemStack(PixelPayItems.BOUCKI_COIN,1)
    )


    private fun getCurrentRecipe() : Optional<RecipeEntry<MoneyPressRecipe>> {
        val world = moneyPressEntity.world
        return world!!.recipeManager.getFirstMatch(MoneyPressRecipe.Type.INSTANCE, this, world)
    }


    fun getRecipe() : ItemStack? {
        val recipe : Optional<RecipeEntry<MoneyPressRecipe>> = getCurrentRecipe()
        return if(recipe.isPresent) {
            recipe.get().value.output
        } else {
            null
        }
    }

    fun craftItem() : Boolean {
        val inventory = SimpleInventory(this.size())
        for(i in 0..<this.size())
            inventory.setStack(i, this.getStack(i))
        val recipeResult : ItemStack? = getRecipe()


        if(recipeResult != null) {
            if(canInsertItemIntoOutputSlot(inventory, recipeResult.item)
                && canInsertAmountIntoOutputSlot(inventory)) {
                this.removeStack(0,1)
                this.removeStack(1,1)
                this.removeStack(2,1)
                val count = this.getStack(3).count+1
                recipeResult.count = count
                this.setStack(3, recipeResult)
                return true
            }
        }
        return false
    }

    fun canInsertItemIntoOutputSlot(inventory: SimpleInventory, output: Item): Boolean {
        return inventory.getStack(3).item == output || inventory.getStack(3).isEmpty
    }

    fun canInsertAmountIntoOutputSlot(inventory: SimpleInventory): Boolean =
        inventory.getStack(3).maxCount > inventory.getStack(3).count

    override fun getItems(): DefaultedList<ItemStack> = this.inventory


    override fun getStackInSlot(slot: Int): ItemStack {
        return if (slot in 0 until inventory.size) inventory[slot] else ItemStack.EMPTY
    }

    override fun getSize(): Int {
        return inventory.size
    }

    override fun isEmpty(): Boolean {
        return inventory.all { it.isEmpty }
    }
}
