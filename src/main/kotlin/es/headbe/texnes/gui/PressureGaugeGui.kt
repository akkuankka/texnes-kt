package es.headbe.texnes.gui

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WLabel

class PressureGaugeGui : LightweightGuiDescription() {
    init {
        var root = WGridPanel(1)
        setRootPanel(root)
        root.setSize(300, 200)

        val label = WLabel("Pressure is over 9000")
        root.add(label, 1, 1)

        root.validate(this)
    }

}