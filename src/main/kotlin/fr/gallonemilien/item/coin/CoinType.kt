package fr.gallonemilien.item.coin

import fr.gallonemilien.item.PixelPayItems
import net.minecraft.item.ItemStack

enum class CoinType(val coin: Coin) {
    BOUCKI(PixelPayItems.BOUCKI_COIN),
    COPPER(PixelPayItems.COPPER_COIN),
    GOLD(PixelPayItems.GOLD_COIN),
    DIAMOND(PixelPayItems.DIAMOND_COIN),
    EMERALD( PixelPayItems.EMERALD_COIN);

    companion object {
        fun getFromStack(coinCopy: ItemStack): CoinType? =
            entries.find { it.coin.asItem() == coinCopy.item }
    }
}
