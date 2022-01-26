package ffufm.jonathan.api.external

import ffufm.jonathan.api.utils.Constants
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class WeatherService {
    fun initializeApiClient(url: String) =
        WebClient.builder()
            .baseUrl(url)
            .filter { request, next -> next.exchange(request)}
            .build()


    suspend fun getWeather(cityName: String): String {
        val client = initializeApiClient(Constants.WEATHER_URL)
        return client.get()
            .uri { builder ->
                builder
                    .apply {
                        path("/$cityName")
                    }
                    .build()
            }
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .awaitBody()
    }


}