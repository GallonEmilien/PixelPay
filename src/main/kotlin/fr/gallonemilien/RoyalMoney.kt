package fr.gallonemilien

import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.block.MoneyBlocksEntityTypes
import fr.gallonemilien.item.MoneyItems
import fr.gallonemilien.item.RoyalMoneyItemGroup
import net.fabricmc.api.ModInitializer


class RoyalMoney : ModInitializer {
	companion object {
		const val MOD_ID = "royalmoney"
	}

	override fun onInitialize() {
		MoneyItems
		MoneyBlocks
		MoneyBlocksEntityTypes
		RoyalMoneyItemGroup.initialize()
	}
}