package es.headbe.texnes.gui

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel

class PressureGaugeGui : LightweightGuiDescription() {
    init {
        val root = WGridPanel()
        setRootPanel(root)
        root.setSize(300, 200)
    }

}