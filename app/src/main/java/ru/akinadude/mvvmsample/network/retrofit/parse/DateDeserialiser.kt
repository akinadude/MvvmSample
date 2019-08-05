package ru.akinadude.mvvmsample.network.retrofit.parse

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.joda.time.LocalDate
import ru.akinadude.mvvmsample.Constants
import java.lang.reflect.Type

class DateDeserialiser : JsonDeserializer<LocalDate> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDate {
        return LocalDate.parse(json.asString, Constants.DATE_FORMAT)
    }
}