package com.sd.laborator.pojo

import com.sd.laborator.interfaces.FilterInterface

class FilterMaxTemp:FilterInterface {
    override fun execute(data: WeatherForecastData): Boolean {
        if (data.maxTemp<20){
            return true
        }
        return false
    }
}