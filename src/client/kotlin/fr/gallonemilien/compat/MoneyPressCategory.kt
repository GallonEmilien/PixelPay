package fr.gallonemilien.compat

import fr.gallonemilien.PixelPay
import fr.gallonemilien.PixelPay.Companion.MOD_ID
import fr.gallonemilien.block.MoneyBlocks
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

class MoneyPressCategory : DisplayCategory<BasicDisplay> {
    companion object {
        val TEXTURE : Identifier = PixelPay.id("textures/gui/moneypress_gui.png")
        val MONEY_PRESS : CategoryIdentifier<MoneyPressDisplay> =
            CategoryIdentifier.of(PixelPay.MOD_ID,"moneypress")
    }

    override fun getCategoryIdentifier(): CategoryIdentifier<out BasicDisplay> =
        MONEY_PRESS

    override fun getTitle(): Text =
        Text.translatable("container.$MOD_ID.money_press")

    override fun getIcon(): Renderer =
        EntryStacks.of(MoneyBlocks.MONEY_PRESS.asItem().defaultStack)

    override fun setupDisplay(display: BasicDisplay?, bounds: Rectangle?): MutableList<Widget> {
        val startPoint = Point(bounds!!.centerX-87, bounds.centerY-35)
        val widgets = mutableListOf<Widget>()
        widgets.add(Widgets.createTexturedWidget(TEXTURE, Rectangle(startPoint.x, startPoint.y, 175, 82)))

        widgets.add(Widgets.createSlot(Point(startPoint.x+57, startPoint.y+11)).entries(display!!.inputEntries[0]))
        widgets.add(Widgets.createSlot(Point(startPoint.x+80, startPoint.y+11)).entries(display.inputEntries[1]))
        widgets.add(Widgets.createSlot(Point(startPoint.x+103, startPoint.y+11)).entries(display.inputEntries[2]))
        widgets.add(Widgets.createSlot(Point(startPoint.x+80, startPoint.y+59)).markOutput().entries(display.outputEntries[0]))
        return widgets
    }

    override fun getDisplayHeight(): Int = 90

}