package fr.gallonemilien;

import fr.gallonemilien.network.packetlogic.responses.BalancePacketResponseLogic;
import fr.gallonemilien.network.packetlogic.responses.DepositCoinPacketResponseLogic;
import fr.gallonemilien.network.packetlogic.responses.WithdrawCoinPacketResponseLogic;
import fr.gallonemilien.network.packetlogic.actions.DataSyncPacketLogic;
import fr.gallonemilien.network.packetlogic.actions.MachinSyncPacketLogic;
import fr.gallonemilien.persistence.PlayerCoinDataCodec;

public class ClientNetwork {

    public static void register() {
        ServerNetwork.NET_CHANNEL.registerClientbound(ServerNetwork.MachineSyncPacket.class, MachinSyncPacketLogic.INSTANCE::action);
        ServerNetwork.NET_CHANNEL.registerClientbound(
                ServerNetwork.DataSyncPacket.class,
                new PlayerCoinDataCodec(),
                DataSyncPacketLogic.INSTANCE::action
        );

        ServerNetwork.NET_CHANNEL.registerClientbound(ServerNetwork.DepositCoinPacketResponse.class, (DepositCoinPacketResponseLogic.INSTANCE::action));
        ServerNetwork.NET_CHANNEL.registerClientbound(ServerNetwork.WithdrawCoinPacketResponse.class, (WithdrawCoinPacketResponseLogic.INSTANCE::action));
        ServerNetwork.NET_CHANNEL.registerClientbound(ServerNetwork.BalancePacketResponse.class, (BalancePacketResponseLogic.INSTANCE::action));

    }
}
