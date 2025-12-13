package utez.edu.mx.integradoraderick.ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun createImageFile(context: Context): Uri {
    val imageFile = File(
        context.getExternalFilesDir("Pictures"),
        "almacen_${System.currentTimeMillis()}.jpg"
    )

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}