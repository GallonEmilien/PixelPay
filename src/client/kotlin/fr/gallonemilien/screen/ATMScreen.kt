package fr.gallonemilien.screen

import com.mojang.blaze3d.systems.RenderSystem
import fr.gallonemilien.PixelPay
import fr.gallonemilien.ServerNetwork
import fr.gallonemilien.block.screen.ATMScreenHandler
import fr.gallonemilien.item.coin.CoinType
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.render.GameRenderer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.item.ItemStack
import net.minecraft.client.MinecraftClient

class ATMScreen(
    handler: ATMScreenHandler,
    inventory: PlayerInventory,
    title: Text
) : HandledScreen<ATMScreenHandler>(handler, inventory, title) {

    companion object {
        private val TEXTURE = PixelPay.id("textures/gui/atm_gui.png")
    }

    // Liste des boutons pour chaque pièce
    private val coinButtons = mutableListOf<ButtonWidget>()
    private val coinPositions = mutableMapOf<CoinType, CoinPosition>()  // HashMap pour stocker les positions des pièces

    override fun init() {
        super.init()
        titleY = 1000
        playerInventoryTitleY = 1000

        val depositButton =
            ButtonWidget.builder(
                Text.translatable("screen.pixelpay.depositButton")) { deposit() }
                .dimensions(
                    x + 56, //+ 39,  // X position relative au GUI
                    y + 13,//+ 13, // Y position relative au GUI
                    90, 16
                )
                .build()


        CoinType.entries.forEachIndexed { index, coinType ->
            coinPositions[coinType] = CoinPosition(coinType)
            coinPositions[coinType]!!.recalculatePosition(x,y, backgroundWidth, index)
            val coinSize = 16
            val button = ButtonWidget.builder(Text.of("")) {
                depositCoin()
            }
            .dimensions(coinPositions[coinType]!!.posX, coinPositions[coinType]!!.posY, coinSize, coinSize)
            .build()

            addDrawableChild(button)
            coinButtons.add(button)

            // Ajouter chaque position de coin dans la HashMap
            coinPositions[coinType] = CoinPosition(coinType)
        }

        addDrawableChild(depositButton)
        recalculateIconsPositions()
    }

    private fun depositCoin() {

    }

    private fun deposit() {
        println("Dépot effectué !")
        ServerNetwork.NET_CHANNEL.clientHandle().send(ServerNetwork.DepositCoinPacket(handler.blockEntity.pos))
    }

    private fun withdraw() {
        println("Retrait effectué !")
    }

    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, TEXTURE)

        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    private fun recalculateIconsPositions() {
        CoinType.entries.forEachIndexed { index, coinType ->
            coinPositions[coinType]?.recalculatePosition(x,y,backgroundWidth, index)
        }
    }

    override fun resize(client: MinecraftClient, width: Int, height: Int) {
        super.resize(client, width, height)
        recalculateIconsPositions()
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        // Dessiner les icônes des coins sur les boutons
        coinButtons.forEachIndexed { index, button ->
            // Vérifier si l'index est valide
            if (CoinType.entries.size > index) {
                val coinType = CoinType.entries[index]
                // Récupérer la position du coin dans la HashMap
                val coinPosition = coinPositions[coinType] ?: return@forEachIndexed

                // Appeler la méthode render de CoinPosition pour dessiner l'icône
                coinPosition.render(context)
            }
        }
    }
}

// Classe CoinPosition pour gérer la position et le rendu de chaque pièce
class CoinPosition(val coinType: CoinType) {
    var posX: Int = 0
    var posY: Int = 0

    // Méthode pour recalculer la position du coin
    fun recalculatePosition(x: Int, y: Int, backgroundWidth: Int, index: Int) {
        val nbCoins = CoinType.entries.size
        val saveWidth = 20
        val coinWidth = 20
        val margin = 16
        val totalWidth = nbCoins * coinWidth + (nbCoins - 1) * margin
        val startX = x + saveWidth
        val endX = x - saveWidth
        val availableWidth = endX - startX - totalWidth
        val offsetX = availableWidth / 2
        posX = startX + offsetX + index * (coinWidth + margin) + (backgroundWidth / 2)
        posY = y + 40
    }

    fun render(context: DrawContext) {
        context.drawItem(ItemStack(coinType.coin), posX, posY)
    }
}
