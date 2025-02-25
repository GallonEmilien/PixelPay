package fr.gallonemilien.item

import fr.gallonemilien.PixelPay
import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.item.custom.MoneyPressItem
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class MoneyPressItemModel : GeoModel<MoneyPressItem>() {

    override fun getModelResource(p0: MoneyPressItem?): Identifier =
        PixelPay.id( "geo/moneypress.geo.json")

    override fun getTextureResource(p0: MoneyPressItem?): Identifier =
        PixelPay.id("textures/block/moneypress.png")

    override fun getAnimationResource(p0: MoneyPressItem?): Identifier =
        PixelPay.id( "animations/moneypress.animation.json")

}