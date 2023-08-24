package com.sunkitto.news.core.datastore.serializers

import androidx.datastore.core.Serializer
import com.sunkitto.news.core.datastore.model.SettingsPreferences
import java.io.InputStream
import java.io.OutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

object SettingsPreferencesSerializer : Serializer<SettingsPreferences> {

    override val defaultValue: SettingsPreferences
        get() = SettingsPreferences()

    override suspend fun readFrom(input: InputStream): SettingsPreferences {
        return try {
            Json.decodeFromString(
                deserializer = SettingsPreferences.serializer(),
                string = input.readBytes().decodeToString(),
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: SettingsPreferences, output: OutputStream) =
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = SettingsPreferences.serializer(),
                    value = t,
                ).encodeToByteArray(),
            )
        }
}