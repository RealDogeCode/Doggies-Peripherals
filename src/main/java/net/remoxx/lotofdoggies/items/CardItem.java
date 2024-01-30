package net.remoxx.lotofdoggies.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CardItem extends Item {
    public CardItem(Properties properties) {
        super(properties);
    }

    public static void setCardColor(ItemStack itemStack, int color){
        itemStack.getOrCreateTag().putInt("color", color);
    }

    public static int getCardColor(ItemStack itemStack){
        return itemStack.hasTag() ? itemStack.getOrCreateTag().getInt("color") : 16383998;
    }

    /*@Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(stack.getNbt() != null && !stack.getNbt().getString("value").toString().isEmpty() ?
                Text.literal(stack.getNbt().getString("value").toString()).formatted(Formatting.GRAY) :
                Text.literal("empty").formatted(Formatting.GRAY));
    }*/
}
