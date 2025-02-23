package fr.gallonemilien.item

import fr.gallonemilien.RoyalMoney.Companion.MOD_ID
import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.item.MoneyItems.royalMoneyItems
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object RoyalMoneyItemGroup {
    private const val ROYALMONEY_TAB_NAME = "itemGroup.$MOD_ID.general"

    val ROYALMONEY_GROUP = FabricItemGroup.builder()
        .icon { royalMoneyItems.random().defaultStack }
        .displayName(Text.translatable(ROYALMONEY_TAB_NAME))
        .entries { displayContext, entries ->
            entries.addAll(royalMoneyItems.map { it.defaultStack })
            entries.add(MoneyBlocks.ATM_BLOCK.asItem().defaultStack.copy().apply { count = 1 })
        }
        .build()

    fun initialize() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MOD_ID, "general"), ROYALMONEY_GROUP)
    }
}



