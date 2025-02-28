package fr.gallonemilien.block.screen

import fr.gallonemilien.PixelPay
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

object ModScreenHandlers {
    val MONEYPRESS_SCREEN_HANDLER : ScreenHandlerType<MoneyPressScreenHandler> =
        register("moneypress",::MoneyPressScreenHandler,BlockPosPayload.PACKET_CODEC)
    val ATM_SCREEN_HANDLER : ScreenHandlerType<ATMScreenHandler> =
        register("atm",::ATMScreenHandler,BlockPosPayload.PACKET_CODEC)

    fun <T : ScreenHandler, D : CustomPayload> register(
        name: String,
        factory: (Int, PlayerInventory, D) -> T,
        codec: PacketCodec<in RegistryByteBuf, D>
    ): ExtendedScreenHandlerType<T, D> {
        return Registry.register(Registries.SCREEN_HANDLER, PixelPay.id(name), ExtendedScreenHandlerType(factory, codec))
    }
}