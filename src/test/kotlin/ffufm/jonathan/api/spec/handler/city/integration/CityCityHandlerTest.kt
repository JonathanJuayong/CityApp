package ffufm.jonathan.api.spec.handler.city.integration

import com.fasterxml.jackson.databind.ObjectMapper
import ffufm.jonathan.api.PassTestBase
import ffufm.jonathan.api.repositories.city.CityCityRepository
import ffufm.jonathan.api.spec.dbo.city.CityCity
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class CityCityHandlerTest : PassTestBase() {
    @Autowired
    private lateinit var cityCityRepository: CityCityRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Before
    @After
    fun cleanRepositories() {
        cityCityRepository.deleteAll()
    }

    @Test
    @WithMockUser
    fun `test create`() {
        val body: CityCity = CityCity()
                mockMvc.post("/cities/") {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(body)
                }.andExpect {
                    status { isOk }
                    
                }
    }

    @Test
    @WithMockUser
    fun `test getAll`() {
                mockMvc.get("/cities/") {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    
                }.andExpect {
                    status { isOk }
                    
                }
    }

    @Test
    @WithMockUser
    fun `test remove`() {
        val id: Long = 0
                mockMvc.delete("/cities/{id}/", id) {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    
                }.andExpect {
                    status { isOk }
                    
                }
    }

    @Test
    @WithMockUser
    fun `test update`() {
        val body: CityCity = CityCity()
        val id: Long = 0
                mockMvc.put("/cities/{id}/", id) {
                    accept(MediaType.APPLICATION_JSON)
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(body)
                }.andExpect {
                    status { isOk }
                    
                }
    }
}
