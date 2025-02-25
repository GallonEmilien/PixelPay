package fr.gallonemilien.block

import fr.gallonemilien.block.entity.moneypress.MoneyPressEntity
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import software.bernie.geckolib.renderer.GeoBlockRenderer

class MoneyPressRenderer(ctx : BlockEntityRendererFactory.Context) : GeoBlockRenderer<MoneyPressEntity>(MoneyPressModel())