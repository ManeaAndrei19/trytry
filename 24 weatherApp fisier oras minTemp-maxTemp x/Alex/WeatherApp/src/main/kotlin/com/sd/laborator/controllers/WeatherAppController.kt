package com.sd.laborator.controllers

import com.sd.laborator.interfaces.FileIOInterface
import com.sd.laborator.interfaces.FilterServiceInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.FilterMaxTemp
import com.sd.laborator.pojo.WeatherForecastData
import com.sd.laborator.services.FileIOService
import com.sd.laborator.services.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class WeatherAppController {
    @Autowired
    private lateinit var locationSearchService: LocationSearchInterface

    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var fileIOService: FileIOInterface

    @Autowired
    private lateinit var filterService:FilterServiceInterface

    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String): String {
        // se incearca preluarea WOEID-ului locaţiei primite in URL
        val locationId = locationSearchService.getLocationId(location)

        // dacă locaţia nu a fost găsită, răspunsul va fi corespunzător
        if (locationId == -1) {
            return "Nu s-au putut gasi date meteo pentru cuvintele cheie \"$location\"!"
        }

        // pe baza ID-ului de locaţie, se interoghează al doilea serviciu care returnează datele meteo
        // încapsulate într-un obiect POJO
        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(locationId)
        return rawForecastData.toString()
    }

    @RequestMapping("/getBucharestFile", method = [RequestMethod.GET])
    @ResponseBody
    fun getBucharestWeather(): String {
        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(868274)
        val filteredForecastData = filterService.filterData(listOf(rawForecastData), listOf(FilterMaxTemp()))
        fileIOService.write(filteredForecastData.get(0).location+"~"+filteredForecastData.get(0).location+" "+filteredForecastData.get(0).minTemp+" "+filteredForecastData.get(0).maxTemp)
        return "File wrote sucessfuly"
    }
}