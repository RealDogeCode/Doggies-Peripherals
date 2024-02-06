package net.remoxx.lotofdoggies.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.remoxx.lotofdoggies.DoggiesMod;

import static net.remoxx.lotofdoggies.DoggiesMod.MOD_ID;

public class DoggiesItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static RegistryObject<CardItem> CARD_ITEM;

    public static void loadItems(IEventBus bus){
        CARD_ITEM = ITEMS.register("card", () -> new CardItem(new Item.Properties().stacksTo(1)));

        ITEMS.register(bus);
        DoggiesMod.LOGGER.debug("Items Loaded! :D");
    }
}
