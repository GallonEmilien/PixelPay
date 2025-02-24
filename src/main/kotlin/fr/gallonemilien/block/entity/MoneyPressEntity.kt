package fr.gallonemilien.block.entity

import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.item.PixelPayItems
import fr.gallonemilien.network.BlockPosPayload
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import software.bernie.geckolib.animatable.GeoAnimatable
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.Animation
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.AnimationState
import software.bernie.geckolib.animation.PlayState
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.RenderUtil
import fr.gallonemilien.block.screen.MoneyPressScreenHandler


class MoneyPressEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(MoneyBlocksEntities.MONEY_PRESS_ENTITY, pos, state), GeoBlockEntity, ExtendedScreenHandlerFactory<BlockPosPayload>, ImplementedInventory {

    var propertyDelegate: PropertyDelegate
    private var progress : Int = 0
    private var maxProgress : Int = 72
    val inventory : DefaultedList<ItemStack> = DefaultedList.ofSize(2, ItemStack.EMPTY)
    private val cache : AnimatableInstanceCache = SingletonAnimatableInstanceCache(this)

    init {
        this.propertyDelegate = object : PropertyDelegate {
            override fun get(index: Int): Int =
                 when (index) {
                    0 -> progress
                    1 -> maxProgress
                    else -> 0
                }


            override fun set(index: Int, value: Int) {
                when (index) {
                    0 -> progress = value
                    1 -> maxProgress = value
                }
            }

            override fun size(): Int = 2
        }
    }

    companion object {
        private val INPUT_SLOT : Int = 0
        private val OUTPUT_SLOT : Int = 1
        private val TITLE : Text = Text.translatable("container.$MOD_ID.money_pres")
    }


    override fun registerControllers(p0: AnimatableManager.ControllerRegistrar?) {
        p0?.add(AnimationController(this, "controller", 0, this::predicate))
    }

    private fun <T : GeoAnimatable> predicate(tAnimationState : AnimationState<T>) : PlayState{
        tAnimationState.controller.setAnimation(RawAnimation.begin().then("animation.model.press", Animation.LoopType.LOOP))
        return PlayState.CONTINUE
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache =
        cache

    override fun getTick(blockEntity: Any?): Double =
        RenderUtil.getCurrentTick()

    override fun getItems(): DefaultedList<ItemStack> =
        inventory

    override fun markDirty() {}

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory?, player: PlayerEntity?): ScreenHandler? =
        MoneyPressScreenHandler(syncId, playerInventory!!, this, this.propertyDelegate)

    override fun writeNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.writeNbt(nbt, registryLookup)
        Inventories.writeNbt(nbt, this.inventory, registryLookup)
    }

    override fun readNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.readNbt(nbt, registryLookup)
        Inventories.readNbt(nbt, this.inventory, registryLookup)
        nbt?.putInt("moneypress.progress", this.progress)
    }

    override fun getDisplayName(): Text =
        TITLE

    override fun getScreenOpeningData(p0: ServerPlayerEntity?): BlockPosPayload =
        BlockPosPayload(this.pos)


    fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (world.isClient) {
            return
        }

        if (isOutputSlotEmptyOrReceivable()) {
            if (hasRecipe()) {
                increaseCraftProgress()
                markDirty(world, pos, state)

                if (hasCraftingFinished()) {
                    craftItem()
                    resetProgress()
                }
            } else {
                resetProgress()
            }
        } else {
            resetProgress()
            markDirty(world, pos, state)
        }
    }

    private fun resetProgress() {
        progress = 0
    }

    private fun craftItem() {
        removeStack(INPUT_SLOT, 1)
        val result = ItemStack(PixelPayItems.COPPER_COIN)
        setStack(OUTPUT_SLOT, ItemStack(result.item, getStack(OUTPUT_SLOT).count + result.count))
    }

    private fun hasCraftingFinished(): Boolean {
        return progress >= maxProgress
    }

    private fun increaseCraftProgress() {
        progress++
    }

    private fun hasRecipe(): Boolean {
        val result = ItemStack(PixelPayItems.COPPER_COIN)
        val hasInput = getStack(INPUT_SLOT).item == Items.COPPER_INGOT
        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.item)
    }

    private fun canInsertItemIntoOutputSlot(item: Item): Boolean {
        return getStack(OUTPUT_SLOT).item == item || getStack(OUTPUT_SLOT).isEmpty
    }

    private fun canInsertAmountIntoOutputSlot(result: ItemStack): Boolean {
        return getStack(OUTPUT_SLOT).count + result.count <= getStack(OUTPUT_SLOT).maxCount
    }

    private fun isOutputSlotEmptyOrReceivable(): Boolean {
        return getStack(OUTPUT_SLOT).isEmpty || getStack(OUTPUT_SLOT).count < getStack(OUTPUT_SLOT).maxCount
    }


}