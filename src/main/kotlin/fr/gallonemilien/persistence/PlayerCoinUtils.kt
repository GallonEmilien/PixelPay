package fr.gallonemilien.persistence

import fr.gallonemilien.item.coin.CoinType
import net.minecraft.server.network.ServerPlayerEntity

object PlayerCoinUtils {

    fun getBalance(player: ServerPlayerEntity, coinType: CoinType): Int =
        ServerCoinSaverLoader.getPlayerState(player).dataMap[coinType] ?: 0

    fun setBalance(player: ServerPlayerEntity, coinType: CoinType, balance: Int) {
        ServerCoinSaverLoader.getPlayerState(player).dataMap[coinType] = balance
    }

    fun addBalance(player: ServerPlayerEntity, coinType: CoinType, balance: Int) {
        val previousBalance = getBalance(player, coinType)
        ServerCoinSaverLoader.getPlayerState(player).dataMap[coinType] = balance + previousBalance
    }
}