package fr.gallonemilien.block

import fr.gallonemilien.RoyalMoney.Companion.MOD_ID
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object MoneyBlocksEntityTypes {


    val ATM_BLOCK_ENTITY : BlockEntityType<ATMEntityType> =
        register("atm_entity", BlockEntityType.Builder.create(::ATMEntityType, MoneyBlocks.ATM_BLOCK).build())

    private fun <T : BlockEntityType<*>> register(path: String, blockEntityType: T): T =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, path), blockEntityType)

}