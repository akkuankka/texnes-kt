package es.headbe.texnes.gui

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text

class ChemistsCabinetScreen(gui: ChemistsCabinetGui, player: PlayerEntity, title: Text) : CottonInventoryScreen<ChemistsCabinetGui>(
    gui, player, title
) {
}