package app.dev.kon.bullchat

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import java.util.*

data class Chat (
//    var UserImageData: Image,
    @DocumentId
    var id: String = "",
    var UserName: String = "userName",
    var ChatContent: String = "",
    var ChatDate: Date = Date(),
    var IconImage: Bitmap? = null
        )