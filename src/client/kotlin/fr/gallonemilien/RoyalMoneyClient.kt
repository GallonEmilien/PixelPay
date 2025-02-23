package fr.gallonemilien

import fr.gallonemilien.block.MoneyBlocks
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

class RoyalMoneyClient : ClientModInitializer {
	override fun onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),MoneyBlocks.ATM_BLOCK)
	}
}
