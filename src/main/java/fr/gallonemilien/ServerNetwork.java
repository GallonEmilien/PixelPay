package fr.gallonemilien;


import fr.gallonemilien.item.coin.CoinType;
import fr.gallonemilien.network.packetlogic.BalancePacketLogic;
import fr.gallonemilien.network.packetlogic.DepositCoinPacketLogic;
import fr.gallonemilien.network.packetlogic.WithdrawCoinPacketLogic;
import fr.gallonemilien.persistence.PlayerCoinData;
import io.wispforest.owo.network.OwoNetChannel;
import net.minecraft.util.math.BlockPos;

public class ServerNetwork {

    public static final OwoNetChannel NET_CHANNEL = OwoNetChannel.create(PixelPay.Companion.id("pixelpay_net"));

    public record MachineSyncPacket(BlockPos position, boolean isWorking, Integer progress) {}
    public record DepositCoinPacket(BlockPos position) {}
    public record WithdrawCoinPacket(BlockPos position, CoinType type, int amount) {}
    public record BalancePacket(CoinType type) {}
    public record DataSyncPacket(PlayerCoinData data) {}

    public static void register() {
        NET_CHANNEL.registerServerbound(ServerNetwork.DepositCoinPacket.class, (DepositCoinPacketLogic.INSTANCE::action));
        NET_CHANNEL.registerServerbound(ServerNetwork.WithdrawCoinPacket.class, (WithdrawCoinPacketLogic.INSTANCE::action));
        NET_CHANNEL.registerServerbound(ServerNetwork.BalancePacket.class, (BalancePacketLogic.INSTANCE::action));
    }
}
