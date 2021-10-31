package app.dev.kon.bullchat

import com.google.firebase.firestore.DocumentId
import java.time.LocalDateTime
import java.util.*

data class Post(
    @DocumentId
    var id: String = "",
    var Title: String = "",
    var Content: String = "",
    val PostDate: LocalDateTime = LocalDateTime.now()
)
