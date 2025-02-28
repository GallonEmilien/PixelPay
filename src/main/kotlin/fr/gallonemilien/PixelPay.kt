package fr.gallonemilien

import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.block.entity.MoneyBlocksEntities
import fr.gallonemilien.block.screen.ModScreenHandlers
import fr.gallonemilien.item.PixelPayItems
import fr.gallonemilien.item.PixelPayItemGroup
import fr.gallonemilien.item.coin.CoinType
import fr.gallonemilien.persistence.PlayerCoinData
import fr.gallonemilien.persistence.PlayerCoinUtils
import fr.gallonemilien.persistence.ServerCoinSaverLoader
import fr.gallonemilien.recipe.ModRecipes
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
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
		ModRecipes.registerRecipes()
		ServerNetwork.register()
		registerServerState()
	}

	private fun registerServerState() {
		ServerPlayConnectionEvents.JOIN.register { handler, _, _ ->
			val playerCoinData : PlayerCoinData = ServerCoinSaverLoader.getPlayerState(handler.player)
			//ServerNetwork.NET_CHANNEL.serverHandle(handler.player).send(ServerNetwork.DataSyncPacket(playerCoinData))
		}
	}
}