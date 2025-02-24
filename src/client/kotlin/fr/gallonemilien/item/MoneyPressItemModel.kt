package fr.gallonemilien.item

import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.item.custom.MoneyPressItem
import net.minecraft.util.Identifier
import software.bernie.geckolib.model.GeoModel

class MoneyPressItemModel : GeoModel<MoneyPressItem>() {

    override fun getModelResource(p0: MoneyPressItem?): Identifier =
        Identifier.of(MOD_ID, "geo/moneypress.geo.json")

    override fun getTextureResource(p0: MoneyPressItem?): Identifier =
        Identifier.of(MOD_ID, "textures/block/moneypress.png")

    override fun getAnimationResource(p0: MoneyPressItem?): Identifier =
        Identifier.of(MOD_ID, "animations/moneypress.animation.json")

}