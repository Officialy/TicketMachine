package officialy.mods.ticketmachine.block;

import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import officialy.mods.ticketmachine.Ticketmachine;
import officialy.mods.ticketmachine.init.ModBEs;
import officialy.mods.ticketmachine.menu.MenuTicketMachine;
import org.jetbrains.annotations.Nullable;

public class TicketMachineBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    private final TicketMachinePeripheral peripheral = new TicketMachinePeripheral(this);

    public TicketMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBEs.TICKET_MACHINE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("ticketmachine.name");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        int i;
        for (i = 0; i < nonNullList.size(); ++i) {
            items.set(i, nonNullList.get(i));
        }
        while (i < items.size()) {
            items.set(i, ItemStack.EMPTY);
            ++i;
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new MenuTicketMachine(containerId, playerInventory, (ContainerLevelAccess) level);
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new MenuTicketMachine(id, inventory, (ContainerLevelAccess) this);
    }

    public TicketMachineBlock getBlock() {
        return (TicketMachineBlock) getBlockState().getBlock();
    }

    public IPeripheral getPeripheral() {
        return peripheral;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        ContainerHelper.loadAllItems(tag, items, registries);
        super.loadAdditional(tag, registries);
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return switch (direction) {
            case DOWN -> new int[]{2}; // output
            case UP -> new int[]{0, 1}; // input
            default -> new int[]{0, 1}; // sides are also input
        };
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 0 && !stack.isEmpty() && stack.getItem() == Items.BLACK_DYE) {
            return true;
        }
        if (slot == 1 && !stack.isEmpty() && stack.getItem() == Items.PAPER) {
            return true;
        }

        return false;
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(i, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return true;
    }
}
