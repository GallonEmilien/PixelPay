package fr.gallonemilien.persistence

import fr.gallonemilien.ServerNetwork.DataSyncPacket
import fr.gallonemilien.item.coin.CoinType
import io.wispforest.endec.Deserializer
import io.wispforest.endec.Serializer
import io.wispforest.endec.SerializationContext
import io.wispforest.endec.StructEndec

class PlayerCoinDataCodec : StructEndec<DataSyncPacket> {

    override fun encodeStruct(
        ctx: SerializationContext?,
        serializer: Serializer<*>?,
        struct: Serializer.Struct?,
        value: DataSyncPacket?
    ) {
        // Sérialiser le PlayerCoinData contenu dans DataSyncPacket
        val playerCoinData = value?.data ?: return
        playerCoinData.dataMap.forEach { (coinType, amount) ->
            // Écrire la clé (CoinType) comme String et la valeur (amount) comme Int
            serializer?.writeString(ctx, coinType.name)  // Écrire le nom de l'enum
            serializer?.writeInt(ctx, amount)  // Écrire l'entier associé à la clé
        }
    }

    override fun decodeStruct(
        ctx: SerializationContext?,
        deserializer: Deserializer<*>?,
        struct: Deserializer.Struct?
    ): DataSyncPacket {
        // Dé-sérialiser la map de données
        val map = hashMapOf<CoinType, Int>()
        // Parcourir les éléments sérialisés
        var index = 0
        deserializer?.tryRead {
            val coinTypeName = deserializer.readString(ctx) // Lire la clé (nom de l'enum) / on sort si on a plus rien
            val amount = deserializer.readInt(ctx)  // Lire la valeur (amount)
            val coinType = CoinType.valueOf(coinTypeName)  // CoinType à partir de la clé (nom de l'enum)
            map[coinType] = amount  // Ajoutez à la map
            index++
        }
        // Retourner un DataSyncPacket avec le PlayerCoinData dé-sérialisé
        return DataSyncPacket(PlayerCoinData(map))
    }
}
