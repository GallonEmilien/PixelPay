package fr.gallonemilien.screen

import com.mojang.blaze3d.systems.RenderSystem
import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.block.screen.MoneyPressScreenHandler
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class MoneyPressScreen(
    handler: MoneyPressScreenHandler,
    inventory: PlayerInventory,
    title: Text
) : HandledScreen<MoneyPressScreenHandler>(handler, inventory, title) {

    companion object {
        private val TEXTURE = Identifier.of(MOD_ID, "textures/gui/moneypress_gui.png")
    }

    override fun init() {
        super.init()
        titleY = 1000
        playerInventoryTitleY = 1000
    }

    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, TEXTURE)

        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight)

        renderProgressArrow(context, x, y)
    }

    private fun renderProgressArrow(context: DrawContext, x: Int, y: Int) {
        if (handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 85, y + 30, 176, 0, 8, handler.getScaledProgress())
        }
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(context, mouseX, mouseY, delta)
        super.render(context, mouseX, mouseY, delta)
        drawMouseoverTooltip(context, mouseX, mouseY)
    }
}
