package fr.gallonemilien.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.sound.SoundEvents
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



class MoneyPressEntity(pos: BlockPos, state: BlockState) : BlockEntity(MoneyBlocksEntities.MONEY_PRESS_ENTITY, pos, state), GeoBlockEntity {

    private val cache : AnimatableInstanceCache = SingletonAnimatableInstanceCache(this)

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
}