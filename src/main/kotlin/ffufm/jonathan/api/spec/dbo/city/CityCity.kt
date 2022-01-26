package ffufm.jonathan.api.spec.dbo.city

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint
import am.ik.yavi.builder.konstraintOnObject
import de.ffuf.pass.common.models.PassDTO
import de.ffuf.pass.common.models.PassDTOModel
import de.ffuf.pass.common.models.PassDtoSerializer
import de.ffuf.pass.common.models.PassModelValidation
import de.ffuf.pass.common.models.idDto
import de.ffuf.pass.common.security.SpringContext
import de.ffuf.pass.common.utilities.extensions.konstraint
import de.ffuf.pass.common.utilities.extensions.toEntities
import de.ffuf.pass.common.utilities.extensions.toSafeDtos
import java.util.TreeSet
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Lob
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import kotlin.Long
import kotlin.String
import kotlin.reflect.KClass
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.FetchMode
import org.springframework.beans.factory.getBeansOfType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Model for city
 */
@Entity(name = "CityCity")
@Table(name = "city_city")
data class CityCity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    /**
     * Name of the city
     * Sample: Manila
     */
    @Column(
        nullable = false,
        updatable = true,
        name = "name"
    )
    @Lob
    val name: String = "",
    /**
     * Country where the city is located
     * Sample: Philippines
     */
    @Column(
        nullable = false,
        updatable = true,
        name = "country"
    )
    @Lob
    val country: String = "",
    /**
     * temperature for the city
     * Sample: 30 C
     */
    @Column(
        nullable = false,
        updatable = true,
        name = "temperature"
    )
    @Lob
    val temperature: String = ""
) : PassDTOModel<CityCity, CityCityDTO, Long>() {
    override fun toDto(): CityCityDTO = super.toDtoInternal(CityCitySerializer::class as
            KClass<PassDtoSerializer<PassDTOModel<CityCity, CityCityDTO, Long>, CityCityDTO, Long>>)

    override fun readId(): Long? = this.id

    override fun toString(): String = super.toString()
}

/**
 * Model for city
 */
data class CityCityDTO(
    val id: Long? = null,
    /**
     * Name of the city
     * Sample: Manila
     */
    val name: String? = "",
    /**
     * Country where the city is located
     * Sample: Philippines
     */
    val country: String? = "",
    /**
     * temperature for the city
     * Sample: 30 C
     */
    val temperature: String? = ""
) : PassDTO<CityCity, Long>() {
    override fun toEntity(): CityCity = super.toEntityInternal(CityCitySerializer::class as
            KClass<PassDtoSerializer<PassDTOModel<CityCity, PassDTO<CityCity, Long>, Long>,
            PassDTO<CityCity, Long>, Long>>)

    override fun readId(): Long? = this.id
}

@Component
class CityCitySerializer : PassDtoSerializer<CityCity, CityCityDTO, Long>() {
    override fun toDto(entity: CityCity): CityCityDTO = cycle(entity) {
        CityCityDTO(
                id = entity.id,
        name = entity.name,
        country = entity.country,
        temperature = entity.temperature,

                )}

    override fun toEntity(dto: CityCityDTO): CityCity = CityCity(
            id = dto.id,
    name = dto.name ?: "",
    country = dto.country ?: "",
    temperature = dto.temperature ?: "",

            )
    override fun idDto(id: Long): CityCityDTO = CityCityDTO(
            id = id,
    name = null,
    country = null,
    temperature = null,

            )}

@Service("city.CityCityValidator")
class CityCityValidator : PassModelValidation<CityCity> {
    override fun buildValidator(validatorBuilder: ValidatorBuilder<CityCity>):
            ValidatorBuilder<CityCity> = validatorBuilder.apply {
    }
}

@Service("city.CityCityDTOValidator")
class CityCityDTOValidator : PassModelValidation<CityCityDTO> {
    override fun buildValidator(validatorBuilder: ValidatorBuilder<CityCityDTO>):
            ValidatorBuilder<CityCityDTO> = validatorBuilder.apply {
    }
}
