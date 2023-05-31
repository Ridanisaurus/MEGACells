package gripe._90.megacells.block;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import appeng.api.config.SecurityPermissions;
import appeng.api.networking.IManagedGridNode;
import appeng.api.util.IOrientable;
import appeng.block.AEBaseBlockItem;
import appeng.block.AEBaseEntityBlock;
import appeng.core.definitions.AEItems;
import appeng.helpers.iface.PatternProviderLogic;
import appeng.helpers.iface.PatternProviderLogicHost;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.menu.implementations.PatternProviderMenu;
import appeng.menu.locator.MenuLocators;
import appeng.util.InteractionUtil;
import appeng.util.inv.AppEngInternalInventory;
import appeng.util.inv.filter.AEItemDefinitionFilter;

import gripe._90.megacells.block.entity.MEGAPatternProviderBlockEntity;
import gripe._90.megacells.definition.MEGATranslations;

public class MEGAPatternProviderBlock extends AEBaseEntityBlock<MEGAPatternProviderBlockEntity> {
    public static final BooleanProperty OMNIDIRECTIONAL = BooleanProperty.create("omnidirectional");
    public static final MenuType<Menu> MENU = MenuTypeBuilder
            .create(Menu::new, PatternProviderLogicHost.class)
            .requirePermission(SecurityPermissions.BUILD)
            .build("mega_pattern_provider");

    public MEGAPatternProviderBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(OMNIDIRECTIONAL, true));
    }

    public static PatternProviderLogic createLogic(IManagedGridNode mainNode, PatternProviderLogicHost host) {
        var logic = new PatternProviderLogic(mainNode, host, 18);
        ((AppEngInternalInventory) logic.getPatternInv())
                .setFilter(new AEItemDefinitionFilter(AEItems.PROCESSING_PATTERN));
        return logic;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OMNIDIRECTIONAL);
    }

    @Override
    protected BlockState updateBlockStateFromBlockEntity(BlockState currentState, MEGAPatternProviderBlockEntity be) {
        return currentState.setValue(OMNIDIRECTIONAL, be.isOmniDirectional());
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos,
            boolean isMoving) {
        var be = this.getBlockEntity(level, pos);
        if (be != null) {
            be.getLogic().updateRedstoneState();
        }
    }

    @Override
    public InteractionResult onActivated(Level level, BlockPos pos, Player p,
            InteractionHand hand,
            @Nullable ItemStack heldItem, BlockHitResult hit) {
        if (InteractionUtil.isInAlternateUseMode(p)) {
            return InteractionResult.PASS;
        }

        var be = this.getBlockEntity(level, pos);

        if (be != null) {
            if (!level.isClientSide()) {
                be.openMenu(p, MenuLocators.forBlockEntity(be));
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    protected boolean hasCustomRotation() {
        return true;
    }

    @Override
    protected void customRotateBlock(IOrientable rotatable, Direction axis) {
        if (rotatable instanceof MEGAPatternProviderBlockEntity patternProvider) {
            patternProvider.setSide(axis);
        }
    }

    public static class Item extends AEBaseBlockItem {
        public Item(Block id, Properties props) {
            super(id, props);
        }

        @Override
        public void addCheckedInformation(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(MEGATranslations.ProcessingOnly.text());
        }
    }

    public static class Menu extends PatternProviderMenu {
        public Menu(int id, Inventory playerInventory, PatternProviderLogicHost host) {
            super(MENU, id, playerInventory, host);
        }
    }
}
