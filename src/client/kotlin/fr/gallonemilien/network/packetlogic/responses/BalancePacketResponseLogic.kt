package fr.gallonemilien.network.packetlogic.responses

import fr.gallonemilien.ServerNetwork.BalancePacketResponse
import fr.gallonemilien.block.entity.atm.ATMEntity
import fr.gallonemilien.network.packetlogic.PacketLogicClient
import io.wispforest.owo.network.ClientAccess
import net.minecraft.block.entity.BlockEntity

object BalancePacketResponseLogic : PacketLogicClient<BalancePacketResponse> {
    override fun action(message: BalancePacketResponse, access: ClientAccess) {
        val entity: BlockEntity? = access.player().clientWorld.getBlockEntity(message.pos)
        if (entity != null && entity is ATMEntity) {
            entity.handleNetworkEntry(message)
        }
    }
}