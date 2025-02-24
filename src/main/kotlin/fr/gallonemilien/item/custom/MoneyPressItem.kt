package fr.gallonemilien.item.custom

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import software.bernie.geckolib.animatable.GeoAnimatable
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.SingletonGeoAnimatable
import software.bernie.geckolib.animatable.client.GeoRenderProvider
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache
import software.bernie.geckolib.animation.*
import software.bernie.geckolib.util.RenderUtil
import java.util.function.Consumer

class MoneyPressItem(block: Block, settings: Settings) : BlockItem(block, Settings()), GeoItem {
    private val cache : AnimatableInstanceCache = SingletonAnimatableInstanceCache(this)

    init {
        SingletonGeoAnimatable.registerSyncedAnimatable(this)

    }

    override fun createGeoRenderer(consumer: Consumer<GeoRenderProvider>?) {
       /* consumer?.accept(object : GeoRenderProvider {
            private lateinit var renderer : MoneyPressItemRenderer

            override fun getGeoItemRenderer(): net.minecraft.client.render.item.BuiltinModelItemRenderer {
                if(this.renderer == null)
                    this.renderer = MoneyPressItemRenderer()
                return this.renderer
            }
        })*/
    }


    override fun registerControllers(p0: AnimatableManager.ControllerRegistrar?) {
        p0?.add(AnimationController(this, "controller", 0, this::predicate))
    }

    private fun <T : GeoAnimatable> predicate(tAnimationState : AnimationState<T>) : PlayState {
        tAnimationState.controller.setAnimation(RawAnimation.begin().then("press", Animation.LoopType.LOOP))
        return PlayState.CONTINUE
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache =
        cache

    override fun getTick(itemStack: Any?): Double =
        RenderUtil.getCurrentTick()

}