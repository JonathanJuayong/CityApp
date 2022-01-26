package ffufm.jonathan.api.handlerimpl.city

import de.ffuf.pass.common.handlers.PassDatabaseHandler
import de.ffuf.pass.common.utilities.extensions.orElseThrow404
import ffufm.jonathan.api.repositories.city.CityCityRepository
import ffufm.jonathan.api.spec.dbo.city.CityCity
import ffufm.jonathan.api.spec.dbo.city.CityCityDTO
import ffufm.jonathan.api.spec.handler.city.CityCityDatabaseHandler
import kotlin.Int
import kotlin.Long
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("city.CityCityHandler")
class CityCityHandlerImpl : PassDatabaseHandler<CityCity, CityCityRepository>(),
        CityCityDatabaseHandler {
    /**
     * Create City: Creates a new City object
     * HTTP Code 201: The created City
     */
    override suspend fun create(body: CityCityDTO): CityCityDTO {
        TODO("not checked yet")
        return repository.save(body)
    }

    /**
     * Get all Cities by page: Returns all Cities from the system that the user has access to. The
     * Headers will include TotalElements, TotalPages, CurrentPage and PerPage to help with Pagination.
     * HTTP Code 200: List of Cities
     */
    override suspend fun getAll(maxResults: Int = 100, page: Int = 0): Page<CityCityDTO> {
        TODO("not checked yet")
        return repository.findAll(Pageable.unpaged())
    }

    /**
     * Delete City by id.: Deletes one specific City.
     */
    override suspend fun remove(id: Long) {
        val original = repository.findById(id).orElseThrow404(id)
        TODO("not checked yet - update the values you really want updated")
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
        TODO("not checked yet - update the values you really want updated")
        return repository.save(original)
    }
}
