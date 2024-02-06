package net.remoxx.lotofdoggies.peripherals;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IDynamicPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.remoxx.lotofdoggies.DoggiesMod;
import net.remoxx.lotofdoggies.blockEntities.CardReaderBlockEntity;
import net.remoxx.lotofdoggies.items.CardItem;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class CardReaderPeripheral implements IDynamicPeripheral {
    public CardReaderBlockEntity CardReader;
    public CardReaderPeripheral(CardReaderBlockEntity CardReader) {
        this.CardReader = CardReader;
    }
    @Override
    public String[] getMethodNames() {
        return new String[]{"cardRead", "cardWrite"};
    }
    @Override
    public MethodResult callMethod(IComputerAccess computer, ILuaContext context, int method, IArguments arguments) throws LuaException {
        switch (method) {
            case 0:
                return MethodResult.pullEvent("card", (a) -> {
                    return MethodResult.of(a[1].toString());
                });
            case 1:
                IArguments escaped = arguments.escapes();
                return MethodResult.pullEvent("card", (a) -> {
                    this.CardReader.getCard().getTag().putString("value", escaped.getString(0));
                    return MethodResult.of(0);
                });
            default:
                return MethodResult.of(-1);
        }
    }

    @Override
    public String getType() {
        return "card_reader";
    }

    @Override
    public void attach(IComputerAccess computer) {
        CardReader.attach(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        CardReader.detach(computer);
    }

    @Nullable
    @Override
    public Object getTarget() {
        return CardReader;
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return this == other || other instanceof CardReaderPeripheral drive && drive.CardReader == CardReader;
    }
}
