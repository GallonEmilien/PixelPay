package fr.gallonemilien.compat

import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.recipe.MoneyPressRecipe
import fr.gallonemilien.screen.MoneyPressScreen
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry
import me.shedaniel.rei.api.common.util.EntryStacks

class PixelPayREIClientPlugin : REIClientPlugin {

    override fun registerCategories(registry: CategoryRegistry?) {
        registry!!.add(MoneyPressCategory())
        registry.addWorkstations(MoneyPressCategory.MONEY_PRESS, EntryStacks.of(MoneyBlocks.MONEY_PRESS))
    }

    override fun registerDisplays(registry: DisplayRegistry?) {
       registry!!.registerRecipeFiller(MoneyPressRecipe::class.java, MoneyPressRecipe.Type.INSTANCE, ::MoneyPressDisplay)
    }

    override fun registerScreens(registry: ScreenRegistry?) {
        registry!!.registerClickArea({ screen -> Rectangle(75, 30, 20, 30) }, MoneyPressScreen::class.java,
            MoneyPressCategory.MONEY_PRESS
        )
    }
}