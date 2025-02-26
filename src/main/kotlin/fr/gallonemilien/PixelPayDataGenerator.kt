package fr.gallonemilien

import fr.gallonemilien.provider.PixelPayLootProvider
import fr.gallonemilien.provider.PixelPayRecipeProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class PixelPayDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator?) {
        val pack : FabricDataGenerator.Pack = fabricDataGenerator!!.createPack()
        pack.addProvider(::PixelPayRecipeProvider)
        pack.addProvider(::PixelPayLootProvider)
    }
}