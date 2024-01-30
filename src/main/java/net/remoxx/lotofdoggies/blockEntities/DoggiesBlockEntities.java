package net.remoxx.lotofdoggies.blockEntities;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.remoxx.lotofdoggies.DoggiesMod;
import net.remoxx.lotofdoggies.blocks.DoggiesBlocks;

import static net.remoxx.lotofdoggies.DoggiesMod.MOD_ID;

public class DoggiesBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

    public static RegistryObject<BlockEntityType<CardReaderBlockEntity>> CARD_READER_BLOCK_ENTITY;
    public static void loadBlockEntities(IEventBus bus){
        CARD_READER_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                "card_reader_block_entity",
                () -> BlockEntityType.Builder.of(CardReaderBlockEntity::new, DoggiesBlocks.CARD_READER_BLOCK.get()).build(null));

        BLOCK_ENTITIES.register(bus);
        DoggiesMod.LOGGER.info("Blocks Loaded! :D");
    }
}
