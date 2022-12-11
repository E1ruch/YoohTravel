package com.example.yoohtravel

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthOptions.*
import com.google.firebase.auth.PhoneAuthProvider.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*

class PhoneActivity : AppCompatActivity() {

    private lateinit var sendOTPBtn: Button
    private lateinit var phoneNumberET: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var number: String
    private lateinit var mProgressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        init()
        sendOTPBtn.setOnClickListener {
            number = phoneNumberET.text.trim().toString()
            if (number.isNotEmpty()) {
                if (number.length == 10) {
                    number = "+7$number"
                    mProgressBar.visibility = View.VISIBLE
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, SECONDS)
                        .setActivity(this)
                        .setCallbacks(callbacks)
                        .build()
                    verifyPhoneNumber(options)

                } else {
                    Toast.makeText(this, "Please Enter Corect number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter number", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun init() {
        mProgressBar = findViewById(R.id.phoneProgressBar)
        mProgressBar.visibility= View.INVISIBLE
        sendOTPBtn = findViewById(R.id.sendOTPBtn)
        phoneNumberET = findViewById(R.id.phoneEditTextNumber)
        auth = FirebaseAuth.getInstance()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Успешная регистрация, обновление пользовательского интерфейса с информацией о зарегистрированном пользователе

                } else {
                    // Вход в систему не удался, выведите сообщение и обновите пользовательский интерфейс
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // введенный проверочный код недействителен
                    }
                    // Обновление пользовательского интерфейса
                }
            }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Этот обратный вызов будет вызываться в двух ситуациях:
            // 1 - Мгновенная проверка. В некоторых случаях номер телефона можно мгновенно // проверить.
            // верифицирован без необходимости отправлять или вводить проверочный код.
            // 2 - Автопоиск. На некоторых устройствах службы Google Play могут автоматически
            // обнаруживать входящее проверочное SMS и выполнять проверку без // действия пользователя.
            // действия пользователя.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // Этот обратный вызов вызывается в случае некорректного запроса на проверку,
            // например, если формат телефонного номера недействителен.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Неверный запрос
                Log.d("TAG", "onVerificateFailer: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // Квота SMS для проекта превышена
                Log.d("TAG", "onVerificateFailer: ${e.toString()}")

            }

            // Показать сообщение и обновить пользовательский интерфейс
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // Код проверки SMS был отправлен на указанный номер телефона, нам
            // теперь нужно попросить пользователя ввести код, а затем создать
            // комбинацию кода с идентификатором верификации.
            // Сохраните идентификатор проверки и маркер повторной отправки, чтобы мы могли использовать их позже
            val intent = Intent(this@PhoneActivity, OTPActivity::class.java)
            intent.putExtra("OTP", verificationId)
            intent.putExtra("resendToken", token)
            startActivity(intent)
            mProgressBar.visibility = View.INVISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}