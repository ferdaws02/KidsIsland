package com.example.kidsislandv1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kidsislandv1.database.ApiInterface
import com.example.kidsislandv1.database.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {




    lateinit var sharedPreferences: SharedPreferences
    var isRemembered2 = false



    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("639193427799-peiooalrhevuvhtn4t4ke4d6lp0qkvq5.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)







        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        isRemembered2=sharedPreferences.getBoolean("CHECKBOX2",false)

        if (isRemembered2){
            val intent = Intent(this,age::class.java)
            startActivity(intent)
            finish()
        }
    //    button.setOnClickListener{

//            val name: String = editTextTextPersonName.text.toString()
//            val age: Int = editTextTextPersonName2.text.toString().toInt()

            // val checked: Boolean = checkBox.isChecked


      //  }
    //}


















    sign_in_button.setOnClickListener {
            println("FIL CLICK LISTENER********")
            var mediaPlayer = MediaPlayer.create(this, R.raw.sound_button)
            mediaPlayer?.start()
            signIn()


        }
    }

    private fun signIn() {
        println("FIL SIGNIN********")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            println("FIL ON ACTIVITY RESULT***")
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }


    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            println("FIL HANDLE FIL TRY********")
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID",googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)

            val apiInterface = ApiInterface.create()
            val map: HashMap<String, String> = HashMap()
            map["email"]=googleEmail
            map["id"]=googleId
//Node js bidaya ************************************
            apiInterface.seConnecter(map).enqueue (object : Callback<User> {

                override fun onResponse(call: Call<User>, response:
                Response<User>
                ) {
                    Log.e("Login Successfut", map.toString())
                    val user = response.body()
                    Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT)
                        .show()

//                    val intent = Intent(this@LoginActivity, SampleResult::class.java)
//                    startActivity(intent)
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                    Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
                }
            })
            //Node js nihaya *************************
        } catch (e: ApiException) {
            println("FIL HANDLE CATCH********")
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }



        val editor: SharedPreferences.Editor = sharedPreferences. edit()
//            editor.putString("NAME", name)
//            editor.putInt("AGE", age)
        editor.putBoolean("CHECKBOX2", true)
        editor.apply()


        val intent = Intent(this,age::class.java)
        startActivity(intent)
        finish()









//
//        println("5RAJ MIL HANDLE********")
//        finish()
//        startActivity(Intent(this,age::class.java))
    }





    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // Update your UI here
            }
    }
    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // Update your UI here
            }
    }

}