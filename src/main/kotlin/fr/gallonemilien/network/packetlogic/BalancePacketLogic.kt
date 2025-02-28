package fr.gallonemilien.network.packetlogic

import fr.gallonemilien.ServerNetwork
import fr.gallonemilien.ServerNetwork.BalancePacketResponse
import fr.gallonemilien.ServerNetwork.BalancePacket
import fr.gallonemilien.block.entity.atm.ATMEntity
import fr.gallonemilien.persistence.PlayerCoinUtils
import io.wispforest.owo.network.ServerAccess

object BalancePacketLogic : PacketLogicServer<BalancePacket> {
    override fun action(message: BalancePacket, access: ServerAccess) {
        val entity = access.player().entityWorld.getBlockEntity(message.position())
        if(entity != null && entity is ATMEntity) {
            ServerNetwork.NET_CHANNEL
                .serverHandle(access.player)
                .send(BalancePacketResponse(
                    "BalancePacketResponse",
                    message.type,
                    PlayerCoinUtils.getBalance(access.player,message.type),
                    message.position
                )
            )
        }
    }
}