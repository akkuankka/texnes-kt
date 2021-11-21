package es.headbe.texnes.gui

import es.headbe.texnes.registry.blocks.ChemistsCabinet
import es.headbe.texnes.util.ident
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType

class ChemistsCabinetGui(id: Int, playerInventory: PlayerInventory, context: ScreenHandlerContext) :
    SyncedGuiDescription(
        type,
        id,
        playerInventory,
        getBlockInventory(context, ChemistsCabinet.Entity.NUM_SLOTS), getBlockPropertyDelegate(context))
{
    init {
        val root = WGridPanel()
        setRootPanel(root)
        root.setSize(200, 200)
        root.insets = Insets.ROOT_PANEL
        val firstRow = WItemSlot.of(blockInventory, 0, 9, 1)
        val secondRow = WItemSlot.of(blockInventory, 9, 9, 1)
        root.add(firstRow, 0, 1)
        root.add(secondRow, 0, 3)
        root.add(this.createPlayerInventoryPanel(), 0, 5)
        root.validate(this)
    }

        companion object {
             lateinit var type: ScreenHandlerType<ChemistsCabinetGui>

             fun registerGui() {
                 type = ScreenHandlerRegistry.registerSimple(ident("chemists_cabinet")) {i, inv ->
                     ChemistsCabinetGui(i, inv, ScreenHandlerContext.EMPTY)
                 }
             }

            fun registerClient() {
                ScreenRegistry.register(type) { gui, inv, title ->
                    ChemistsCabinetScreen(gui, inv.player, title)
                }
            }
        }
}