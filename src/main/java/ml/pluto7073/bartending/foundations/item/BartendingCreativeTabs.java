package ml.pluto7073.bartending.foundations.item;

import ml.pluto7073.bartending.TheArtOfBartending;
import ml.pluto7073.bartending.content.alcohol.AlcoholicDrinks;
import ml.pluto7073.bartending.content.block.BartendingBlocks;
import ml.pluto7073.bartending.content.fluid.BartendingFluids;
import ml.pluto7073.bartending.content.item.BartendingItems;
import ml.pluto7073.bartending.foundations.util.BrewingUtil;
import ml.pluto7073.pdapi.block.PDBlocks;
import ml.pluto7073.pdapi.util.DrinkUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BartendingCreativeTabs {

    public static final ResourceKey<CreativeModeTab> MAIN_TAB =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, TheArtOfBartending.asId("main"));

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MAIN_TAB,
                FabricItemGroup.builder().title(Component.translatable("itemGroup.bartending.main"))
                        .icon(() -> new ItemStack(BartendingItems.RED_WINE)).build());
        ItemGroupEvents.modifyEntriesEvent(MAIN_TAB).register(stacks -> {

            // Blocks
            stacks.accept(BartendingItems.BOILER);
            stacks.accept(BartendingItems.DISTILLERY);
            stacks.accept(BartendingItems.BOTTLER);
            stacks.accept(PDBlocks.DRINK_WORKSTATION);
            stacks.accept(BartendingItems.COUNTER_TOP);
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.OAK));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.SPRUCE));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.BIRCH));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.JUNGLE));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.ACACIA));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.DARK_OAK));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.MANGROVE));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.CHERRY));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.BAMBOO));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.CRIMSON));
            stacks.accept(BartendingBlocks.BARRELS.get(WoodType.WARPED));
            stacks.acceptAll(BartendingBlocks.BARRELS.values().stream()
                    .filter(barrel -> !new ResourceLocation(barrel.woodType.name()).getNamespace().equals("minecraft"))
                    .sorted(DrinkUtil.alphabetizer(barrel -> BuiltInRegistries.BLOCK.getKey(barrel).toString()))
                    .map(Block::asItem).map(ItemStack::new).toList());

            // Bottles
            stacks.accept(BartendingItems.WINE_BOTTLE);
            stacks.accept(BartendingItems.BEER_BOTTLE);
            stacks.accept(BartendingItems.JUG);
            stacks.accept(BartendingItems.LIQUOR_BOTTLE);
            stacks.accept(Items.GLASS_BOTTLE);
            stacks.accept(BartendingItems.WINE_GLASS);
            stacks.accept(BartendingItems.COCKTAIL_GLASS);
            stacks.accept(BartendingItems.SHOT_GLASS);

            // Drinks
            stacks.accept(BartendingItems.JUG_OF_BEER);
            stacks.accept(BartendingItems.JUG_OF_WHEAT_BEER);
            stacks.accept(BartendingItems.JUG_OF_DARK_BEER);
            stacks.accept(BartendingItems.MEAD);
            stacks.accept(BartendingItems.APPLE_MEAD);
            stacks.accept(BartendingItems.RED_WINE);
            stacks.accept(BartendingItems.WHITE_WINE);
            stacks.accept(BartendingItems.SWEET_VERMOUTH);
            stacks.accept(BartendingItems.DRY_VERMOUTH);
            stacks.accept(BartendingItems.ABSINTHE);
            stacks.accept(BartendingItems.VODKA);
            stacks.accept(BartendingItems.APPLE_LIQUEUR);
            stacks.accept(BartendingItems.RUM);
            if (AlcoholicDrinks.COFFEE_LIQUEUR.isVisible()) stacks.accept(BartendingItems.COFFEE_LIQUEUR);
            stacks.accept(BartendingItems.GIN);
            stacks.accept(BartendingItems.TEQUILA);
            if (AlcoholicDrinks.ORANGE_LIQUEUR.isVisible()) stacks.accept(BartendingItems.ORANGE_LIQUEUR);
            stacks.accept(BartendingItems.WHISKEY);

            // Servings
            stacks.accept(BartendingItems.BOTTLE_OF_BEER);
            stacks.accept(BartendingItems.BOTTLE_OF_WHEAT_BEER);
            stacks.accept(BartendingItems.BOTTLE_OF_DARK_BEER);
            stacks.accept(BartendingItems.GLASS_OF_BEER);
            stacks.accept(BartendingItems.GLASS_OF_WHEAT_BEER);
            stacks.accept(BartendingItems.GLASS_OF_DARK_BEER);
            stacks.accept(BartendingItems.GLASS_OF_MEAD);
            stacks.accept(BartendingItems.GLASS_OF_APPLE_MEAD);
            stacks.accept(BartendingItems.GLASS_OF_RED_WINE);
            stacks.accept(BartendingItems.GLASS_OF_WHITE_WINE);
            stacks.accept(BartendingItems.GLASS_OF_DRY_VERMOUTH);
            stacks.accept(BartendingItems.GLASS_OF_SWEET_VERMOUTH);
            stacks.accept(BartendingItems.GLASS_OF_ABSINTHE);
            stacks.accept(BartendingItems.SHOT_OF_SWEET_VERMOUTH);
            stacks.accept(BartendingItems.SHOT_OF_DRY_VERMOUTH);
            stacks.accept(BartendingItems.SHOT_OF_ABSINTHE);
            stacks.accept(BartendingItems.SHOT_OF_VODKA);
            stacks.accept(BartendingItems.SHOT_OF_APPLE_LIQUEUR);
            stacks.accept(BartendingItems.SHOT_OF_RUM);
            if (AlcoholicDrinks.COFFEE_LIQUEUR.isVisible()) stacks.accept(BartendingItems.SHOT_OF_COFFEE_LIQUEUR);
            stacks.accept(BartendingItems.SHOT_OF_GIN);
            stacks.accept(BartendingItems.SHOT_OF_TEQUILA);
            if (AlcoholicDrinks.ORANGE_LIQUEUR.isVisible()) stacks.accept(BartendingItems.SHOT_OF_ORANGE_LIQUEUR);
            stacks.accept(BartendingItems.SHOT_OF_WHISKEY);

            // Buckets
            stacks.accept(BartendingFluids.BEER.bucket());
            stacks.accept(BartendingFluids.WHEAT_BEER.bucket());
            stacks.accept(BartendingFluids.DARK_BEER.bucket());
            stacks.accept(BartendingFluids.MEAD.bucket());
            stacks.accept(BartendingFluids.RED_WINE.bucket());
            stacks.accept(BartendingFluids.WHITE_WINE.bucket());
            stacks.accept(BartendingFluids.SWEET_VERMOUTH.bucket());
            stacks.accept(BartendingFluids.DRY_VERMOUTH.bucket());
            stacks.accept(BartendingFluids.ABSINTHE.bucket());
            stacks.accept(BartendingFluids.VODKA.bucket());
            stacks.accept(BartendingFluids.APPLE_LIQUEUR.bucket());
            stacks.accept(BartendingFluids.RUM.bucket());
            if (AlcoholicDrinks.COFFEE_LIQUEUR.isVisible()) stacks.accept(BartendingFluids.COFFEE_LIQUEUR.bucket());
            stacks.accept(BartendingFluids.GIN.bucket());
            stacks.accept(BartendingFluids.TEQUILA.bucket());
            if (AlcoholicDrinks.ORANGE_LIQUEUR.isVisible()) stacks.accept(BartendingFluids.ORANGE_LIQUEUR.bucket());
            stacks.accept(BartendingFluids.WHISKEY.bucket());
        });
    }

}
