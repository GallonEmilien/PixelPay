package fr.gallonemilien.block.entity.moneypress

import fr.gallonemilien.NetworkContent
import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.block.entity.ImplementedInventory
import fr.gallonemilien.block.entity.MoneyBlocksEntities
import fr.gallonemilien.block.screen.MoneyPressScreenHandler
import fr.gallonemilien.item.PixelPayItems
import fr.gallonemilien.network.BlockPosPayload
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
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
import software.bernie.geckolib.animation.*
import software.bernie.geckolib.util.RenderUtil


class MoneyPressEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(MoneyBlocksEntities.MONEY_PRESS_ENTITY, pos, state),
    GeoBlockEntity, ExtendedScreenHandlerFactory<BlockPosPayload> {


    companion object {
        val WORKING: RawAnimation = RawAnimation.begin().thenLoop("animation.model.press")
        private val TITLE: Text = Text.translatable("container.$MOD_ID.money_press")
    }

    var networkDirty : Boolean = false
    var isWorking : Boolean = false
    private var progress = 0
    private var maxProgress = 15
    private val waitForReset = 45
    val moneyPressInventory = MoneyPressInventory(DefaultedList.ofSize(4, ItemStack.EMPTY), this)
    private val cache: AnimatableInstanceCache = SingletonAnimatableInstanceCache(this)

    fun resetProgress() {
        this.progress = 0
    }


    private val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
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

        override fun size() = 2
    }

    override fun registerControllers(p0: AnimatableManager.ControllerRegistrar?) {
        p0?.add(AnimationController(this, "controller", 0, this::predicate))
    }

    private fun <T : GeoAnimatable> predicate(tAnimationState : AnimationState<T>) : PlayState{
        if(isWorking) {
            tAnimationState.controller.setAnimation(
                RawAnimation.begin().then("animation.model.press", Animation.LoopType.LOOP)
            )
            return PlayState.CONTINUE
        }
        return PlayState.STOP
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache

    override fun getTick(blockEntity: Any?): Double = RenderUtil.getCurrentTick()


    override fun createMenu(syncId: Int, playerInventory: PlayerInventory?, player: PlayerEntity?): ScreenHandler? {
        return MoneyPressScreenHandler(syncId, playerInventory!!, this, this.propertyDelegate)
    }

    override fun writeNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.writeNbt(nbt, registryLookup)
        Inventories.writeNbt(nbt, moneyPressInventory.inventory,false, registryLookup)
        nbt?.putInt("moneypress.progress", progress)
    }

    override fun readNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?) {
        Inventories.readNbt(nbt, moneyPressInventory.inventory, registryLookup)
        super.readNbt(nbt, registryLookup)
        progress = nbt?.getInt("moneypress.progress") ?: 0
    }

    override fun getDisplayName(): Text = TITLE

    override fun getScreenOpeningData(player: ServerPlayerEntity?): BlockPosPayload = BlockPosPayload(pos)


    private fun markNetworkDirty() {
        this.networkDirty = true
    }

    override fun markDirty() {
        if(this.world != null)
            this.world!!.markDirty(pos)
        markNetworkDirty()
    }

    fun tick(world: World, pos: BlockPos, state: BlockState, entity: MoneyPressEntity) {
        if (world.isClient) return

        if(moneyPressInventory.getRecipe() != null) {
            entity.progress++
            if(!isWorking) {
                isWorking = true
                markDirty()
            }
            markDirty(world, pos, state)
            world.updateListeners(pos, state, state, 3)
            if (progress == maxProgress) {
                world.playSound(null, pos, net.minecraft.sound.SoundEvents.ENTITY_ITEM_BREAK,
                    net.minecraft.sound.SoundCategory.BLOCKS, 1.0f, 1.0f)
            }

            if(entity.progress >= entity.waitForReset) {
                if(moneyPressInventory.craftItem()) resetProgress()
            }
        } else {
            entity.resetProgress()
            if(isWorking) {
                isWorking = false
                markDirty()
            }
            markDirty(world, pos, state)
            world.updateListeners(pos, state, state, 3)
        }

        if(networkDirty)
            updateNetwork()
    }



    private fun updateNetwork() {
        if(!networkDirty) return
        NetworkContent.NET_CHANNEL.serverHandle(this).send(NetworkContent.MachineSyncPacket(pos,isWorking))
        networkDirty = false
    }

    fun handleNetworkEntry(message: NetworkContent.MachineSyncPacket) {
        this.isWorking = message.isWorking
    }
}
