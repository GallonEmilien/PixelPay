package fr.gallonemilien.network.packetlogic.actions

import fr.gallonemilien.ServerNetwork.MachineSyncPacket
import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import fr.gallonemilien.network.packetlogic.PacketLogicClient
import io.wispforest.owo.network.ClientAccess
import net.minecraft.block.entity.BlockEntity

object MachinSyncPacketLogic : PacketLogicClient<MachineSyncPacket> {
    override fun action(message: MachineSyncPacket, access: ClientAccess) {
        val entity: BlockEntity? = access.player().clientWorld.getBlockEntity(message.position)
        if (entity != null && entity is MoneyPressEntity) {
            entity.handleNetworkEntry(message)
        }
    }
}