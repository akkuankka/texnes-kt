package es.headbe.texnes.registry

import es.headbe.texnes.gui.ChemistsCabinetGui

object MiscRegistry {
    fun registerMisc() {
        ChemistsCabinetGui.registerGui()
    }

    fun registerClientScreens() {
        ChemistsCabinetGui.registerClient()
    }

}