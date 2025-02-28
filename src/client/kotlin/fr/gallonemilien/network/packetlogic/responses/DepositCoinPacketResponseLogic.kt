package fr.gallonemilien.network.packetlogic.responses

import fr.gallonemilien.ServerNetwork.DepositCoinPacketResponse
import fr.gallonemilien.network.packetlogic.PacketLogicClient
import io.wispforest.owo.network.ClientAccess

object DepositCoinPacketResponseLogic : PacketLogicClient<DepositCoinPacketResponse> {
    override fun action(message: DepositCoinPacketResponse, access: ClientAccess) {
    }
}