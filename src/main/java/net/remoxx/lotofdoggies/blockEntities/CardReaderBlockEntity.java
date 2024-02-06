package net.remoxx.lotofdoggies.blockEntities;

import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.remoxx.lotofdoggies.peripherals.CardReaderPeripheral;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

import static dan200.computercraft.shared.Capabilities.CAPABILITY_PERIPHERAL;
public class CardReaderBlockEntity extends BlockEntity {
    ItemStack card;
    public Set<IComputerAccess> computers = new HashSet<IComputerAccess>();
    private final CardReaderPeripheral peripheral = new CardReaderPeripheral(this);
    public CardReaderBlockEntity(BlockPos pos, BlockState state) {
        super(DoggiesBlockEntities.CARD_READER_BLOCK_ENTITY.get(), pos, state);
    }

    public void setCard(ItemStack cardValue) {
        this.card = cardValue;
    }
    public ItemStack getCard() {
        return card;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if( cap == CAPABILITY_PERIPHERAL )
        {
            return LazyOptional.of( () -> new CardReaderPeripheral( this ) ).cast();
        }

        return super.getCapability( cap, side );
    }

    public void detach(IComputerAccess computer) {
        computers.remove(computer);
    }

    public void attach(IComputerAccess computer) {
        computers.add(computer);
    }

    public Set<IComputerAccess> getAllComputers() {
        return computers;
    }
}
