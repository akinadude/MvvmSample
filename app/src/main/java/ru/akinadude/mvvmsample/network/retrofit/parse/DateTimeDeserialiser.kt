package ru.akinadude.mvvmsample.network.retrofit.parse

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.joda.time.LocalDateTime
import ru.akinadude.mvvmsample.Constants
import java.lang.reflect.Type

class DateTimeDeserialiser : JsonDeserializer<LocalDateTime> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDateTime {
        return LocalDateTime.parse(json.asString, Constants.DATE_TIME_ZONE_FORMAT)
    }
}