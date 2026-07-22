package com.traffic.data.model.local

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull

@Serializable
data class StationDataWrapper(
    @SerialName("STATION_LIST")
    val stationList: List<StationEntity>
)
@Serializable
data class LineDataWrapper(
    @SerialName("LINE_LIST")
    val lineList: List<LineEntity>
)

// 1. Non-Null String 변환기
object AnyToStringSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("AnyToStringSerializer", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): String {
        val jsonDecoder = decoder as? JsonDecoder ?: return decoder.decodeString()
        val element = jsonDecoder.decodeJsonElement() as? JsonPrimitive
        return element?.contentOrNull ?: ""
    }
    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }
}

// 2. Nullable String? 변환기
object NullableAnyToStringSerializer : KSerializer<String?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NullableAnyToStringSerializer", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): String? {
        val jsonDecoder = decoder as? JsonDecoder ?: return decoder.decodeString()
        val element = jsonDecoder.decodeJsonElement() as? JsonPrimitive
        return element?.contentOrNull
    }
    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: String?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            encoder.encodeString(value)
        }
    }
}