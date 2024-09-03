package ml.pluto7073.bartending.foundations.datagen;

import ml.pluto7073.bartending.TheArtOfBartending;
import ml.pluto7073.bartending.content.block.BartendingBlocks;
import ml.pluto7073.bartending.content.fluid.BartendingFluids;
import ml.pluto7073.bartending.content.item.BartendingItems;
import ml.pluto7073.bartending.foundations.fluid.FluidHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;
import java.util.function.Consumer;

public class BartendingModelsProvider extends FabricModelProvider {

    public BartendingModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        for (FluidHolder fluid : BartendingFluids.FLUIDS) {
            generators.createAirLikeBlock(fluid.block(), new ResourceLocation("block/water_still"));
        }
        for (Block block : BartendingBlocks.BARRELS.values()) {
            generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                    .with(generators.createColumnWithFacing())
                    .with(PropertyDispatch.property(BlockStateProperties.OPEN)
                            .select(false, Variant.variant().with(VariantProperties.MODEL,
                                    TheArtOfBartending.asId("block/fermenting_barrel")))
                            .select(true, Variant.variant().with(VariantProperties.MODEL,
                                    TheArtOfBartending.asId("block/fermenting_barrel_open")))));
            generators.delegateItemModel(block, TheArtOfBartending.asId("block/fermenting_barrel"));
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        for (FluidHolder fluid : BartendingFluids.FLUIDS) {
            Item bucket = fluid.bucket();
            generators.generateFlatItem(bucket, new ModelTemplate(
                    Optional.of(TheArtOfBartending.asId("item/bucket")),
                    Optional.empty()
            ));
        }
        for (Item shot : BartendingItems.SHOTS.values()) {
            generators.generateFlatItem(shot, new ModelTemplate(
                    Optional.of(TheArtOfBartending.asId("item/liquor_shot")),
                    Optional.empty()
            ));
        }
        for (Item liquor : BartendingItems.BOTTLES.values()
                .stream().filter(item -> item.emptyBottleItem == BartendingItems.LIQUOR_BOTTLE).toList()) {
            generators.generateFlatItem(liquor, new ModelTemplate(
                    Optional.of(TheArtOfBartending.asId("item/liquor")),
                    Optional.empty()
            ));
        }
        for (Item wine : BartendingItems.BOTTLES.values()
                .stream().filter(item -> item.emptyBottleItem == BartendingItems.WINE_BOTTLE).toList()) {
            generators.generateFlatItem(wine, new ModelTemplate(
                    Optional.of(TheArtOfBartending.asId("item/sealed_wine_bottle")),
                    Optional.empty()
            ));
        }
    }

}