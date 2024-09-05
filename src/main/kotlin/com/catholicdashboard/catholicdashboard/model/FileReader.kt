package com.catholicdashboard.catholicdashboard.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import java.nio.file.Files


//
//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
//
class FileReader {
    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    companion object{
        inline fun <reified T> getFile(fileName: String): T?{
            val mapper = jacksonObjectMapper()
                try{
                    val jsonFile = ClassPathResource(fileName).file
                    val str = Files.readString(jsonFile.toPath())
//                    println("SSSSTTTTRRR: $str")
                    return mapper.readValue(str, T::class.java)
                } catch (e: Exception){
                    println("***************************EXCEPTION***************************")
                    println(e)
                }
//
//            val str = javaClass.classLoader?.getResourceAsStream(fileName).use { stream ->
////                if (stream != null) {
////                    InputStreamReader(stream).use { reader ->
////                        reader.readText()
////                    }
////                } else {
////                    Log.error("Cannot find file: $fileName")
////                    null
////                }
////            }
//            return mapper.readValue(str, T::class.java)
            return null
        }
    }
}