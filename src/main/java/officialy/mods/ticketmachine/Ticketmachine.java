package officialy.mods.ticketmachine;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import officialy.mods.ticketmachine.init.ModBEs;
import officialy.mods.ticketmachine.init.ModBlocks;
import officialy.mods.ticketmachine.menu.MenuTicketMachine;
import org.slf4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Ticketmachine.MODID)
public class Ticketmachine {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "ticketmachine";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Ticketmachine.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>> TICKET_MACHINE_MENU = MENUS.register("ticket_machine_menu", () -> new MenuType<>(MenuTicketMachine::new, FeatureFlags.VANILLA_SET));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Ticketmachine.MODID);

    public static final DeferredHolder<Item, BlockItem> TICKET_MACHINE = ITEMS.register("ticket_machine", TicketMachineBlockItem::new);

    public Ticketmachine(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onCapability);
        modEventBus.addListener(this::addCreative);
        ModBlocks.BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        ModBEs.BLOCK_ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS)
            event.accept(TICKET_MACHINE.get());
    }

    @SubscribeEvent
    public void onCapability(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(PeripheralCapability.get(), ModBEs.TICKET_MACHINE_BLOCK_ENTITY.get(), (b, d) -> b.getPeripheral());
    }

    public static class TicketMachineBlockItem extends BlockItem {

        public TicketMachineBlockItem() {
            super(ModBlocks.TICKET_MACHINE_BLOCK.get(), new Item.Properties());
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            tooltipComponents.add(Component.literal("A computer is required to use this!").withStyle(ChatFormatting.RED));
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        }
    }
}
