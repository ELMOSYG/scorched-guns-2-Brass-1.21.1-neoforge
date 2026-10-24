package top.ribs.scbrass;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Scorched Guns Brass, ported to 1.21.1 NeoForge.
 *
 * <p>For packs that have Scorched Guns but no other source of brass or zinc (Create being the usual one). It registers
 * the brass and zinc line, the ores, and a zinc ore feature so the mod's brass recipes have materials.</p>
 *
 * <p>Everything lives in this one class on purpose: an earlier version split the registries across {@code ScBrass},
 * {@code ModItems} and {@code ModBlocks}, and the cross-class static initialisers could run in an order that left a
 * registry null, which surfaced as {@code ExceptionInInitializerError} while a creative tab was being built.</p>
 */
@Mod(ScBrass.MOD_ID)
public final class ScBrass {
   public static final String MOD_ID = "scbrass";

   public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
   public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
   public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

   // plain materials
   public static final DeferredItem<Item> BRASS_INGOT = ITEMS.registerSimpleItem("brass_ingot", new Item.Properties());
   public static final DeferredItem<Item> BRASS_BLEND = ITEMS.registerSimpleItem("brass_blend", new Item.Properties());
   public static final DeferredItem<Item> ZINC_INGOT = ITEMS.registerSimpleItem("zinc_ingot", new Item.Properties());
   public static final DeferredItem<Item> ZINC_NUGGET = ITEMS.registerSimpleItem("zinc_nugget", new Item.Properties());
   public static final DeferredItem<Item> RAW_ZINC = ITEMS.registerSimpleItem("raw_zinc", new Item.Properties());

   // storage blocks and ores
   public static final DeferredBlock<Block> BRASS_BLOCK = block("brass_block", MapColor.COLOR_YELLOW, 5.0F, 6.0F, SoundType.METAL);
   public static final DeferredBlock<Block> ZINC_BLOCK = block("zinc_block", MapColor.COLOR_LIGHT_GRAY, 5.0F, 6.0F, SoundType.METAL);
   public static final DeferredBlock<Block> RAW_ZINC_BLOCK = block("raw_zinc_block", MapColor.COLOR_LIGHT_GRAY, 5.0F, 6.0F, SoundType.STONE);
   public static final DeferredBlock<Block> ZINC_ORE = block("zinc_ore", MapColor.STONE, 3.0F, 3.0F, SoundType.STONE);
   public static final DeferredBlock<Block> DEEPSLATE_ZINC_ORE = block("deepslate_zinc_ore", MapColor.DEEPSLATE, 4.5F, 3.0F, SoundType.DEEPSLATE);

   private static DeferredBlock<Block> block(String name, MapColor color, float destroy, float explosion, SoundType sound) {
      DeferredBlock<Block> registered = BLOCKS.registerSimpleBlock(
         name,
         BlockBehaviour.Properties.of().mapColor(color).strength(destroy, explosion).sound(sound).requiresCorrectToolForDrops()
      );
      ITEMS.registerSimpleBlockItem(registered);
      return registered;
   }

   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = TABS.register(
      "main",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("creativetab.sc_brass"))
         .icon(() -> new ItemStack(BRASS_INGOT.get()))
         .displayItems((parameters, output) -> {
            // Resolved at build time, after every registry in this class has been filled.
            output.accept(BRASS_INGOT.get());
            output.accept(BRASS_BLEND.get());
            output.accept(ZINC_INGOT.get());
            output.accept(ZINC_NUGGET.get());
            output.accept(RAW_ZINC.get());
            output.accept(BRASS_BLOCK.get().asItem());
            output.accept(ZINC_BLOCK.get().asItem());
            output.accept(RAW_ZINC_BLOCK.get().asItem());
            output.accept(ZINC_ORE.get().asItem());
            output.accept(DEEPSLATE_ZINC_ORE.get().asItem());
         })
         .build()
   );

   public ScBrass(IEventBus modBus, ModContainer container) {
      ITEMS.register(modBus);
      BLOCKS.register(modBus);
      TABS.register(modBus);
   }
}
