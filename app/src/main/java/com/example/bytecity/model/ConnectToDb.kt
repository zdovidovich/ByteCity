package com.example.bytecity.model

import android.content.Context
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties


open class DbConnection{
    lateinit var connection: Connection



    private fun loadPropertiesFromAssets(context: Context): Properties {
        val props = Properties()
        try {
            context.assets.open("database.properties").use { inputStream ->
                props.load(inputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return props
    }



    fun connect(context: Context) {
        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance()
        } catch (ex: Exception) {
            println("Connection failed...")
            println(ex)
        }
        val props = loadPropertiesFromAssets(context)


        val url: String = props.getProperty("url")
        val username: String = props.getProperty("username")
        val password: String = props.getProperty("password")
        connection = DriverManager.getConnection(url, username, password)
    }

}


object DbConn: DbConnection()