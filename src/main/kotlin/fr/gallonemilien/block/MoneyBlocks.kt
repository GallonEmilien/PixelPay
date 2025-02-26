package fr.gallonemilien.block

import fr.gallonemilien.PixelPay
import fr.gallonemilien.block.custom.MoneyPress
import fr.gallonemilien.item.PixelPayItems
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object MoneyBlocks {

    val MONEY_PRESS : Block = register("moneypress", MoneyPress())
    val ATM_BLOCK : Block = register("atm", ATM())

    private fun <T : Block> register(path: String, block: T): T {
        Registry.register(Registries.BLOCK, PixelPay.id(path), block)
        PixelPayItems.register(path,BlockItem(block, Item.Settings()))
        return block
    }
}