package net.remoxx.lotofdoggies.blocks;

import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.remoxx.lotofdoggies.blockEntities.CardReaderBlockEntity;
import net.remoxx.lotofdoggies.items.CardItem;
import org.jetbrains.annotations.Nullable;

public class CardReaderBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public CardReaderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide && pPlayer.getItemInHand(pHand).getItem() instanceof CardItem && pLevel.getBlockEntity(pPos) instanceof CardReaderBlockEntity blockEntity) {
            if(!pPlayer.getItemInHand(pHand).hasTag()) pPlayer.getItemInHand(pHand).setTag(new CompoundTag());
            blockEntity.setCard(pPlayer.getItemInHand(pHand));

            for (IComputerAccess computerAccess : blockEntity.computers){
                computerAccess.queueEvent("card", pPlayer.getItemInHand(pHand).getTag().getString("value"));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CardReaderBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> Builder) {
        super.createBlockStateDefinition(Builder);
        Builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}
