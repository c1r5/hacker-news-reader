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
    val hits: List<PostDTO>,
    val hitsPerPage: Int,
    val page: Int,
) {
    companion object {
        private val json = Json {
            serializersModule = SerializersModule {
                polymorphic(PostDTO::class) {
                    subclass(PostDTO.StoryPostDTO::class, PostDTO.StoryPostDTO.serializer())
                    subclass(PostDTO.JobPostDTO::class, PostDTO.JobPostDTO.serializer())
                    subclass(PostDTO.CommentPostDTO::class, PostDTO.CommentPostDTO.serializer())
                }
                ignoreUnknownKeys = true
            }
        }

        fun fromJson(content: String): ApiResponseDTO = json.decodeFromString(serializer(), content)
    }
}

@Serializable
sealed class PostDTO {
    abstract val author: String
    abstract val children: List<Long>?
    abstract val createdAt: String
    abstract val createdAtI: Long
    abstract val objectID: String
    abstract val title: String
    abstract val updatedAt: String
    abstract val points: Int?
    abstract val url: String?
    abstract val numComments: Int?
    abstract val storyText: String?

    @Serializable
    data class StoryPostDTO(
        override val objectID: String,
        override val points: Int? = null,
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
        override val numComments: Int? = null,
        @SerialName("story_id")
        val storyId: Long? = null,
        @SerialName("story_text")
        override val storyText: String? = null,
    ) : PostDTO()

    @Serializable
    data class JobPostDTO(
        override val storyText: String? = null,
        override val numComments: Int? = null,
        override val points: Int? = null,
        override val url: String? = null,
        @SerialName("job_text")
        val jobText: String? = null,
        override val author: String,
        override val children: List<Long> = emptyList(),
        override val createdAt: String,
        override val createdAtI: Long,
        override val objectID: String,
        override val title: String,
        @SerialName("updated_at")
        override val updatedAt: String,
    ) : PostDTO()

    @Serializable
    data class CommentPostDTO(
        override val storyText: String? = null,
        override val numComments: Int? = null,
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
    ) : PostDTO()

}

object HitListSerializer : JsonTransformingSerializer<List<PostDTO>>(ListSerializer(HitSerializer)) {
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

object HitSerializer : JsonContentPolymorphicSerializer<PostDTO>(PostDTO::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<PostDTO> {
        return when {
            // Checa por StoryHit: se tiver "story_text" ou uma combinação de "url" sem "job_text"
            "story_text" in element.jsonObject || ("url" in element.jsonObject && "job_text" !in element.jsonObject) -> {
                PostDTO.StoryPostDTO.serializer()
            }
            // Checa por JobHit: se tiver "job_text" ou uma combinação de "url" sem "story_text"
            "job_text" in element.jsonObject || ("url" in element.jsonObject && "story_text" !in element.jsonObject) -> {
                PostDTO.JobPostDTO.serializer()
            }

            "comment_text" in element.jsonObject -> PostDTO.CommentPostDTO.serializer()

            else -> {
                throw SerializationException("Unknown element: $element")
            }
        }
    }

}

