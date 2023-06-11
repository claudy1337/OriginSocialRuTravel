package com.autonture.originsocialrutravel.Utilis

import android.content.Context

class PrefsManager(val context: Context)  {
    private var prefs = context.getSharedPreferences("travel", Context.MODE_PRIVATE)

    fun setLogged(isLogining:Boolean){
        prefs.edit().putBoolean("Login", isLogining).apply()
    }
    fun isLogged():Boolean{
        return prefs.getBoolean("Login", false)
    }
    fun setCode(code:String){
        prefs.edit().putString("Code", code).apply()
    }
    fun isCode():Boolean{
        return prefs.getBoolean("isCode", false)
    }
    fun setCodeBoolean(isCode:Boolean){
        prefs.edit().putBoolean("isCode", isCode).apply()
    }
}