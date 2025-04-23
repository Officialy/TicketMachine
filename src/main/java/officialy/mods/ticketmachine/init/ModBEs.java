package officialy.mods.ticketmachine.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import officialy.mods.ticketmachine.Ticketmachine;
import officialy.mods.ticketmachine.block.TicketMachineBlockEntity;

public class ModBEs {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Ticketmachine.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TicketMachineBlockEntity>> TICKET_MACHINE_BLOCK_ENTITY = BLOCK_ENTITIES.register("ticket_machine", () -> BlockEntityType.Builder.of(TicketMachineBlockEntity::new, ModBlocks.TICKET_MACHINE_BLOCK.get()).build(null));

}