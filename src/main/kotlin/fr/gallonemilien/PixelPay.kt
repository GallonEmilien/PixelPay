package fr.gallonemilien

import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.block.entity.MoneyBlocksEntities
import fr.gallonemilien.block.screen.ModScreenHandlers
import fr.gallonemilien.item.PixelPayItems
import fr.gallonemilien.item.PixelPayItemGroup
import net.fabricmc.api.ModInitializer


class PixelPay : ModInitializer {
	companion object {
		const val MOD_ID = "pixelpay"
	}

	override fun onInitialize() {
		MoneyBlocks
		MoneyBlocksEntities
		PixelPayItems
		PixelPayItemGroup.initialize()
		ModScreenHandlers
	}
}