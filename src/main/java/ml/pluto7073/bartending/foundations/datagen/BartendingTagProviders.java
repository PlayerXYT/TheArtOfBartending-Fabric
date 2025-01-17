package ml.pluto7073.bartending.foundations.datagen;

import com.simibubi.create.AllTags;
import ml.pluto7073.bartending.content.block.BartendingBlocks;
import ml.pluto7073.bartending.content.fluid.BartendingFluids;
import ml.pluto7073.bartending.content.item.BartendingItems;
import ml.pluto7073.bartending.foundations.fluid.FluidHolder;
import ml.pluto7073.bartending.foundations.item.AlcoholicShotItem;
import ml.pluto7073.pdapi.tag.PDTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BartendingTagProviders {

    public static class FluidTagProvider extends FabricTagProvider.FluidTagProvider {

        public FluidTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            FabricTagBuilder water = getOrCreateTagBuilder(FluidTags.WATER);
            for (FluidHolder fluid : BartendingFluids.FLUIDS) {
                water.add(fluid.still()).add(fluid.flowing());
            }
        }

    }

    public static class BlockTagProvider extends FabricTagProvider.BlockTagProvider {

        public BlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            FabricTagBuilder pickaxe = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE);
            pickaxe.add(BartendingBlocks.BOILER, BartendingBlocks.BOTTLER, BartendingBlocks.DISTILLERY);

            FabricTagBuilder axe = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE);
            BartendingBlocks.BARRELS.values().forEach(axe::add);
        }

    }

    public static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {


        public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            FabricTagBuilder workstationDrinks = getOrCreateTagBuilder(PDTags.WORKSTATION_DRINKS);
            BartendingItems.GLASSES.values().forEach(workstationDrinks::add);
            FabricTagBuilder uprightOnBelt = getOrCreateTagBuilder(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag);
            uprightOnBelt.add(BartendingItems.SHOT_GLASS, BartendingItems.WINE_GLASS, BartendingItems.COCKTAIL_GLASS,
                    BartendingItems.CONCOCTION, BartendingItems.BEER_BOTTLE, BartendingItems.WINE_BOTTLE,
                    BartendingItems.LIQUOR_BOTTLE, BartendingItems.JUG);

            BartendingItems.SHOTS.values().forEach(uprightOnBelt::add);
            BartendingItems.BOTTLES.values().forEach(uprightOnBelt::add);
            BartendingItems.GLASSES.values().forEach(uprightOnBelt::add);
            BartendingItems.SERVING_BOTTLES.values().forEach(uprightOnBelt::add);
        }
    }

}
