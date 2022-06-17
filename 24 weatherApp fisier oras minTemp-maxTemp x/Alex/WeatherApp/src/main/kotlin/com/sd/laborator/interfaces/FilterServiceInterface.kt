package com.sd.laborator.interfaces

import com.sd.laborator.pojo.WeatherForecastData

interface FilterServiceInterface {
    fun filterData(data: List<WeatherForecastData>,filters:List<FilterInterface>): List<WeatherForecastData>
}