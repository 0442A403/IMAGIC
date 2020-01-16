package com.imagic

import android.graphics.Bitmap

class ReceivedPhoto(val image: Bitmap, val type: PhotoType) {
    enum class PhotoType {
        Photo, Video
    }
}