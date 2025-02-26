package fr.gallonemilien.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.gallonemilien.block.entity.moneypress.MoneyPressInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import java.util.List;

public class MoneyPressRecipe implements Recipe<MoneyPressInventory> {
    private final ItemStack output;
    private final List<Ingredient> recipeItems;

    public MoneyPressRecipe(List<Ingredient> ingredients, ItemStack itemStack) {
        this.output = itemStack;
        this.recipeItems = ingredients;
    }


    @Override
    public boolean matches(MoneyPressInventory input, World world) {
        if(world.isClient()) {
            return false;
        }
        return     recipeItems.get(0).test(input.getStack(0))
                && recipeItems.get(1).test(input.getStack(1))
                && recipeItems.get(2).test(input.getStack(2));
    }

    @Override
    public ItemStack craft(MoneyPressInventory input, RegistryWrapper.WrapperLookup lookup) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MoneyPressRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "moneypress";
    }

    public static class Serializer implements RecipeSerializer<MoneyPressRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "moneypress";

        @Override
        public MapCodec<MoneyPressRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.list(Ingredient.DISALLOW_EMPTY_CODEC).fieldOf("ingredients").forGetter(MoneyPressRecipe::getIngredients),
                    ItemStack.CODEC.fieldOf("result").forGetter(MoneyPressRecipe::getOutput)
            ).apply(instance, MoneyPressRecipe::new));
        }


        private final PacketCodec<RegistryByteBuf, MoneyPressRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), MoneyPressRecipe::getIngredients,
                ItemStack.PACKET_CODEC, MoneyPressRecipe::getOutput,
                MoneyPressRecipe::new
        );


        @Override
        public PacketCodec<RegistryByteBuf, MoneyPressRecipe> packetCodec() {
            return PACKET_CODEC;
        }

    }
}