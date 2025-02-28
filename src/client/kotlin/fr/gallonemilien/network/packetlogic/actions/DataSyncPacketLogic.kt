package fr.gallonemilien.network.packetlogic.actions

import fr.gallonemilien.PixelPayClient
import fr.gallonemilien.ServerNetwork.DataSyncPacket
import fr.gallonemilien.network.packetlogic.PacketLogicClient
import io.wispforest.owo.network.ClientAccess

object DataSyncPacketLogic : PacketLogicClient<DataSyncPacket> {
    override fun action(message: DataSyncPacket, access: ClientAccess) {
        PixelPayClient.playerData = message.data
    }
}