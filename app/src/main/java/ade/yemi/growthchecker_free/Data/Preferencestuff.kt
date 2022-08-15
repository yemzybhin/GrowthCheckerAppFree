package ade.yemi.growthchecker_free.Data

import android.content.Context
import android.content.SharedPreferences

class Preferencestuff {
    var context: Context? = null
    var sharef: SharedPreferences? = null
    constructor(context: Context){
        this.context = context
        this.sharef = context.getSharedPreferences("preferencesstuff", Context.MODE_PRIVATE)
    }
    fun setPoint(int: Int){
        var editor = sharef!!.edit()
        editor.putInt("points", int)
        editor.commit()
    }
    fun getPoint(): Int{
        return sharef!!.getInt("points", 0)
    }
    fun setLocalAds(adsString: String){
        var editor = sharef!!.edit()
        editor.putString("localAds", adsString)
        editor.commit()
    }
    fun getLocalAds(): String?{
        return sharef!!.getString("localAds", "")
    }
    fun setloacalApps(appString: String){
        var editor = sharef!!.edit()
        editor.putString("localApps", appString)
        editor.commit()
    }
    fun getlocalApps(): String?{
        return sharef!!.getString("localApps", "")
    }

    fun setUserAttributes(type : String, value: String){
        var editor = sharef!!.edit()
        when(type){
            "userName" -> {
                editor.putString("userName", value)
            }
            "userAge" ->{
                editor.putString("userAge", value)
            }
            "displayImage"->{
                editor.putString("displayImage", value)
            }
        }
        editor.commit()
    }
    fun getUserAttributes(type : String): String?{
        when(type){
            "userName" -> {
                return sharef!!.getString("userName", "Anonymous")
            }
            "userAge" ->{
                return sharef!!.getString("userAge", "0")
            }
            "displayImage"->{
                return sharef!!.getString("displayImage", "0")
            }
            else ->{
                return "No value"
            }
        }

    }

    fun setAddiction(appString: String){
        var editor = sharef!!.edit()
        editor.putString("addiction", appString)
        editor.commit()
    }
    fun getAddiction(): String?{
        return sharef!!.getString("addiction", "")
    }
}