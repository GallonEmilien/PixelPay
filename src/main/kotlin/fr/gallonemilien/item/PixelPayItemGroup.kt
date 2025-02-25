package fr.gallonemilien.item

import fr.gallonemilien.PixelPay
import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.item.PixelPayItems.pixelPayItems
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object PixelPayItemGroup {
    private const val PIXELPAY_TAB_NAME = "itemGroup.$MOD_ID.general"

    val PIXELPAY_GROUP = FabricItemGroup.builder()
        .icon { pixelPayItems.random().defaultStack }
        .displayName(Text.translatable(PIXELPAY_TAB_NAME))
        .entries { displayContext, entries ->
            entries.addAll(pixelPayItems.map { it.defaultStack })
            entries.add(MoneyBlocks.ATM_BLOCK.asItem().defaultStack.copy().apply { count = 1 })
            entries.add(MoneyBlocks.MONEY_PRESS.asItem().defaultStack.copy().apply { count = 1 })
        }
        .build()

    fun initialize() {
        Registry.register(Registries.ITEM_GROUP, PixelPay.id( "general"), PIXELPAY_GROUP)
    }
}



