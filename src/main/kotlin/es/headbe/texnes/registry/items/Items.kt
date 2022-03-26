package es.headbe.texnes.registry.items

import es.headbe.texnes.Texnes
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import es.headbe.texnes.util.*
import net.minecraft.item.Item

object Items {
// normal items
    val bismuth: CrystalsItem = CrystalsItem(
        FabricItemSettings().group(Texnes.ITEM_GROUP)
    )

    val pressureGauge = PressureGauge(
        FabricItemSettings().group(Texnes.ITEM_GROUP)
    )


    fun genericItem(): Item = Item(FabricItemSettings().group(Texnes.ITEM_GROUP))

    private val registerOreLumps = {
        ident("raw_tin").item(genericItem())
        ident("raw_zinc").item(genericItem())
        ident("raw_lead").item(genericItem())
        ident("raw_silver").item(genericItem())
        ident("raw_arsenic").item(genericItem())
    }

    fun registerAll() {
        // item items
        ident("bismuth").item(bismuth)
        ident("pressure_gauge").item(pressureGauge)
        ident("lead_ingot").item(genericItem())
        ident("lead_plate").item(genericItem())

        registerOreLumps()
    }
}

