package com.imagic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CameraActivity : AppCompatActivity(), OnPhotoReceiveCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onPhotoReceive(photo: ReceivedPhoto) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}