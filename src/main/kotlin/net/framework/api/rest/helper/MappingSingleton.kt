package net.framework.api.rest.helper

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * JSONマッパーシングルトンクラス。
 */
class MappingSingleton {

    companion object {
        private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

        fun getMapper(): Gson {
            return this.gson
        }
    }
}