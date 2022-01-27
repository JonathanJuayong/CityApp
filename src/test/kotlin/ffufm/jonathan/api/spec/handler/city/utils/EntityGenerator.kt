package ffufm.jonathan.api.spec.handler.city.utils

import ffufm.jonathan.api.spec.dbo.city.CityCity

object EntityGenerator {
    fun createCity(): CityCity = CityCity(
        name = "Manila",
        country = "Philippines",
        temperature = "+29 °C"
    )
}