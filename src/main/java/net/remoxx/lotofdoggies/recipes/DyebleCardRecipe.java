package net.remoxx.lotofdoggies.recipes;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.remoxx.lotofdoggies.items.CardItem;

public class DyebleCardRecipe extends CustomRecipe {

    public final RecipeSerializer INSTANCE_SERIALIZER = new SimpleCraftingRecipeSerializer<>(DyebleCardRecipe::new);
    public DyebleCardRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {super(pId, pCategory);}

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        int backpackCount = 0;
        int dyeCount = 0;

        for (int i = 0; i < pInv.getContainerSize(); ++i) {
            ItemStack stack = pInv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof CardItem) {
                    ++backpackCount;
                } else if (stack.is(Tags.Items.DYES)) {
                    ++dyeCount;
                } else {
                    return false;
                }
            }
        }

        return backpackCount == 1 && dyeCount > 0;
    }

    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        ItemStack card = ItemStack.EMPTY;
        Collection<ItemStack> dyes = new ArrayList<>();

        for (int i = 0; i < pContainer.getContainerSize(); ++i) {
            ItemStack stack = pContainer.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof CardItem) {
                    card = stack;
                } else if (stack.is(Tags.Items.DYES)) {
                    dyes.add(stack);
                }
            }
        }

        ItemStack result = card.copyWithCount(1);
        applyDyes(result, dyes);
        return result;
    }

    private static void applyDyes(ItemStack backpack, Collection<ItemStack> dyes) {
        int[] componentSums = new int[3];
        int maxColorSum = 0;
        int colorCount = 0;

        int backpackColor = CardItem.getCardColor(backpack);
        if (backpackColor != DyeColor.WHITE.getFireworkColor()) {
            float r = (float) (backpackColor >> 16 & 255) / 255.0F;
            float g = (float) (backpackColor >> 8 & 255) / 255.0F;
            float b = (float) (backpackColor & 255) / 255.0F;
            maxColorSum = (int) ((float) maxColorSum + Math.max(r, Math.max(g, b)) * 255.0F);
            componentSums[0] = (int) ((float) componentSums[0] + r * 255.0F);
            componentSums[1] = (int) ((float) componentSums[1] + g * 255.0F);
            componentSums[2] = (int) ((float) componentSums[2] + b * 255.0F);
            ++colorCount;
        }

        for (ItemStack dye : dyes) {
            DyeColor dyeColor = DyeColor.getColor(dye);
            if (dyeColor != null) {
                float[] componentValues = dyeColor.getTextureDiffuseColors();
                int r = (int) (componentValues[0] * 255.0F);
                int g = (int) (componentValues[1] * 255.0F);
                int b = (int) (componentValues[2] * 255.0F);
                maxColorSum += Math.max(r, Math.max(g, b));
                componentSums[0] += r;
                componentSums[1] += g;
                componentSums[2] += b;
                ++colorCount;
            }
        }

        if (colorCount > 0) {
            int r = componentSums[0] / colorCount;
            int g = componentSums[1] / colorCount;
            int b = componentSums[2] / colorCount;
            float maxAverage = (float) maxColorSum / (float) colorCount;
            float max = (float) Math.max(r, Math.max(g, b));
            r = (int) ((float) r * maxAverage / max);
            g = (int) ((float) g * maxAverage / max);
            b = (int) ((float) b * maxAverage / max);
            int finalColor = (r << 8) + g;
            finalColor = (finalColor << 8) + b;
            CardItem.setCardColor(backpack, finalColor);
        }
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return INSTANCE_SERIALIZER;
    }
}