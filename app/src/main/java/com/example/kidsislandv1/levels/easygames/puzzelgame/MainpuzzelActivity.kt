package com.example.kidsislandv1.levels.easygames.puzzelgame

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.kidsislandv1.R
import com.example.kidsislandv1.levels.easygames.Database_easygames
import com.example.kidsislandv1.levels.easygames.Easygames
import com.example.kidsislandv1.levels.easygames.PlayerDataBase_easygames
import com.example.kidsislandv1.levels.easygames.memorygame.MemoryLevel
import kotlinx.android.synthetic.main.activity_memory_level.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainpuzzelActivity : AppCompatActivity() {

    lateinit var dataBase_easygames : PlayerDataBase_easygames

    var mCurrentPhotoPath: String? = null
    var mMediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpuzzel)


       var textidpm: TextView = findViewById(R.id.idtextplayerpm)
        var textscorepm: TextView = findViewById(R.id.idtextscorepm)


        val sharedPreferences = getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString( "STRING_KEY",  null)
        val playernameMainpuzzelActivity= savedString.toString()


        dataBase_easygames = PlayerDataBase_easygames.getDatabase(this)
        val score =dataBase_easygames.DAOplayer_easygames().getPlayerbyName(playernameMainpuzzelActivity)
        textscorepm.setText(score.toString())


        playSound()
        loadData()


        back.setOnClickListener { onBackPressed()}



        val am = assets
        try {
            val files = am.list("img")
            val grid = findViewById<GridView>(R.id.grid)
            grid.adapter = ImageAdapter(this)
            grid.onItemClickListener =
                AdapterView.OnItemClickListener {




                        adapterView, view, i, l ->
                    val intent = Intent(applicationContext, puzzelActivity::class.java)
                    intent.putExtra("assetName", files!![i % files.size])

                    finish()
                    startActivity(intent)
                }
        } catch (e: IOException) {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

//    fun onImageFromCameraClick(view: View?) {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (intent.resolveActivity(packageManager) != null) {
//            var photoFile: File? = null
//            try {
//                photoFile = createImageFile()
//            } catch (e: IOException) {
//                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
//            }
//            if (photoFile != null) {
//                val photoUri = FileProvider.getUriForFile(
//                    this,
//                    applicationContext.packageName + ".fileprovider",
//                    photoFile
//                )
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
//    }

//    @Throws(IOException::class)
//    private fun createImageFile(): File? {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // permission not granted, initiate request
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE
//            )
//        } else {
//            // Create an image file name
//            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//            val imageFileName = "JPEG_" + timeStamp + "_"
//            val storageDir =
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//            val image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",  /* suffix */
//                storageDir /* directory */
//            )
//            mCurrentPhotoPath = image.absolutePath // save this to use in the intent
//            return image
//        }
//        return null
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE -> {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    onImageFromCameraClick(View(this))
//                }
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val intent = Intent(this, puzzelActivity::class.java)
            intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath)
            finish()
            startActivity(intent)
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            val uri = data!!.data
            val intent = Intent(this, puzzelActivity::class.java)
            intent.putExtra("mCurrentPhotoUri", uri.toString())
            finish()
            startActivity(intent)
        }
    }

//    fun onImageFromGalleryClick(view: View?) {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
//            )
//        } else {
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.type = "image/*"
//            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
//        }
//    }

    companion object {
        private const val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2
        private const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3
        const val REQUEST_IMAGE_GALLERY = 4
    }


    // 1. Plays the water sound
    fun playSound() {

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.sound_music_puzzel_game)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    // 2. Pause playback
    fun pauseSound(view: View) {
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) mMediaPlayer!!.pause()
    }

    // 3. {optional} Stops playback
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    // 4. Closes the MediaPlayer when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    override fun onBackPressed (){
        super.onBackPressed()
        stopSound()
        var intent = Intent(this, Easygames::class.java)
        startActivity(intent)

    }

    private fun loadData() {


        var textidpm: TextView = findViewById(R.id.idtextplayerpm)
        val sharedPreferences = getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString( "STRING_KEY",  null)


        textidpm.setText(savedString.toString())


    }









}
