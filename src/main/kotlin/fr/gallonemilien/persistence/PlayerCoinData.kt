package fr.gallonemilien.persistence

import fr.gallonemilien.item.coin.CoinType
import kotlin.collections.HashMap

class PlayerCoinData  {
    var dataMap: HashMap<CoinType, Int> = HashMap()

    constructor() {
        CoinType.entries.forEach {
            dataMap[it] = 0
        }
    }

    constructor(dataMap: HashMap<CoinType, Int>) {
        this.dataMap = dataMap
    }
}