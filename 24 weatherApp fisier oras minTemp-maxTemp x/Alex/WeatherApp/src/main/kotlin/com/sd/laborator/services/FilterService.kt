package com.sd.laborator.services

import com.sd.laborator.interfaces.FilterInterface
import com.sd.laborator.interfaces.FilterServiceInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.springframework.stereotype.Service

@Service
class FilterService:FilterServiceInterface {
    override fun filterData(data: List<WeatherForecastData>,filters:List<FilterInterface>): List<WeatherForecastData> {
        var result: MutableList<WeatherForecastData> = mutableListOf()
        for (element in data){
            var ok: Boolean = true
            filters.forEach {
                if(!it.execute(element)){
                    ok=false
                }
            }
            if (ok){
                result.add(element)
            }
        }
        return  result
    }
}