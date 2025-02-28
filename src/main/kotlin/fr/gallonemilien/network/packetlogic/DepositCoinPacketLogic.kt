package fr.gallonemilien.network.packetlogic

import fr.gallonemilien.ServerNetwork.DepositCoinPacket
import fr.gallonemilien.block.entity.atm.ATMEntity
import fr.gallonemilien.item.coin.CoinType
import fr.gallonemilien.persistence.PlayerCoinUtils
import io.wispforest.owo.network.ServerAccess

object DepositCoinPacketLogic : PacketLogicServer<DepositCoinPacket> {

    override fun action(message: DepositCoinPacket, access: ServerAccess) {
        val player = access.player()
        val entity = access.player().entityWorld.getBlockEntity(message.position())
        if (entity is ATMEntity) {
            val playerInAtm = entity.playerOpeningATM
            if (playerInAtm != null && playerInAtm.uuid == player.uuid) {
                val coinCopy = entity.atmInventory.getItems().first().copy()
                val amount = coinCopy.count
                entity.atmInventory.getItems().clear()
                val coinType = CoinType.getFromStack(coinCopy)
                if(coinType != null)
                    PlayerCoinUtils.addBalance(player, CoinType.getFromStack(coinCopy)!!,amount)
            }
        }
    }
}