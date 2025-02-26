package fr.gallonemilien.compat

import fr.gallonemilien.recipe.MoneyPressRecipe
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryIngredients
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.recipe.RecipeEntry

class MoneyPressDisplay: BasicDisplay {

    constructor(inputs: List<EntryIngredient>, outputs: List<EntryIngredient>) : super(inputs, outputs)

    constructor(recipe: RecipeEntry<MoneyPressRecipe>) : super(
        getInputList(recipe.value),
        listOf(EntryIngredient.of(EntryStacks.of(recipe.value.getResult(null))))
    )


    companion object {
        private fun getInputList(recipe: MoneyPressRecipe?) : List<EntryIngredient> {
            if(recipe==null) return listOf()
            val list = mutableListOf<EntryIngredient>()
            list.add(EntryIngredients.ofIngredient(recipe.ingredients[0]))
            list.add(EntryIngredients.ofIngredient(recipe.ingredients[1]))
            list.add(EntryIngredients.ofIngredient(recipe.ingredients[2]))
            return list
        }
    }

    override fun getCategoryIdentifier(): CategoryIdentifier<*> =
        MoneyPressCategory.MONEY_PRESS
}