package ade.yemi.growthchecker.Data

import android.content.Context
import android.content.SharedPreferences

class AlarmInfo {
    var context: Context? = null
    var sharef: SharedPreferences? = null
    constructor(context: Context){
        this.context = context
        this.sharef = context.getSharedPreferences("alarminfo", Context.MODE_PRIVATE)
    }

    fun SaveAlarmInfo(hour: Int, minute: Int){
        var editor = sharef!!.edit()
        editor.putInt("hour", hour)
        editor.putInt("minute", minute)
        editor.commit()
    }

    fun setday(day: Int){
        var editor = sharef!!.edit()
        editor.putInt("day", day)
        editor.commit()
    }
    fun setongoing(ongoing: Boolean){
        var editor = sharef!!.edit()
        editor.putBoolean("ongoing", ongoing)
        editor.commit()
    }

    fun gethour(): Int{
        return sharef!!.getInt("hour", 0)
    }

    fun setassess(assess: Boolean){
        var editor = sharef!!.edit()
        editor.putBoolean("assess", assess)
        editor.commit()
    }

    fun getassess(): Boolean{
        return sharef!!.getBoolean("assess", false)
    }

    fun getday(): Int{
        return sharef!!.getInt("day", 0)
    }
    fun getminute(): Int{
        return sharef!!.getInt("minute", 0)
    }
    fun getongoing(): Boolean{
        return sharef!!.getBoolean("ongoing", false)
    }
}