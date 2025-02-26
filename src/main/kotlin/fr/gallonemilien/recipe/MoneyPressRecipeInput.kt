package fr.gallonemilien.recipe

import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import fr.gallonemilien.block.entity.moneypress.MoneyPressInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.input.RecipeInput
import net.minecraft.util.collection.DefaultedList
import java.util.*

class MoneyPressRecipeInput(val moneyPressInventory : MoneyPressInventory) :RecipeInput {

    private val inventory : DefaultedList<ItemStack> = moneyPressInventory.inventory
    private val moneyPressEntity : MoneyPressEntity = moneyPressInventory.moneyPressEntity

    override fun getStackInSlot(slot: Int): ItemStack {
        return if (slot in 0 until inventory.size) inventory[slot] else ItemStack.EMPTY
    }

    override fun getSize(): Int {
        return inventory.size
    }

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
        val inventory = SimpleInventory(moneyPressInventory.size())
        for(i in 0..<moneyPressInventory.size())
            inventory.setStack(i, moneyPressInventory.getStack(i))
        val recipeResult : ItemStack? = getRecipe()


        if(recipeResult != null) {
            if(canInsertItemIntoOutputSlot(inventory, recipeResult.item)
                && canInsertAmountIntoOutputSlot(inventory)) {
                moneyPressInventory.removeStack(0,1)
                moneyPressInventory.removeStack(1,1)
                moneyPressInventory.removeStack(2,1)
                val count = moneyPressInventory.getStack(3).count+1
                recipeResult.count = count
                moneyPressInventory.setStack(3, recipeResult)
                return true
            }
        }
        return false
    }

    private fun canInsertItemIntoOutputSlot(inventory: SimpleInventory, output: Item): Boolean {
        return inventory.getStack(3).item == output || inventory.getStack(3).isEmpty
    }

    private fun canInsertAmountIntoOutputSlot(inventory: SimpleInventory): Boolean =
        inventory.getStack(3).maxCount > inventory.getStack(3).count

    override fun isEmpty(): Boolean =
        moneyPressInventory.isEmpty
}