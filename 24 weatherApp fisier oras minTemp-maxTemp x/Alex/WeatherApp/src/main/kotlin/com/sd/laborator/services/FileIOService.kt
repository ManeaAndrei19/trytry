package com.sd.laborator.services

import com.sd.laborator.interfaces.FileIOInterface
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileIOService:FileIOInterface {
    override fun write(data: String) {
        var pathname=data.split("~")
        if(pathname.count()==2){
            val fileDescriptor= File("${pathname[0]}.txt")
            fileDescriptor.writeText(pathname[1])
        }
    }
}