package com.example.kidsislandv1.levels.easygames.puzzelgame

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kidsislandv1.R
import com.example.kidsislandv1.levels.easygames.*
import kotlinx.android.synthetic.main.player_item_easygames.*
import kotlinx.android.synthetic.main.win_diamond_layout_dialog.view.*
import kotlinx.android.synthetic.main.win_layout_dialog.view.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class puzzelActivity : AppCompatActivity() {



    lateinit var PlayerAdapter_easygames : PlayerAdapter_easygames
    lateinit var dataBase_easygames : PlayerDataBase_easygames
    lateinit var PlayerList_easygames: MutableList<Player_easygames>


    var mMediaPlayer: MediaPlayer? = null






    var pieces: ArrayList<PuzzlePiece?>? = null
    var mCurrentPhotoPath: String? = null
    var mCurrentPhotoUri: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzel)



        playSound()



        val PlayerText:TextView=findViewById(R.id.idtextplayerpuzzel)
        val scoreText:TextView=findViewById(R.id.idtextscorepuzzel)
        val layout = findViewById<RelativeLayout>(R.id.layout)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val intent = intent
        val assetName = intent.getStringExtra("assetName")
        mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath")
        mCurrentPhotoUri = intent.getStringExtra("mCurrentPhotoUri")
        val namePlayer=intent.getStringExtra("profilnameverspuzzelA")
        val scorePuzz=intent.getStringExtra("score")



        PlayerText.setText(namePlayer)
        scoreText.setText(scorePuzz)




        // run image related code after the view was laid out
        // to have all dimensions calculated
        imageView.post {
            if (assetName != null) {
                setPicFromAsset(assetName, imageView)
            } else if (mCurrentPhotoPath != null) {
                setPicFromPath(mCurrentPhotoPath!!, imageView)
            } else if (mCurrentPhotoUri != null) {
                imageView.setImageURI(Uri.parse(mCurrentPhotoUri))
            }
            pieces = splitImage()
            val touchListener = TouchListener(this@puzzelActivity)
            // shuffle pieces order
            Collections.shuffle(pieces)
            for (piece in pieces!!) {
                piece!!.setOnTouchListener(touchListener)
                layout.addView(piece)
                // randomize position, on the bottom of the screen
                val lParams = piece.layoutParams as RelativeLayout.LayoutParams
                lParams.leftMargin = Random().nextInt(layout.width - piece.pieceWidth)
                lParams.topMargin = layout.height - piece.pieceHeight
                piece.layoutParams = lParams
            }
        }
    }





    private fun setPicFromAsset(assetName: String, imageView: ImageView) {
        // Get the dimensions of the View
        val targetW = imageView.width
        val targetH = imageView.height
        val am = assets
        try {
            val `is` = am.open("img/$assetName")
            // Get the dimensions of the bitmap
            val bmOptions = BitmapFactory.Options()
            bmOptions.inJustDecodeBounds = true
            BitmapFactory.decodeStream(`is`, Rect(-1, -1, -1, -1), bmOptions)
            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight

            // Determine how much to scale down the image
            val scaleFactor = Math.min(photoW / targetW, photoH / targetH)
            `is`.reset()

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor
            bmOptions.inPurgeable = true
            val bitmap = BitmapFactory.decodeStream(`is`, Rect(-1, -1, -1, -1), bmOptions)
            imageView.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun splitImage(): ArrayList<PuzzlePiece?> {
        val piecesNumber = 12
        val rows = 4
        val cols = 3
        val imageView = findViewById<ImageView>(R.id.imageView)
        val pieces = ArrayList<PuzzlePiece?>(piecesNumber)

        // Get the scaled bitmap of the source image
        val drawable = imageView.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val dimensions = getBitmapPositionInsideImageView(imageView)
        val scaledBitmapLeft = dimensions[0]
        val scaledBitmapTop = dimensions[1]
        val scaledBitmapWidth = dimensions[2]
        val scaledBitmapHeight = dimensions[3]
        val croppedImageWidth = scaledBitmapWidth - 2 * Math.abs(scaledBitmapLeft)
        val croppedImageHeight = scaledBitmapHeight - 2 * Math.abs(scaledBitmapTop)
        val scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true)
        val croppedBitmap = Bitmap.createBitmap(
            scaledBitmap,
            Math.abs(scaledBitmapLeft),
            Math.abs(scaledBitmapTop),
            croppedImageWidth,
            croppedImageHeight
        )

        // Calculate the with and height of the pieces
        val pieceWidth = croppedImageWidth / cols
        val pieceHeight = croppedImageHeight / rows

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {
                // calculate offset for each piece
                var offsetX = 0
                var offsetY = 0
                if (col > 0) {
                    offsetX = pieceWidth / 3
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3
                }

                // apply the offset to each piece
                val pieceBitmap = Bitmap.createBitmap(
                    croppedBitmap,
                    xCoord - offsetX,
                    yCoord - offsetY,
                    pieceWidth + offsetX,
                    pieceHeight + offsetY
                )
                val piece = PuzzlePiece(applicationContext)
                piece.setImageBitmap(pieceBitmap)
                piece.xCoord = xCoord - offsetX + imageView.left
                piece.yCoord = yCoord - offsetY + imageView.top
                piece.pieceWidth = pieceWidth + offsetX
                piece.pieceHeight = pieceHeight + offsetY

                // this bitmap will hold our final puzzle piece image
                val puzzlePiece = Bitmap.createBitmap(
                    pieceWidth + offsetX,
                    pieceHeight + offsetY,
                    Bitmap.Config.ARGB_8888
                )

                // draw path
                val bumpSize = pieceHeight / 4
                val canvas = Canvas(puzzlePiece)
                val path = Path()
                path.moveTo(offsetX.toFloat(), offsetY.toFloat())
                if (row == 0) {
                    // top side piece
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                } else {
                    // top bump
                    path.lineTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 3).toFloat(),
                        offsetY.toFloat()
                    )
                    path.cubicTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 6).toFloat(),
                        (offsetY - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 6 * 5).toFloat(),
                        (offsetY - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 3 * 2).toFloat(),
                        offsetY.toFloat()
                    )
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                }
                if (col == cols - 1) {
                    // right side piece
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // right bump
                    path.lineTo(
                        pieceBitmap.width.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3).toFloat()
                    )
                    path.cubicTo(
                        (pieceBitmap.width - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6).toFloat(),
                        (pieceBitmap.width - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6 * 5).toFloat(),
                        pieceBitmap.width.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3 * 2).toFloat()
                    )
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                }
                if (row == rows - 1) {
                    // bottom side piece
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // bottom bump
                    path.lineTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 3 * 2).toFloat(),
                        pieceBitmap.height.toFloat()
                    )
                    path.cubicTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 6 * 5).toFloat(),
                        (pieceBitmap.height - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 6).toFloat(),
                        (pieceBitmap.height - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 3).toFloat(),
                        pieceBitmap.height.toFloat()
                    )
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                }
                if (col == 0) {
                    // left side piece
                    path.close()
                } else {
                    // left bump
                    path.lineTo(
                        offsetX.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3 * 2).toFloat()
                    )
                    path.cubicTo(
                        (offsetX - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6 * 5).toFloat(),
                        (offsetX - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6).toFloat(),
                        offsetX.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3).toFloat()
                    )
                    path.close()
                }

                // mask the piece
                val paint = Paint()
                paint.color = -0x1000000
                paint.style = Paint.Style.FILL
                canvas.drawPath(path, paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                canvas.drawBitmap(pieceBitmap, 0f, 0f, paint)

                // draw a white border
                var border = Paint()
                border.color = -0x7f000001
                border.style = Paint.Style.STROKE
                border.strokeWidth = 8.0f
                canvas.drawPath(path, border)

                // draw a black border
                border = Paint()
                border.color = -0x80000000
                border.style = Paint.Style.STROKE
                border.strokeWidth = 3.0f
                canvas.drawPath(path, border)

                // set the resulting bitmap to the piece
                piece.setImageBitmap(puzzlePiece)
                pieces.add(piece)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }
        return pieces
    }

    private fun getBitmapPositionInsideImageView(imageView: ImageView?): IntArray {
        val ret = IntArray(4)
        if (imageView == null || imageView.drawable == null) return ret

        // Get image dimensions
        // Get image matrix values and place them in an array
        val f = FloatArray(9)
        imageView.imageMatrix.getValues(f)

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        val d = imageView.drawable
        val origW = d.intrinsicWidth
        val origH = d.intrinsicHeight

        // Calculate the actual dimensions
        val actW = Math.round(origW * scaleX)
        val actH = Math.round(origH * scaleY)
        ret[2] = actW
        ret[3] = actH

        // Get image position
        // We assume that the image is centered into ImageView
        val imgViewW = imageView.width
        val imgViewH = imageView.height
        val top = (imgViewH - actH) / 2
        val left = (imgViewW - actW) / 2
        ret[0] = left
        ret[1] = top
        return ret
    }

    fun checkGameOver()  {
        if (isGameOver) {


            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)


            win () // lancee le fun win pour ajouter 10 au score

            val view = View.inflate(this@puzzelActivity, R.layout.win_layout_dialog, null)

            val builder = AlertDialog.Builder(this@puzzelActivity)
            builder.setView(view)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(false)

            view.buttonidcancelwin.setOnClickListener{
                var soudClick = R.raw.sound_button
                AudioPlay.playAudioButton(this, soudClick)

                dialog.cancel()




if ( dataBase_easygames.DAOplayer_easygames().getPlayerbyName(intent.getStringExtra("profilnameverspuzzelA").toString())%100==0){



                val view = View.inflate(this@puzzelActivity, R.layout.win_diamond_layout_dialog, null)

                val builder = AlertDialog.Builder(this@puzzelActivity)
                builder.setView(view)

                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setCancelable(false)

                view.buttonidcancelwindiamond.setOnClickListener{
                    var soudClick = R.raw.sound_button
                    AudioPlay.playAudioButton(this, soudClick)

                    dialog.cancel()}

}

            }
            view.Restartbtndia.setOnClickListener {
                var soudClick = R.raw.sound_button
                AudioPlay.playAudioButton(this, soudClick)
                finish()
                startActivity(intent)
            }
 }
    }

    private val isGameOver: Boolean
        private get() {
            for (piece in pieces!!) {
                if (piece!!.canMove) {

                    var saundmagnit = R.raw.sound_magnet
                    AudioPlay.playAudioButton(this, saundmagnit)



                    return false
                }
            }
            return true
        }

    private fun setPicFromPath(mCurrentPhotoPath: String, imageView: ImageView) {
        // Get the dimensions of the View
        val targetW = imageView.width
        val targetH = imageView.height

        // Get the dimensions of the bitmap
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true
        val bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
        var rotatedBitmap = bitmap

        // rotate bitmap if needed
        try {
            val ei = ExifInterface(mCurrentPhotoPath)
            val orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270f)
            }
        } catch (e: IOException) {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
        imageView.setImageBitmap(rotatedBitmap)
    }

    companion object {
        fun rotateImage(source: Bitmap, angle: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(angle)
            return Bitmap.createBitmap(
                source, 0, 0, source.width, source.height,
                matrix, true
            )
        }
    }



    fun win () {


        val textidfromintent = intent.getStringExtra("profilnameverspuzzelA")



        var player1=Player_easygames(1,"avatar1",0,0,0,0)
        var player2=Player_easygames(2,"avatar2",0,0,0,0)
        var player3=Player_easygames(3,"avatar3",0,0,0,0)
        var player4=Player_easygames(4,"avatar4",0,0,0,0)
        var player5=Player_easygames(5,"avatar5",0,0,0,0)
        var player6=Player_easygames(6,"avatar6",0,0,0,0)

        dataBase_easygames = PlayerDataBase_easygames.getDatabase(this)
        PlayerList_easygames=ArrayList()
        PlayerAdapter_easygames =PlayerAdapter_easygames(PlayerList_easygames)

     //   val textidfromintent = intent.getStringExtra("playername")



        val textid: TextView = findViewById(R.id.idtextplayerpuzzel)


        val textscore: TextView = findViewById(R.id.idtextscorepuzzel)

        textid.setText(textidfromintent)


        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAnewdiamond lbarrraa")




        if ( textidfromintent == "avatar1") {

            textscore.setText(dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar1").toString())


            var scoreactueldeplayer1 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar1")
            var newScorePlayer1 =  scoreactueldeplayer1 + 10
//            var newScorePlayer1 =  100


            var diamondactueldeplayer1 = dataBase_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar1")
            if (newScorePlayer1 %100==0) {
                var newdiamondPlayer1 =  diamondactueldeplayer1 + 1
                dataBase_easygames.DAOplayer_easygames().updatediamond(newdiamondPlayer1.toString(),1)
            }

//
//            println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAnewdiamond"+dataBase_easygames.DAOplayer_easygames().getAllPlayer())
//
//            println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAnewdiamond lda5elllll")



            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer1.toString(),1)
            PlayerList_easygames.add(player1)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        }
        else if ( textidfromintent == "avatar2") {


            textscore.setText(dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar2") .toString())



            var scoreactueldeplayer2 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar2")
            var newScorePlayer2 =  scoreactueldeplayer2 + 10



            var diamondactueldeplayer2 = dataBase_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar2")
            if (newScorePlayer2 %100==0) {
                var newdiamondPlayer1 =  diamondactueldeplayer2 + 1
                dataBase_easygames.DAOplayer_easygames().updatediamond(newdiamondPlayer1.toString(),1)
            }




            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer2.toString(),2)
            PlayerList_easygames.add(player2)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        } else if ( textidfromintent == "avatar3") {

            textscore.setText(dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar3").toString())


            var scoreactueldeplayer3 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar3")
            var newScorePlayer3 =  scoreactueldeplayer3 + 10



            var diamondactueldeplayer3 = dataBase_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar3")
            if (newScorePlayer3 %100==0) {
                var newdiamondPlayer3 =  diamondactueldeplayer3 + 1
                dataBase_easygames.DAOplayer_easygames().updatediamond(newdiamondPlayer3.toString(),1)
            }


            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer3.toString(),3)
            PlayerList_easygames.add(player3)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        } else if ( textidfromintent == "avatar4") {

            textscore.setText(dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar4").toString())


            var  scoreactueldeplayer4 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar4")
            var newScorePlayer4 =  scoreactueldeplayer4 + 10



            var diamondactueldeplayer4 = dataBase_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar4")
            if (newScorePlayer4 %100==0) {
                var newdiamondPlayer4 =  diamondactueldeplayer4 + 1
                dataBase_easygames.DAOplayer_easygames().updatediamond(newdiamondPlayer4.toString(),1)
            }


            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer4.toString(),4)
            PlayerList_easygames.add(player4)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        } else if ( textidfromintent == "avatar5") {

            textscore.setText(dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar5") .toString())



            var scoreactueldeplayer5 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar5")
            var newScorePlayer5 =  scoreactueldeplayer5 + 10



            var diamondactueldeplayer5 = dataBase_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar5")
            if (newScorePlayer5 %100==0) {
                var newdiamondPlayer5 =  diamondactueldeplayer5 + 1
                dataBase_easygames.DAOplayer_easygames().updatediamond(newdiamondPlayer5.toString(),1)
            }


            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer5.toString(),5)
            PlayerList_easygames.add(player5)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        } else {


            textscore.setText(dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar6").toString())



            var  scoreactueldeplayer6 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar6")
            var newScorePlayer6 =  scoreactueldeplayer6 + 10



            var diamondactueldeplayer6 = dataBase_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar6")
            if (newScorePlayer6 %100==0) {
                var newdiamondPlayer6 =  diamondactueldeplayer6 + 1
                dataBase_easygames.DAOplayer_easygames().updatediamond(newdiamondPlayer6.toString(),1)
            }

            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer6.toString(),6)
            PlayerList_easygames.add(player6)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        }








        fun playSound() {

            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound_music_puzzel_game)
                mMediaPlayer!!.isLooping = true
                mMediaPlayer!!.start()
            } else mMediaPlayer!!.start()
        }

    }




    override fun onBackPressed (){
        super.onBackPressed()
        var intent = Intent(this, MainpuzzelActivity::class.java)
        stopSound()
        startActivity(intent)

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




}
