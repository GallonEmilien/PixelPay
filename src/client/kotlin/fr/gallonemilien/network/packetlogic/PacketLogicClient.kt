package fr.gallonemilien.network.packetlogic

import io.wispforest.owo.network.ClientAccess

interface PacketLogicClient<T> {
    fun action(message: T, access: ClientAccess)
}