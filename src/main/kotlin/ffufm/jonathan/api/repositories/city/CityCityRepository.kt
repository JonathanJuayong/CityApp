package ffufm.jonathan.api.repositories.city

import de.ffuf.pass.common.repositories.PassRepository
import ffufm.jonathan.api.spec.dbo.city.CityCity
import kotlin.Long
import org.springframework.stereotype.Repository

@Repository
interface CityCityRepository : PassRepository<CityCity, Long>
