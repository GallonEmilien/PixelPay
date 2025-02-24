package fr.gallonemilien.block

import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.block.entity.MoneyPressEntity
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class MoneyPressModel : GeoModel<MoneyPressEntity>() {

    override fun getModelResource(p0: MoneyPressEntity?): Identifier =
        Identifier.of(MOD_ID, "geo/moneypress.geo.json")

    override fun getTextureResource(p0: MoneyPressEntity?): Identifier =
        Identifier.of(MOD_ID, "textures/block/moneypress.png")

    override fun getAnimationResource(p0: MoneyPressEntity?): Identifier =
        Identifier.of(MOD_ID, "animations/moneypress.animation.json")

}