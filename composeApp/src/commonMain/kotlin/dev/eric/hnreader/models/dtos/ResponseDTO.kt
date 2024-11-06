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
    val hitsPerPage: Int,
    val page: Int,
) {
    companion object {
        private val json = Json {
            serializersModule = SerializersModule {
                polymorphic(HitDTO::class) {
                    subclass(HitDTO.AskHitDTO::class, HitDTO.AskHitDTO.serializer())
                    subclass(HitDTO.StoryHitDTO::class, HitDTO.StoryHitDTO.serializer())
                    subclass(HitDTO.JobHitDTO::class, HitDTO.JobHitDTO.serializer())
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
    abstract val children: List<Long>?
    abstract val createdAt: String
    abstract val createdAtI: Long
    abstract val objectID: String
    abstract val title: String
    abstract val updatedAt: String
    abstract val points: Int?
    abstract val url: String?

    @Serializable
    data class AskHitDTO(
        override val url: String? = null,
        override val author: String,
        override val children: List<Long> = emptyList(),
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
        override val objectID: String,
        override val points: Int? = 0,
        override val title: String,
        override val url: String? = null,
        override val author: String,
        override val children: List<Long> = emptyList(),
        @SerialName("created_at")
        override val createdAt: String,
        @SerialName("created_at_i")
        override val createdAtI: Long,
        @SerialName("updated_at")
        override val updatedAt: String,
        @SerialName("num_comments")
        val numComments: Long? = 0,
        @SerialName("story_id")
        val storyId: Long? = null,
        @SerialName("story_text")
        val storyText: String? = null,
    ) : HitDTO()

    @Serializable
    data class JobHitDTO(
        override val url: String? = null,
        override val author: String,
        override val children: List<Long> = emptyList(),
        override val createdAt: String,
        override val createdAtI: Long,
        override val objectID: String,
        override val points: Int? = null,
        override val title: String,
        @SerialName("updated_at")
        override val updatedAt: String,
        @SerialName("job_text")
        val jobText: String? = null
    ) : HitDTO()

    @Serializable
    data class CommentHitDTO(
        override val url: String? = null,
        override val author: String,
        override val children: List<Long> = emptyList(),
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
            // Checa por StoryHit: se tiver "story_text" ou uma combinação de "url" sem "job_text"
            "story_text" in element.jsonObject || ("url" in element.jsonObject && "job_text" !in element.jsonObject) -> {
                HitDTO.StoryHitDTO.serializer()
            }
            // Checa por JobHit: se tiver "job_text" ou uma combinação de "url" sem "story_text"
            "job_text" in element.jsonObject || ("url" in element.jsonObject && "story_text" !in element.jsonObject) -> {
                HitDTO.JobHitDTO.serializer()
            }

            "comment_text" in element.jsonObject -> HitDTO.CommentHitDTO.serializer()

            else -> {
                throw SerializationException("Unknown element: $element")
            }
        }
    }

}

