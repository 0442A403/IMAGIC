package com.imagic

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.awt.image.BufferedImage
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.ByteBuffer
import javax.imageio.ImageIO


class Receiver(private val host: String,
               private val port: Int,
               private val onPhotoReceiveCallback: OnPhotoReceiveCallback): AsyncTask<Void, ReceivedPhoto, Void>() {
    private val timeout = 2000
    private val socket = Socket()

    override fun doInBackground(vararg params: Void?): Void {
        socket.connect(InetSocketAddress(host, port), timeout)
        val inputStream = socket.getInputStream()
        while (socket.isConnected) {
            var buffer = ByteArray(4)
            inputStream.read(buffer)
            val photoSize: Int = ByteBuffer.wrap(buffer).asIntBuffer().get()

            val imageAr = ByteArray(photoSize)
            inputStream.read(imageAr)

            val image: Bitmap = BitmapFactory.decodeByteArray(imageAr,0,photoSize)
            buffer = ByteArray(8)
            inputStream.read(buffer)
            val photoType: Int = ByteBuffer.wrap(buffer).asIntBuffer().get()
            if (photoType == 0) {
                publishProgress(ReceivedPhoto(image, ReceivedPhoto.PhotoType.Photo))
            } else {
                publishProgress(ReceivedPhoto(image, ReceivedPhoto.PhotoType.Video))
            }
        }
    }

    override fun onProgressUpdate(vararg values: ReceivedPhoto?) {
        super.onProgressUpdate(*values)
        val photo = values[0]!!
        if (photo.type == ReceivedPhoto.PhotoType.Photo) {
            onPhotoReceiveCallback.onPhotoReceive(photo)
        }
    }
}