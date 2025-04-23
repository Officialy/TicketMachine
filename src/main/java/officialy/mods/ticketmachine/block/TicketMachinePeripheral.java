package officialy.mods.ticketmachine.block;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import mods.railcraft.world.item.RailcraftItems;
import mods.railcraft.world.item.TicketItem;
import mods.railcraft.world.item.component.RailcraftDataComponents;
import mods.railcraft.world.item.component.TicketComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import officialy.mods.ticketmachine.Ticketmachine;
import org.jetbrains.annotations.Nullable;

public class TicketMachinePeripheral implements IPeripheral {

    private final TicketMachineBlockEntity ticketMachineBlockEntity;

    public TicketMachinePeripheral(TicketMachineBlockEntity ticketMachineBlockEntity) {
        this.ticketMachineBlockEntity = ticketMachineBlockEntity;
    }

    @Override
    public String getType() {
        return "ticket";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other != null && other.getClass() == getClass();
    }

    //Creates a ticket to the specified destination
    @LuaFunction(mainThread = true)
    public final void createTicket(String destination, int amount) {
        if (amount < 1 || amount > 64) {
            Ticketmachine.LOGGER.info("Creating ticket to " + destination + ", amount: " + amount);
            throw new IllegalStateException("Amount must be between 1 and 64");
        }
        if (destination == null || destination.isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be empty or null");
        }

        ItemStack blackDyeStack = ticketMachineBlockEntity.items.get(0);
        if (!Items.BLACK_DYE.getDefaultInstance().is(blackDyeStack.getItem())) {
            Ticketmachine.LOGGER.info("No black dye found in slot 0");
            throw new IllegalStateException("No black dye found");
        }
        if (blackDyeStack.getCount() < amount) {
            Ticketmachine.LOGGER.info("Not enough black dye: {} needs: {}", blackDyeStack.getCount(), amount);
            throw new IllegalStateException("Not enough black dye");
        }

        ItemStack paperStack = ticketMachineBlockEntity.items.get(1);
        if (!Items.PAPER.getDefaultInstance().is(paperStack.getItem())) {
            Ticketmachine.LOGGER.info("No paper found in slot 1");
            throw new IllegalStateException("No paper found");
        }
        if (paperStack.getCount() < amount) {
            Ticketmachine.LOGGER.info("Not enough paper: {} needs: {}", paperStack.getCount(), amount);
            throw new IllegalStateException("Not enough paper");
        }

        // Generate 'amount' tickets in a loop
        for (int i = 0; i < amount; i++) {
            ItemStack output = ticketMachineBlockEntity.items.get(2);

            ItemStack newTicket = RailcraftItems.TICKET.toStack();
            newTicket.set(RailcraftDataComponents.TICKET, new TicketComponent(destination, ticketMachineBlockEntity.getBlock().owner));

            if (output.isEmpty() || output.getItem() == Items.AIR) {
                ticketMachineBlockEntity.items.set(2, newTicket);
            } else {
                ItemStack existingTicket = output;
                if (!ItemStack.isSameItemSameComponents(existingTicket, newTicket)) {
                    Ticketmachine.LOGGER.info("Cannot add to existing ticket");
                    throw new IllegalArgumentException("Output items are not the same");
                }
                int existingCount = existingTicket.getCount();
                if (existingCount + 1 <= existingTicket.getMaxStackSize()) {
                    output.setCount(existingCount + 1);  // Only add one ticket at a time
                } else {
                    Ticketmachine.LOGGER.info("Not enough space in slot 2, item count exceeds max stack size: {} needs: {}", output.getCount(), amount);
                    throw new IllegalStateException("Not enough space in slot 2");
                }
            }

            // Reduce material count for each ticket generated
            ticketMachineBlockEntity.items.get(0).setCount(ticketMachineBlockEntity.items.get(0).getCount() - 1);
            ticketMachineBlockEntity.items.get(1).setCount(ticketMachineBlockEntity.items.get(1).getCount() - 1);
        }

        ticketMachineBlockEntity.setChanged();
        //todo play a printing sound
    }

    @Nullable
    @Override
    public Object getTarget() {
        return ticketMachineBlockEntity;
    }
}
