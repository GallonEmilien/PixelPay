package fr.gallonemilien.network

import fr.gallonemilien.PixelPay
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.network.packet.CustomPayload.Id

data class PersistencePayload(val buf : PacketByteBuf) : CustomPayload {

    companion object {
        val ID : Id<PersistencePayload> = Id<PersistencePayload>(PixelPay.id("initial_sync"))

        val PACKET_CODEC: PacketCodec<RegistryByteBuf, PersistencePayload> = object : PacketCodec<RegistryByteBuf, PersistencePayload> {
            override fun encode(buf: RegistryByteBuf, packet: PersistencePayload) {
                buf.writeBytes(packet.buf.array())
            }

            override fun decode(buf: RegistryByteBuf): PersistencePayload {
                val packetBuf = PacketByteBuf(buf.copy())
                return PersistencePayload(packetBuf)
            }
        }
    }

    override fun getId(): Id<out CustomPayload> = ID

}