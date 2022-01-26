package ffufm.jonathan.api.handlerimpl.city

import com.fasterxml.jackson.databind.ObjectMapper
import de.ffuf.pass.common.handlers.PassDatabaseHandler
import de.ffuf.pass.common.utilities.extensions.orElseThrow404
import de.ffuf.pass.common.utilities.extensions.toDtos
import ffufm.jonathan.api.external.WeatherService
import ffufm.jonathan.api.repositories.city.CityCityRepository
import ffufm.jonathan.api.spec.dbo.city.CityCity
import ffufm.jonathan.api.spec.dbo.city.CityCityDTO
import ffufm.jonathan.api.spec.handler.city.CityCityDatabaseHandler
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component("city.CityCityHandler")
class CityCityHandlerImpl(
    private val weatherService: WeatherService,
    private val objectMapper: ObjectMapper
) : PassDatabaseHandler<CityCity, CityCityRepository>(),
    CityCityDatabaseHandler {
    /**
     * Create City: Creates a new City object
     * HTTP Code 201: The created City
     */
    override suspend fun create(body: CityCityDTO): CityCityDTO {
        val weather = weatherService.getWeather(body.name ?: "")

        val weatherJsonString = objectMapper.writeValueAsString(weather)
        val jsonNode = objectMapper.readTree(weatherJsonString)
        val forecasts = jsonNode.get("forecast").asIterable()
        val day1Temperature = forecasts.first().get("temperature").asText()

        return repository.save(
            body.copy(
                temperature = day1Temperature
            ).toEntity()
        ).toDto()
    }

    /**
     * Get all Cities by page: Returns all Cities from the system that the user has access to. The
     * Headers will include TotalElements, TotalPages, CurrentPage and PerPage to help with Pagination.
     * HTTP Code 200: List of Cities
     */
    override suspend fun getAll(maxResults: Int, page: Int): Page<CityCityDTO> {
        val pagination = PageRequest.of(page, maxResults)
        return repository.findAll(pagination).toDtos()
    }

    /**
     * Delete City by id.: Deletes one specific City.
     */
    override suspend fun remove(id: Long) {
        val original = repository.findById(id).orElseThrow404(id)
        return repository.delete(original)
    }

    /**
     * Update the City: Updates an existing City
     * HTTP Code 200: The updated model
     * HTTP Code 404: The requested object could not be found by the submitted id.
     * HTTP Code 422: On or many fields contains a invalid value.
     */
    override suspend fun update(body: CityCityDTO, id: Long): CityCityDTO {
        val original = repository.findById(id).orElseThrow404(id)
        return repository.save(
            original.copy(
                name = body.name ?: original.name,
                country = body.country ?: original.country,
                temperature = body.temperature ?: original.temperature
            )
        ).toDto()
    }
}
