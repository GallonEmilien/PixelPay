package fr.gallonemilien.recipe

import fr.gallonemilien.PixelPay
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModRecipes {
    fun registerRecipes() {
        Registry.register(  Registries.RECIPE_SERIALIZER, PixelPay.id(MoneyPressRecipe.Serializer.ID),
                            MoneyPressRecipe.Serializer.INSTANCE)
        Registry.register(Registries.RECIPE_TYPE, PixelPay.id(MoneyPressRecipe.Type.ID), MoneyPressRecipe.Type.INSTANCE)
    }
}