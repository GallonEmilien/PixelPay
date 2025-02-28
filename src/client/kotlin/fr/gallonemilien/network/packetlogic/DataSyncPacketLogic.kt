package fr.gallonemilien.network.packetlogic

import fr.gallonemilien.PixelPayClient
import fr.gallonemilien.ServerNetwork.DataSyncPacket
import io.wispforest.owo.network.ClientAccess

object DataSyncPacketLogic : PacketLogicClient<DataSyncPacket>{
    override fun action(message: DataSyncPacket, access: ClientAccess) {
        PixelPayClient.playerData = message.data
    }
}