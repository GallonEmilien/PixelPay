package fr.gallonemilien.block.entity

import fr.gallonemilien.PixelPay
import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.block.MoneyBlocks
import fr.gallonemilien.block.entity.atm.ATMEntity
import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object MoneyBlocksEntities {


    val ATM_BLOCK_ENTITY : BlockEntityType<ATMEntity> =
        register("atm_entity", BlockEntityType.Builder.create(::ATMEntity, MoneyBlocks.ATM_BLOCK).build())

    val MONEY_PRESS_ENTITY : BlockEntityType<MoneyPressEntity> =
        register("moneypress_entity", BlockEntityType.Builder.create(::MoneyPressEntity, MoneyBlocks.MONEY_PRESS).build())

    private fun <T : BlockEntityType<*>> register(path: String, blockEntityType: T): T =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, PixelPay.id( path), blockEntityType)

}