package fr.gallonemilien.block.screen

import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.network.BlockPosPayload
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

object ModScreenHandlers {
    val MONEYPRESS_SCREEN_HANDLER : ScreenHandlerType<MoneyPressScreenHandler> =
        register("moneypress",::MoneyPressScreenHandler,BlockPosPayload.PACKET_CODEC)

    fun <T : ScreenHandler, D : CustomPayload> register(
        name: String,
        factory: (Int, PlayerInventory, D) -> T,
        codec: PacketCodec<in RegistryByteBuf, D>
    ): ExtendedScreenHandlerType<T, D> {
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MOD_ID,name), ExtendedScreenHandlerType(factory, codec))
    }
}