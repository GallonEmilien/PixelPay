package fr.gallonemilien

import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.block.MoneyPressRenderer
import fr.gallonemilien.block.entity.MoneyBlocksEntities
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories

class PixelPayClient : ClientModInitializer {
	override fun onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),MoneyBlocks.ATM_BLOCK)
		BlockEntityRendererFactories.register(MoneyBlocksEntities.MONEY_PRESS_ENTITY,::MoneyPressRenderer)
	}
}
