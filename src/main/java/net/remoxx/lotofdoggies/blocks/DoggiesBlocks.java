package net.remoxx.lotofdoggies.blocks;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.remoxx.lotofdoggies.DoggiesMod;
import net.remoxx.lotofdoggies.items.DoggiesItems;

import static net.remoxx.lotofdoggies.DoggiesMod.MOD_ID;

public class DoggiesBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static RegistryObject<CardReaderBlock> CARD_READER_BLOCK;
    public static void loadBlocks(IEventBus bus){
        CARD_READER_BLOCK = BLOCKS.register("card_reader_block", () -> new CardReaderBlock(BlockBehaviour.Properties.of()));
        DoggiesItems.ITEMS.register("card_reader_block", () -> new BlockItem(CARD_READER_BLOCK.get(), new Item.Properties()));

        BLOCKS.register(bus);
        DoggiesMod.LOGGER.info("Blocks Loaded! :D");
    }
}
