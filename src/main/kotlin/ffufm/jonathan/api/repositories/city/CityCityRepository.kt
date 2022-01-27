package ffufm.jonathan.api.repositories.city

import de.ffuf.pass.common.repositories.PassRepository
import ffufm.jonathan.api.spec.dbo.city.CityCity
import org.springframework.data.jpa.repository.Query
import kotlin.Long
import org.springframework.stereotype.Repository

@Repository
interface CityCityRepository : PassRepository<CityCity, Long> {
    @Query(
        """
            SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
            FROM CityCity c WHERE c.name = :name
        """
    )
    fun isCityDuplicated(name: String): Boolean
}
