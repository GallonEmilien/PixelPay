package fr.gallonemilien;

import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity;
import io.wispforest.owo.network.OwoNetChannel;
import net.minecraft.util.math.BlockPos;

public class NetworkContent {

    public static final OwoNetChannel NET_CHANNEL = OwoNetChannel.create(PixelPay.Companion.id("pixelpay_net"));

    // Server -> Client
    public record MachineSyncPacket(BlockPos position, boolean isWorking, Integer progress) {
    }
}
