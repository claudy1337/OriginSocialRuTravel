package com.autonture.originsocialrutravel.PartUI.Authorization

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.autonture.originsocialrutravel.MainActivity
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.ActivitySecurityCodeAuthenticationBinding
import java.util.concurrent.Executor

class SecurityCodeAuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecurityCodeAuthenticationBinding
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var executor: Executor

    private val listCode: MutableList<Int> = MutableList<Int>(4){0}
    private fun setUpEditors() {
        with(binding) {
            txt1.addTextChangedListener(GenericTextWatcher(txt1, txt2))
            txt2.addTextChangedListener(GenericTextWatcher(txt2, txt3))
            txt3.addTextChangedListener(GenericTextWatcher(txt3, txt4))
            txt4.addTextChangedListener(GenericTextWatcher(
                txt4,
                null,
                onLastEditTextFilled = {
                    txt4.clearFocus()
                    sendRequest("${txt1.text}${txt2.text}${txt3.text}${txt4.text}")
                }
            ))

            txt1.setOnKeyListener(GenericKeyEvent(txt1, null))
            txt2.setOnKeyListener(GenericKeyEvent(txt2, txt1))
            txt3.setOnKeyListener(GenericKeyEvent(txt3, txt2))
            txt4.setOnKeyListener(GenericKeyEvent(txt4, txt3))
        }

        initBio()
        binding.imageButtonBio.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
    private fun initBio(){
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                binding.textView7.text = "$errString"
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                binding.textView7.text = "Успешно"
                sendRequest("bio")

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                binding.textView7.text = "неверный ключ"
            }
        })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Биометрическая аутентификация")
            .setNegativeButtonText("Ввести код в ручную")
            .build()

    }

    private fun sendRequest(code:String){
        //проверка
        val codeUser = PrefsManager(this).getCode()
        if (code != null && code == codeUser || code== "bio" ){
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            binding.textView7.text = "неверный код"
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecurityCodeAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpEditors()
    }
}