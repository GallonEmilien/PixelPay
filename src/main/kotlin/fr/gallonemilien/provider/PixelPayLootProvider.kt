package fr.gallonemilien.provider

import fr.gallonemilien.block.MoneyBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class PixelPayLootProvider(dataOuput : FabricDataOutput, registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>?)
    : FabricBlockLootTableProvider(dataOuput, registryLookup) {
    override fun generate() {
        addDrop(MoneyBlocks.ATM_BLOCK, drops(MoneyBlocks.ATM_BLOCK))
        addDrop(MoneyBlocks.MONEY_PRESS, drops(MoneyBlocks.MONEY_PRESS))
    }
}