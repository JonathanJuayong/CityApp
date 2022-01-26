package ffufm.jonathan.api

import de.ffuf.pass.common.PassBase
import de.ffuf.pass.common.security.PassApplication
import de.ffuf.pass.common.security.PassDataConfiguration
import de.ffuf.pass.common.utilities.LoggerDelegate
import ffufm.jonathan.api.spec.SBJonathanSpec
import javax.annotation.PostConstruct
import kotlin.Array
import kotlin.String
import kotlin.Suppress
import org.slf4j.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@PassApplication
@PassDataConfiguration(basePackageClasses = [SBJonathan::class, SBJonathanSpec::class])
@SpringBootApplication(scanBasePackageClasses = [SBJonathan::class, SBJonathanSpec::class,
        PassBase::class])
@ConfigurationPropertiesScan
class SBJonathan {
    val logger: Logger by LoggerDelegate()

    @PostConstruct
    fun afterConstruct() {
        logger.info("""Constructing complete""")
    }
}

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<SBJonathan>(*args)
}
