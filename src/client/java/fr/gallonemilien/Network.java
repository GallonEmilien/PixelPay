package fr.gallonemilien;

import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity;

public class Network {

    public static void register() {
        NetworkContent.NET_CHANNEL.registerClientbound(NetworkContent.MachineSyncPacket.class, ((message, access) -> {
            var entity = access.player().clientWorld.getBlockEntity(message.position());
            if (entity instanceof MoneyPressEntity machine) {
                machine.handleNetworkEntry(message);
            }
        }));
    }
}
