package ffufm.jonathan.api.spec.handler.city.implementation

import ffufm.jonathan.api.PassTestBase
import ffufm.jonathan.api.repositories.city.CityCityRepository
import ffufm.jonathan.api.spec.dbo.city.CityCity
import ffufm.jonathan.api.spec.handler.city.CityCityDatabaseHandler
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class CityCityDatabaseHandlerTest : PassTestBase() {
    @Autowired
    lateinit var cityCityRepository: CityCityRepository

    @Autowired
    lateinit var cityCityDatabaseHandler: CityCityDatabaseHandler

    @Before
    @After
    fun cleanRepositories() {
        cityCityRepository.deleteAll()
    }

    @Test
    fun `test create`() = runBlocking {
        val body: CityCity = CityCity()
        cityCityDatabaseHandler.create(body)
        Unit
    }

    @Test
    fun `test getAll`() = runBlocking {
        val maxResults: Int = 100
        val page: Int = 0
        cityCityDatabaseHandler.getAll(maxResults, page)
        Unit
    }

    @Test
    fun `test remove`() = runBlocking {
        val id: Long = 0
        cityCityDatabaseHandler.remove(id)
        Unit
    }

    @Test
    fun `test update`() = runBlocking {
        val body: CityCity = CityCity()
        val id: Long = 0
        cityCityDatabaseHandler.update(body, id)
        Unit
    }
}
