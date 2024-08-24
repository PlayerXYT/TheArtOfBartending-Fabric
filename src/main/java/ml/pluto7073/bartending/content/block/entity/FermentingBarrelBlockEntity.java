package ml.pluto7073.bartending.content.block.entity;

import ml.pluto7073.bartending.TheArtOfBartending;
import ml.pluto7073.bartending.content.item.TAOBItems;
import ml.pluto7073.bartending.foundations.step.FermentingBrewerStep;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

@MethodsReturnNonnullByDefault
public class FermentingBarrelBlockEntity extends RandomizableContainerBlockEntity {

    public final WoodType woodType;

    private NonNullList<ItemStack> contents;
    private final ContainerOpenersCounter counter;

    public FermentingBarrelBlockEntity(WoodType woodType, BlockPos pos, BlockState blockState) {
        super(TAOBBlockEntities.FERMENTING_BARREL_BLOCK_ENTITY_TYPE, pos, blockState);
        this.woodType = woodType;
        contents = NonNullList.withSize(9, ItemStack.EMPTY);
        counter = new ContainerOpenersCounter() {
            protected void onOpen(Level level, BlockPos pos, BlockState state) {
                FermentingBarrelBlockEntity.this.playSound( SoundEvents.BARREL_OPEN);
                FermentingBarrelBlockEntity.this.updateBlockState(state, true);
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
                FermentingBarrelBlockEntity.this.playSound(SoundEvents.BARREL_CLOSE);
                FermentingBarrelBlockEntity.this.updateBlockState(state, false);
            }

            protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int count, int openCount) {
            }

            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)player.containerMenu).getContainer();
                    return container == FermentingBarrelBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.counter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.counter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void recheckOpen() {
        if (!this.remove) {
            this.counter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FermentingBarrelBlockEntity entity) {
        for (ItemStack stack : entity.getItems()) {
            if (!stack.is(TAOBItems.CONCOCTION)) continue;
            ListTag steps = stack.getOrCreateTag().getList("BrewingSteps", Tag.TAG_COMPOUND);
            CompoundTag data = steps.getCompound(steps.size() - 1);
            if (!FermentingBrewerStep.TYPE_ID.equals(data.getString("type")) ||
                    !(TheArtOfBartending.asId(entity.woodType.name() + "_fermenting_barrel"))
                            .toString().equals(data.getString("barrel"))) {
                CompoundTag tag = new CompoundTag();
                tag.putString("type", FermentingBrewerStep.TYPE_ID);
                tag.putString("barrel", TheArtOfBartending.asId(entity.woodType.name() + "_fermenting_barrel").toString());
                tag.putInt("ticks", 1);
                steps.add(tag);
            } else {
                int ticks = data.getInt("ticks");
                data.putInt("ticks", ++ticks);
                steps.set(steps.size() - 1, data);
            }
            stack.getOrCreateTag().put("BrewingSteps", steps);
        }
        setChanged(level, pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        contents = NonNullList.withSize(9, ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, contents);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, contents);
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return contents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemStacks) {
        contents = itemStacks;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.bartending." + woodType.name() + "_fermenting_barrel");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ChestMenu(MenuType.GENERIC_9x1, containerId, inventory, this, 1);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    void updateBlockState(BlockState state, boolean open) {
        //this.level.setBlock(this.getBlockPos(), state.setValue(BarrelBlock.OPEN, open), 3);
    }

    void playSound(SoundEvent sound) {
        double d = (double)this.worldPosition.getX() + 0.5;
        double e = (double)this.worldPosition.getY() + 0.5;
        double f = (double)this.worldPosition.getZ() + 0.5;
        this.level.playSound(null, d, e, f, sound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

}
