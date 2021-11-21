package es.headbe.texnes

import es.headbe.texnes.gui.PressureGaugeGui
import es.headbe.texnes.gui.PressureGaugeScreen
import es.headbe.texnes.registry.MiscRegistry
import es.headbe.texnes.util.networking.NetworkingConstants
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

@Suppress("unused")
class TexnesClient {
    fun init() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkingConstants.PRESSURE_GAUGE_PKID)
        {client, handler, buf, responseSender ->
            client.execute {
                client.setScreen(PressureGaugeScreen(PressureGaugeGui()))
            }
        }

        MiscRegistry.registerClientScreens()
    }
}