package fr.gallonemilien

import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.block.entity.MoneyBlocksEntities
import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import fr.gallonemilien.block.screen.ModScreenHandlers
import fr.gallonemilien.item.PixelPayItems
import fr.gallonemilien.item.PixelPayItemGroup
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.util.Identifier


class PixelPay : ModInitializer {
	companion object {
		const val MOD_ID = "pixelpay"

		fun id(path : String): Identifier = Identifier.of(MOD_ID,path)
	}

	override fun onInitialize() {
		MoneyBlocks
		MoneyBlocksEntities
		PixelPayItems
		PixelPayItemGroup.initialize()
		ModScreenHandlers
	}
}