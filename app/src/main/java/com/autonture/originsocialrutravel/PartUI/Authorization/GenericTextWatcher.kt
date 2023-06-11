package com.autonture.originsocialrutravel.PartUI.Authorization

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.autonture.originsocialrutravel.R



class GenericTextWatcher(private val currentView: View, private val nextView: View?, private val onLastEditTextFilled: () -> Unit = {}) :
    TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
        val text = editable.toString()
        when (currentView.id) {
            R.id.txt1 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.txt2 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.txt3 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.txt4 -> if (text.length == 1) onLastEditTextFilled()
            //You can use EditText4 same as above to hide the keyboard
        }
    }
}

class GenericKeyEvent(private val currentView: EditText, private val previousView: EditText?) :
    View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.txt1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }
}

