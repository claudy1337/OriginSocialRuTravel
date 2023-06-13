package com.autonture.originsocialrutravel.Utilis

import android.content.Context
import com.autonture.originsocialrutravel.Utilis.Classes.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsManager(val context: Context)  {
    private var prefs = context.getSharedPreferences("travel", Context.MODE_PRIVATE)

    fun getCurrentUserList():MutableList<User>{
        return Gson().fromJson(prefs.getString("User", "[ ]"), object:TypeToken<MutableList<User>>(){

        }.type)
    }

    fun isLogining():Boolean{
        return prefs.getBoolean("setLoginig", false)
    }
    fun setLoginig(value:Boolean){
        prefs.edit().putBoolean("setLoginig", value).apply()
    }

    //list user
    fun addToListByCurrentUser(user: User){
        var list = getCurrentUserList()
        Gson().toJson(list)
        prefs.edit().putString("User",  Gson().toJson(list)).apply()
    }
    fun deleteListByUser(item: User){
        var list = getCurrentUserList()
        list.remove(item)
        Gson().toJson(list)
        prefs.edit().putString("User",  Gson().toJson(list)).apply()
    }

    //user login
    fun saveUserLogin(login: String){
        prefs.edit().putString("UserLogin", login).apply()

    }
    fun deleteUserLogin(){
        prefs.edit().clear().putString("UserLogin", "{ }").apply()
    }
    fun getUserLogin(): String? {
        return prefs.getString("UserLogin", null)
    }

    //user id
    fun saveUserId(id:Int){
        prefs.edit().putInt("UserId", id).apply()
    }
    fun deleteUserId() {
        prefs.edit().clear().putInt("UserId", 0).apply()
    }
    fun getUserId(): Int? {
        return prefs.getInt("UserId", 0)
    }

    //user code
    fun isCodeUser():Boolean {
        return prefs.getBoolean("isCodeUser", false)
    }
    fun setCode(isCode:Boolean){
        prefs.edit().putBoolean("isCodeUser", isCode).apply()
    }
    fun saveCode(code:String){
        prefs.edit().putString("UserCode", code).apply()
    }
    fun deleteCode(){
        prefs.edit().putString("UserCode", null).apply()
    }
    fun getCode():String? {
        return prefs.getString("UserCode", null)
    }

}