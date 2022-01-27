package ffufm.jonathan.api.spec.handler.city.integration

import com.fasterxml.jackson.databind.ObjectMapper
import ffufm.jonathan.api.PassTestBase
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import ffufm.jonathan.api.spec.handler.city.utils.EntityGenerator

class CityCityHandlerTest : PassTestBase() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @WithMockUser
    fun `test create city should return 200`() {
        val body = EntityGenerator.createCity()

        mockMvc.post("/cities/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.andExpect {
            status { isOk() }

        }
    }

    @Test
    @WithMockUser
    fun `create city should return 409 if duplicate city`() {
        cityCityRepository.save(EntityGenerator.createCity())
        val body = EntityGenerator.createCity()

        mockMvc.post("/cities/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.asyncDispatch().andExpect {
            status { isConflict() }
        }
    }
    @Test
    @WithMockUser
    fun `test getAll the cities`() {
        val user = cityCityRepository.save(EntityGenerator.createCity())

        mockMvc.get("/cities/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON

        }.andExpect {
            status { isOk() }

        }
    }

    @Test
    @WithMockUser
    fun `test remove should return 200`() {

        val savedUser = cityCityRepository.save(EntityGenerator.createCity())

        mockMvc.delete("/cities/{id}/", savedUser.id!!) {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }

        }
    }
    @Test
    @WithMockUser
    fun `test remove should failed if the id is not found`() {

         cityCityRepository.save(EntityGenerator.createCity())
        val id: Long = 143

        mockMvc.delete("/cities/${id}/") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
        }.asyncDispatch().andExpect {
            status { isNotFound() }

        }
    }
    @Test
    @WithMockUser
    fun `test update should return 200`() {
        val savedCity = cityCityRepository.save(EntityGenerator.createCity())
        val body = savedCity.copy(
            name = "Tokyo",
            country = "Japan"
        )
        mockMvc.put("/cities/{id}/", savedCity.id) {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.andExpect {
            status { isOk() }

        }
    }

    @Test
    @WithMockUser
    fun `test update should return 400 if the id is not exist`() {
        val savedCity = cityCityRepository.save(EntityGenerator.createCity())
        val body = savedCity.copy(
            name = "Tokyo",
            country = "Japan"
        )
        mockMvc.put("/cities/{id}/", 3) {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.andExpect {
            status { isOk() }

        }
    }
}
