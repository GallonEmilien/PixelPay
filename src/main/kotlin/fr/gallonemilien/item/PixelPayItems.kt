package fr.gallonemilien.item

import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.item.coin.*
import fr.gallonemilien.item.custom.MoneyPressItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object PixelPayItems {

    val pixelPayItems : List<Item> = listOf(
        register("copper_coin", CopperCoin()),
        register("gold_coin", GoldCoin()),
        register("diamond_coin", DiamondCoin()),
        register("emerald_coin", EmeraldCoin()),
        register("boucki_coin", BouckiCoin()),
        //register("moneypress", MoneyPressItem(MONEY_PRE))
    )

    fun <T : Item> register(path: String, item: T): T =
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, path), item)
}