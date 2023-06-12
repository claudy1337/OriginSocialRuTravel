package com.autonture.originsocialrutravel.PartUI.Authorization

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
import com.autonture.originsocialrutravel.MainActivity
import com.autonture.originsocialrutravel.databinding.ActivitySecurityCodeAuthenticationBinding

class SecurityCodeAuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecurityCodeAuthenticationBinding
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
                    sendRequest()
                }
            ))

            txt1.setOnKeyListener(GenericKeyEvent(txt1, null))
            txt2.setOnKeyListener(GenericKeyEvent(txt2, txt1))
            txt3.setOnKeyListener(GenericKeyEvent(txt3, txt2))
            txt4.setOnKeyListener(GenericKeyEvent(txt4, txt3))
        }
        binding.imageButtonBio.setOnClickListener {

        }
    }
    private fun sendRequest(){
        //проверка
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecurityCodeAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpEditors()
    }
}