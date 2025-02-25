package fr.gallonemilien.network

import fr.gallonemilien.PixelPay
import fr.gallonemilien.PixelPay.Companion.MOD_ID
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.network.packet.CustomPayload.Id
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

data class BlockPosPayload(val pos : BlockPos) : CustomPayload {

    companion object {
        val ID : Id<BlockPosPayload> = Id<BlockPosPayload>(PixelPay.id("block_pos"))
        val PACKET_CODEC : PacketCodec<RegistryByteBuf, BlockPosPayload> =
            PacketCodec.tuple(BlockPos.PACKET_CODEC, BlockPosPayload::pos, ::BlockPosPayload)
    }

    override fun getId(): Id<out CustomPayload> = ID

}