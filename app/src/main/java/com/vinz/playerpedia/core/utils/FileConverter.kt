package com.vinz.playerpedia.core.utils

import androidx.room.TypeConverter
import java.io.File

class FileConverter {
    @TypeConverter
    fun fromFile(file: File?): String? {
        return file?.path
    }

    @TypeConverter
    fun toFile(path: String?): File? {
        return if (path != null) File(path) else null
    }
}