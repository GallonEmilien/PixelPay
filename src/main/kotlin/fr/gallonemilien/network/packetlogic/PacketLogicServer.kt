package fr.gallonemilien.network.packetlogic

import io.wispforest.owo.network.ServerAccess

interface PacketLogicServer<T> {
    fun action(message: T, access: ServerAccess)
}