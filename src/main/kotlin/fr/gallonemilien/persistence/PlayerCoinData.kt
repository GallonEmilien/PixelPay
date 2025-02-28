package fr.gallonemilien.persistence

import fr.gallonemilien.item.coin.CoinType
import java.util.*

class PlayerCoinData {
    val dataMap : MutableMap<CoinType, Int> = EnumMap(CoinType::class.java)
}