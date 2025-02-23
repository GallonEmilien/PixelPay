package fr.gallonemilien

import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.block.MoneyBlocksEntityTypes
import fr.gallonemilien.item.PixelPayItems
import fr.gallonemilien.item.PixelPayItemGroup
import net.fabricmc.api.ModInitializer


class PixelPay : ModInitializer {
	companion object {
		const val MOD_ID = "pixelpay"
	}

	override fun onInitialize() {
		PixelPayItems
		MoneyBlocks
		MoneyBlocksEntityTypes
		PixelPayItemGroup.initialize()
	}
}