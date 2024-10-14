package com.catholicdashboard.catholicdashboard.model

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths

@Service
class FileReader @Autowired constructor(private val resourceLoader: ResourceLoader) {

    fun <T> getFile(fileName: String, clazz: Class<T>): T? {
        val mapper = jacksonObjectMapper()
        return try {
            val resource = resourceLoader.getResource("classpath:$fileName")
            resource.inputStream.use { inputStream ->
                mapper.readValue(inputStream, clazz)
            }
        } catch (e: Exception) {
            println("***************************EXCEPTION***************************")
            println(e)
            null
        }
    }

    fun writeToFile(fileName: String, data: CalendarData) {
        // "novus/calendar$year.json"
//        val filePrefix = "src/main/resources/"
//        val fullPath = filePrefix+fileName.removePrefix("/")

        val resource: Resource = resourceLoader.getResource("classpath:$fileName")
        val mapper = jacksonObjectMapper()
        try{
            val fullPath = Paths.get(resource.toString(), fileName)
            Files.createDirectories(fullPath.parent)
            Files.newOutputStream(fullPath).use { outputStream ->
                val writer = mapper.writer(DefaultPrettyPrinter())
                writer.writeValue(outputStream, data)
            }
        } catch (e: Exception){
            println("***************************EXCEPTION***************************")
            println(e)}
    }

    fun doesFileExist(fileName: String): Boolean {
        return try {
            val resource: Resource = resourceLoader.getResource("classpath:$fileName")
            resource.exists()
        } catch (e: Exception) {
            println("***************************EXCEPTION***************************")
            println(e)
            false
        }
    }
}