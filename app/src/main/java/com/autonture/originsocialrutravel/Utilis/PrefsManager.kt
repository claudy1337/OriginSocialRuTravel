package com.autonture.originsocialrutravel.Utilis

import android.content.Context
import com.autonture.originsocialrutravel.Utilis.Classes.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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


    fun getList():MutableList<User>{
        return Gson().fromJson(prefs.getString("User", "[ ]"), object:
            TypeToken<MutableList<User>>(){

        }.type)
    }
    fun addToList(item:User){
        var list = getList()
        list.add(item)
        Gson().toJson(list)
        prefs.edit().putString("User",  Gson().toJson(list)).apply()
    }
    fun removeList(){
        prefs.edit().clear().putString("User", "{ }").apply() //очистка
    }
}