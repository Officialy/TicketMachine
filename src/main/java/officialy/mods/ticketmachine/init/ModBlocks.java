package officialy.mods.ticketmachine.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import officialy.mods.ticketmachine.Ticketmachine;
import officialy.mods.ticketmachine.block.TicketMachineBlock;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Ticketmachine.MODID);

    public static final DeferredBlock<TicketMachineBlock> TICKET_MACHINE_BLOCK = BLOCKS.register("ticket_machine", () -> new TicketMachineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));


}