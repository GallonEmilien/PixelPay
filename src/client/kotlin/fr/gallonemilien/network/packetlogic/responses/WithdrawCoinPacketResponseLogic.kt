package fr.gallonemilien.network.packetlogic.responses

import fr.gallonemilien.ServerNetwork.WithdrawCoinPacketResponse
import fr.gallonemilien.network.packetlogic.PacketLogicClient
import io.wispforest.owo.network.ClientAccess

object WithdrawCoinPacketResponseLogic : PacketLogicClient<WithdrawCoinPacketResponse> {
    override fun action(message: WithdrawCoinPacketResponse, access: ClientAccess) {
    }
}