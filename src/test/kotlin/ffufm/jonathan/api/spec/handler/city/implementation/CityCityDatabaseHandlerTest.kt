package ffufm.jonathan.api.spec.handler.city.implementation

import ffufm.jonathan.api.PassTestBase
import ffufm.jonathan.api.spec.handler.city.CityCityDatabaseHandler
import ffufm.jonathan.api.spec.handler.city.utils.EntityGenerator
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CityCityDatabaseHandlerTest : PassTestBase() {
    @Autowired
    lateinit var cityCityDatabaseHandler: CityCityDatabaseHandler

    @Test
    fun `create should return city`() = runBlocking {
        assertEquals(0, cityCityRepository.findAll().count())
        val city = EntityGenerator.createCity().toDto()
        val createdCity = cityCityDatabaseHandler.create(city)

        assertEquals(1, cityCityRepository.findAll().count())
        assertEquals(city.name, createdCity.name)
        assertEquals(city.country, createdCity.country)
        assertEquals(city.temperature, createdCity.temperature)
    }

    @Test
    fun `create should fail given duplicate city name`() = runBlocking {
        val city = cityCityRepository.save(EntityGenerator.createCity())
        val duplicateCity = city.copy(
            name = "Manila"
        ).toDto()

        val exception = assertFailsWith<ResponseStatusException> {
            cityCityDatabaseHandler.create(duplicateCity)
        }

        val expectedException = "409 CONFLICT \"City Name ${duplicateCity.name} already exists!\""
        assertEquals(expectedException, exception.message)
    }

    @Test
    fun `get all cities should return cities`() = runBlocking {
        val body = EntityGenerator.createCity()
        cityCityRepository.saveAll(
            listOf(
                body,
                body.copy(
                    name = "Manila",
                    country = "Philippines",
                    temperature = "+29 °C"
                )
            )
        )
        val cities = cityCityDatabaseHandler.getAll(100, 0)
        assertEquals(2, cities.totalElements)
    }

    @Test
    fun `remove city should work`() = runBlocking {
        val city = EntityGenerator.createCity()
        val createdCity = cityCityRepository.save(city)

        assertEquals(1, cityCityRepository.findAll().count())
        cityCityDatabaseHandler.remove(createdCity.id!!)
        assertEquals(0, cityCityRepository.findAll().count())
    }

    @Test
    fun `remove city should fail given invalid cityId`() = runBlocking {
        val invalidId : Long = 123
        val exception = assertFailsWith<ResponseStatusException> {
            cityCityDatabaseHandler.remove(invalidId)
        }
        val expectedException = "404 NOT_FOUND \"CityCity with ID $invalidId not found\""
        assertEquals(expectedException, exception.message)
    }

    @Test
    fun `update city should return updated city`() = runBlocking {
        val city = EntityGenerator.createCity()
        val original = cityCityRepository.save(city)

        val body = original.copy(
            name = "Manila",
            country = "Philippines",
            temperature = "30°C"
        ).toDto()

        val updatedCity = cityCityDatabaseHandler.update(body, original.id!!)
        assertEquals(body.name, updatedCity.name)
        assertEquals(body.country, updatedCity.country)
        assertEquals(body.temperature, updatedCity.temperature)
    }

    @Test
    fun `update should fail given invalid cityId`() = runBlocking {
        val original = cityCityRepository.save(EntityGenerator.createCity())
        val body = original.copy(
            name = "Manila",
            country = "Philippines",
            temperature = "30°C"
        )

        val id : Long = 123
        val exception = assertFailsWith<ResponseStatusException> {
            cityCityDatabaseHandler.update(body.toDto(), id)
        }
        val expectedException = "404 NOT_FOUND \"CityCity with ID $id not found\""
        assertEquals(expectedException, exception.message)
    }
}
