package fr.gallonemilien;

import fr.gallonemilien.network.packetlogic.MachinSyncPacketLogic;

public class ClientNetwork {

    public static void register() {
        ServerNetwork.NET_CHANNEL.registerClientbound(ServerNetwork.MachineSyncPacket.class, (MachinSyncPacketLogic.INSTANCE::action));
    }
}
