package com.autonture.originsocialrutravel.PartUI.Authorization

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.autonture.originsocialrutravel.MainActivity
import com.autonture.originsocialrutravel.databinding.ActivitySecurityCodeAuthenticationBinding

class SecurityCodeAuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecurityCodeAuthenticationBinding
    private val listCode: MutableList<Int> = MutableList<Int>(4){0}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecurityCodeAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.codeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            startAfterDesiredNumber(4)
        }
    })


        buttonPress(binding.imageButton0, "0")
        buttonPress(binding.imageButton, "1")
        buttonPress(binding.imageButton2, "2")
        buttonPress(binding.imageButton3, "3")
        buttonPress(binding.imageButton4, "4")
        buttonPress(binding.imageButton5, "5")
        buttonPress(binding.imageButton6, "6")
        buttonPress(binding.imageButton7, "7")
        buttonPress(binding.imageButton8, "8")
        buttonPress(binding.imageButton9, "9")
    }
    private fun startAfterDesiredNumber(desiredNumber: Int) {
        var count = 0
        while (count < desiredNumber) {
            count++

        }
        if (count == desiredNumber) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            
        }
    }
    fun buttonPress(button: ImageButton, num:String) {
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val buttonText = num
                val buttonNumber = buttonText.toIntOrNull()

                if (buttonNumber != null) {
                    listCode.add(buttonNumber)
                    var i = 1
                    do {
                        binding.codeText.setText(listCode[i].toString())
                        i++
                    } while (i < 4)
                } else {
                    showToast("Неверный код")
                }
            }
        })
    }

    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}