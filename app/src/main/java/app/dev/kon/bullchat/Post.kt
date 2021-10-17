package app.dev.kon.bullchat

import com.google.firebase.firestore.DocumentId
import java.time.LocalDateTime
import java.util.*

data class Post(
    @DocumentId
    val id: String = "",
    val Title: String,
    val Content: String,
    val PostDate: LocalDateTime
)
