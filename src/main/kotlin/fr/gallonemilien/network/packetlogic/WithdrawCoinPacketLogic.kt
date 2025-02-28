package fr.gallonemilien.network.packetlogic

import fr.gallonemilien.ServerNetwork.WithdrawCoinPacket
import fr.gallonemilien.block.entity.atm.ATMEntity
import fr.gallonemilien.persistence.PlayerCoinUtils
import io.wispforest.owo.network.ServerAccess

object WithdrawCoinPacketLogic : PacketLogicServer<WithdrawCoinPacket> {

    override fun action(message: WithdrawCoinPacket, access: ServerAccess) {
        val player = access.player()
        val entity = access.player().entityWorld.getBlockEntity(message.position())
        if (entity is ATMEntity) {
            val playerInAtm = entity.playerOpeningATM
            if (playerInAtm != null && playerInAtm.uuid == player.uuid) {
                val coinType = message.type
                val playerBalance = PlayerCoinUtils.getBalance(playerInAtm,coinType)
                if(playerBalance > message.amount) {
                    PlayerCoinUtils.addBalance(player, coinType, -(message.amount))
                }
            }
        }
    }
}