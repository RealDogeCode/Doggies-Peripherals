package net.remoxx.lotofdoggies;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.remoxx.lotofdoggies.blockEntities.DoggiesBlockEntities;
import net.remoxx.lotofdoggies.blocks.DoggiesBlocks;
import net.remoxx.lotofdoggies.items.CardItem;
import net.remoxx.lotofdoggies.items.DoggiesItems;
import net.remoxx.lotofdoggies.recipes.DoggiesRecipes;
import org.slf4j.Logger;

import java.awt.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DoggiesMod.MOD_ID)
public class DoggiesMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "lotofdoggies";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public DoggiesMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        DoggiesBlocks.loadBlocks(modEventBus);
        DoggiesItems.loadItems(modEventBus);
        DoggiesBlockEntities.loadBlockEntities(modEventBus);
        DoggiesRecipes.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        modEventBus.addListener(ColorHandler::registerItemColors);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            for(DyeColor color : DyeColor.values()){
                ItemStack is = new ItemStack(DoggiesItems.CARD_ITEM.get());
                CardItem.setCardColor(is, color.getFireworkColor());
                event.accept(is);
            }
            event.accept(DoggiesBlocks.CARD_READER_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ColorHandler {
        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.getItemColors().register(ColorHandler::getColor,
                    DoggiesItems.CARD_ITEM.get());
        }
        private static int getColor(ItemStack pStack, int tintIndex) {
            if (tintIndex == 0) {
                return CardItem.getCardColor(pStack);
            }
            return 0xFFFFFF;
        }
    }
}
