package fr.gallonemilien.item

import fr.gallonemilien.PixelPay
import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.item.coin.*
import fr.gallonemilien.item.custom.MoneyPressItem
import net.minecraft.block.AbstractBlock
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object PixelPayItems {

    val COPPER_COIN : CopperCoin = register("copper_coin", CopperCoin())
    val GOLD_COIN : GoldCoin = register("gold_coin", GoldCoin())
    val DIAMOND_COIN : DiamondCoin = register("diamond_coin", DiamondCoin())
    val EMERALD_COIN : EmeraldCoin = register("emerald_coin", EmeraldCoin())
    val BOUCKI_COIN : BouckiCoin = register("boucki_coin", BouckiCoin())

    /*val MONEYPRESS_ITEM : MoneyPressItem =
        register("moneypress_item", MoneyPressItem(MoneyBlocks.MONEY_PRESS, Settings()))*/

    val pixelPayItems : List<Item> = listOf(
        COPPER_COIN, GOLD_COIN, DIAMOND_COIN, EMERALD_COIN, BOUCKI_COIN
    )

    fun <T : Item> register(path: String, item: T): T =
        Registry.register(Registries.ITEM, PixelPay.id(path), item)
}