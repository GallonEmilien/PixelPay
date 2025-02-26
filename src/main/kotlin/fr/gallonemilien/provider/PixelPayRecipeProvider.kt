package fr.gallonemilien.provider

import fr.gallonemilien.block.MoneyBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.TagKey
import java.util.concurrent.CompletableFuture

class PixelPayRecipeProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>)
    : FabricRecipeProvider(output, registriesFuture) {

    override fun generate(exporter: RecipeExporter?) {

        ShapedRecipeJsonBuilder
            .create(RecipeCategory.BUILDING_BLOCKS, MoneyBlocks.MONEY_PRESS)
            .input('A', Items.ANDESITE)
            .input('L', Items.OAK_LOG)
            .input('P', Items.PISTON)
            .input('B', Items.IRON_BLOCK)
            .pattern("AAA")
            .pattern("LPL")
            .pattern("LBL")
            .criterion(hasItem(Items.PISTON), RecipeProvider.conditionsFromItem(Items.PISTON))
            .offerTo(exporter)

        ShapedRecipeJsonBuilder
            .create(RecipeCategory.BUILDING_BLOCKS, MoneyBlocks.ATM_BLOCK)
            .input('L', Items.OAK_LOG)
            .input('G', Items.GLASS_PANE)
            .input('R', Items.REDSTONE_BLOCK)
            .input('C', Items.COMPARATOR)
            .pattern("LGL")
            .pattern("RCR")
            .pattern("LLL")
            .criterion(hasItem(Items.COMPARATOR), RecipeProvider.conditionsFromItem(Items.COMPARATOR))
            .offerTo(exporter)
    }
}