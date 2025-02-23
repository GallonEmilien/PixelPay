package fr.gallonemilien.block

import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.item.PixelPayItems
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object MoneyBlocks {

    val ATM_BLOCK : Block = register("atm", ATM())

    private fun <T : Block> register(path: String, block: T): T {
        Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, path), block)
        PixelPayItems.register(path,BlockItem(block, Item.Settings()))
        return block
    }
}