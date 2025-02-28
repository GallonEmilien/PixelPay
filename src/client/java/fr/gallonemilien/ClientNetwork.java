package fr.gallonemilien;

import fr.gallonemilien.network.packetlogic.DataSyncPacketLogic;
import fr.gallonemilien.network.packetlogic.MachinSyncPacketLogic;
import fr.gallonemilien.persistence.PlayerCoinDataCodec;

public class ClientNetwork {

    public static void register() {
        ServerNetwork.NET_CHANNEL.registerClientbound(ServerNetwork.MachineSyncPacket.class, MachinSyncPacketLogic.INSTANCE::action);
        ServerNetwork.NET_CHANNEL.registerClientbound(
                ServerNetwork.DataSyncPacket.class,
                new PlayerCoinDataCodec(),
                DataSyncPacketLogic.INSTANCE::action
        );

    }
}
