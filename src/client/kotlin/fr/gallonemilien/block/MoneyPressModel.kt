package fr.gallonemilien.block

import fr.gallonemilien.PixelPay
import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class MoneyPressModel(): GeoModel<MoneyPressEntity>() {

    override fun getModelResource(p0: MoneyPressEntity?): Identifier =
        PixelPay.id( "geo/moneypress.geo.json")

    override fun getTextureResource(p0: MoneyPressEntity?): Identifier =
        PixelPay.id( "textures/block/moneypress.png")

    override fun getAnimationResource(p0: MoneyPressEntity?): Identifier =
        PixelPay.id( "animations/moneypress.animation.json")

}