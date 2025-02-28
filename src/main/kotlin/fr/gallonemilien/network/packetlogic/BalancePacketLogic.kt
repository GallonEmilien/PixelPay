package fr.gallonemilien.network.packetlogic

import fr.gallonemilien.ServerNetwork.BalancePacket
import fr.gallonemilien.persistence.PlayerCoinUtils
import io.wispforest.owo.network.ServerAccess

object BalancePacketLogic : PacketLogicServer<BalancePacket> {
    override fun action(message: BalancePacket, access: ServerAccess) {
        val player = access.player()
        println(PlayerCoinUtils.getBalance(player, message.type))
    }
}