package dev.eric.hnreader.models.dtos

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
data class ApiResponseDTO(
    @Serializable(with = HitListSerializer::class)
    val hits: List<HitDTO>,
    val hitsPerPage: Long,
    val page: Long,
) {
    companion object {
        private val json = Json {
            serializersModule = SerializersModule {
                polymorphic(HitDTO::class) {
                    subclass(HitDTO.AskHitDTO::class, HitDTO.AskHitDTO.serializer())
                    subclass(HitDTO.StoryHitDTO::class, HitDTO.StoryHitDTO.serializer())
                    subclass(HitDTO.PollHitDTO::class, HitDTO.PollHitDTO.serializer())
                    subclass(HitDTO.CommentHitDTO::class, HitDTO.CommentHitDTO.serializer())
                }
                ignoreUnknownKeys = true
            }
        }

        fun fromJson(content: String): ApiResponseDTO = json.decodeFromString(serializer(), content)
    }
}

@Serializable
sealed class HitDTO {
    abstract val author: String
    abstract val children: List<Long>
    abstract val createdAt: String
    abstract val createdAtI: Long
    abstract val objectID: String
    abstract val title: String
    abstract val updatedAt: String
    abstract val points: Int?

    @Serializable
    data class AskHitDTO(
        override val author: String,
        override val children: List<Long>,
        @SerialName("created_at")
        override val createdAt: String,
        @SerialName("created_at_i")
        override val createdAtI: Long,
        @SerialName("num_comments")
        val numComments: Long,
        override val objectID: String,
        override val points: Int,
        @SerialName("story_id")
        val storyId: Long,
        override val title: String,
        @SerialName("updated_at")
        override val updatedAt: String,
        @SerialName("story_text")
        val storyText: String,
    ) : HitDTO()

    @Serializable
    data class StoryHitDTO(
        override val author: String,
        override val children: List<Long>,
        @SerialName("created_at")
        override val createdAt: String,
        @SerialName("created_at_i")
        override val createdAtI: Long,
        override val objectID: String,
        override val points: Int,
        override val title: String,
        @SerialName("updated_at")
        override val updatedAt: String,
        @SerialName("num_comments")
        val numComments: Long,
        @SerialName("story_id")
        val storyId: Long,
        val url: String
    ) : HitDTO()

    @Serializable
    data class PollHitDTO(
        override val author: String,
        override val children: List<Long>,
        override val createdAt: String,
        override val createdAtI: Long,
        @SerialName("num_comments")
        val numComments: Long,
        override val objectID: String,
        val parts: List<Long>,
        override val points: Int,
        override val title: String,
        @SerialName("updated_at")
        override val updatedAt: String,
    ) : HitDTO()

    @Serializable
    data class CommentHitDTO(
        override val author: String,
        override val children: List<Long>,
        @SerialName("comment_text")
        val commentText: String,
        override val createdAt: String,
        override val createdAtI: Long,
        override val objectID: String,
        @SerialName("parent_id")
        val parentID: Long,
        override val points: Int? = null,
        @SerialName("story_id")
        val storyId: Long,
        @SerialName("story_title")
        override val title: String,
        @SerialName("story_url")
        val storyUrl: String,
        @SerialName("updated_at")
        override val updatedAt: String,
    ) : HitDTO()

}

object HitListSerializer : JsonTransformingSerializer<List<HitDTO>>(ListSerializer(HitSerializer)) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        // Filtra objetos desconhecidos da lista
        val filteredHits = element.jsonArray.filter {
            it.jsonObject.keys.any { key ->
                key in listOf("story_text", "url", "comment_text", "parts")
            }
        }
        return JsonArray(filteredHits)
    }
}

object HitSerializer : JsonContentPolymorphicSerializer<HitDTO>(HitDTO::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<HitDTO> {
        return when {
            "story_text" in element.jsonObject -> HitDTO.AskHitDTO.serializer()
            "url" in element.jsonObject -> HitDTO.StoryHitDTO.serializer()
            "comment_text" in element.jsonObject -> HitDTO.CommentHitDTO.serializer()
            "parts" in element.jsonObject -> HitDTO.PollHitDTO.serializer()
            else -> {
                throw SerializationException("Unknown element: $element")
            }
        }
    }

}

