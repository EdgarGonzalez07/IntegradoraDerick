package utez.edu.mx.integradoraderick.ui.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

fun uriToMultipart(
    context: Context,
    uri: Uri
): MultipartBody.Part {

    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri) ?: "image/*"

    val inputStream = contentResolver.openInputStream(uri)!!
    val bytes = inputStream.readBytes()
    inputStream.close()

    val requestBody = bytes.toRequestBody(mimeType.toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(
        name = "image",          // ⚠️ DEBE llamarse "image"
        filename = "upload.jpg",
        body = requestBody
    )
}