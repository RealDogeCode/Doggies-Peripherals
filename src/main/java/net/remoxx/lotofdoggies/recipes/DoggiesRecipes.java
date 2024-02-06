package net.remoxx.lotofdoggies.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.remoxx.lotofdoggies.DoggiesMod;

public class DoggiesRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DoggiesMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DyebleCardRecipe>> GEM_CUTTING_SERIALIZER =
            SERIALIZERS.register("dyeable_card", () -> new DyebleCardRecipe(new ResourceLocation("dyeable_card"), CraftingBookCategory.REDSTONE).INSTANCE_SERIALIZER);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
