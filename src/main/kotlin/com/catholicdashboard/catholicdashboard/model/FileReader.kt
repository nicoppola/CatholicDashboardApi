package com.catholicdashboard.catholicdashboard.model

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.nio.file.Files

class FileReader {
    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    companion object {
        inline fun <reified T> getFile(fileName: String): T? {
            val mapper = jacksonObjectMapper()
            try {
                val jsonFile = ClassPathResource(fileName).file
                val str = Files.readString(jsonFile.toPath())
                return mapper.readValue(str, T::class.java)
            } catch (e: Exception) {
                println("***************************EXCEPTION***************************")
                println(e)
            }
            return null
        }

        fun writeToFile(fileName: String, data: CalendarData) {
           // "novus/calendar$year.json"
            val filePrefix = "src/main/resources/"
            val fullPath = filePrefix+fileName.removePrefix("/")
            val mapper = jacksonObjectMapper()
            try{
                val writer = mapper.writer(DefaultPrettyPrinter())
                File(fullPath).createNewFile()
                writer.writeValue(File(fullPath), data)
            } catch (e: Exception){
                println("***************************EXCEPTION***************************")
                println(e)}
        }

        fun doesFileExist(fileName: String): Boolean {
            try {
                val jsonFile = ClassPathResource(fileName).file
            } catch (e: FileNotFoundException){
                return false
            }
            return true
        }
    }
}