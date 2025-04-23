package officialy.mods.ticketmachine.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import officialy.mods.ticketmachine.init.ModBlocks;

public class MenuTicketMachine extends ItemCombinerMenu {

    public MenuTicketMachine(int containerId, Inventory playerInventory) {
        super(MenuType.SMITHING, containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public MenuTicketMachine(int containerId, Inventory playerInventory, ContainerLevelAccess levelAccess) {
        super(MenuType.SMITHING, containerId, playerInventory, levelAccess);
    }

    @Override
    protected boolean mayPickup(Player player, boolean b) {
        return false;
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {

    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(ModBlocks.TICKET_MACHINE_BLOCK.get());
    }

    @Override
    public void createResult() {

    }

    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 8, 48, p_266643_ -> p_266643_ == Items.PAPER.getDefaultInstance())
                .withSlot(1, 26, 48, p_286208_ -> p_286208_ == Items.BLACK_DYE.getDefaultInstance())
                .withResultSlot(2, 98, 48)
                .build();
    }

}
