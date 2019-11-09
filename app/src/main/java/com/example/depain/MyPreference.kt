package com.example.depain

import android.content.Context

class MyPreference (context: Context) {

    val prefference_name = "sharedpreferanceexample"
    val preference_login_count = "LoginCount"
    val preference_usingLocalhost = "Localhost"

    val preference = context.getSharedPreferences(prefference_name, Context.MODE_PRIVATE)

    fun getLoginCount() : Int{
        return preference.getInt(preference_login_count, 0)
    }

    fun getLocalhost() : Boolean{
        return preference.getBoolean(preference_usingLocalhost, true)
    }

    fun setloginCount(count:Int){
        val editor = preference.edit()
        editor.putInt(preference_login_count, count)
        editor.apply()
    }

    fun setlocalhost(usinglocalhost: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(preference_usingLocalhost, usinglocalhost)
        editor.apply()
    }
}