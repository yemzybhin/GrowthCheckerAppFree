package ade.yemi.growthchecker_free.Helpers.Graph

import com.github.mikephil.charting.data.Entry

fun GenerateFloat(list: MutableList<String>):ArrayList<Entry>{
    val entries = ArrayList<Entry>()
    var pointfloat = mutableListOf<Float>()
    var cumulative = mutableListOf<Float>()
    var days = mutableListOf<Float>()
    var loopadd = 0f
    for (i in list){
        pointfloat.add(i.toFloat())
        loopadd = loopadd + i.toFloat()
        cumulative.add(loopadd)
    }
    for (i in 1..list.size){
        days.add(i.toFloat())
    }
    for (i in 0..list.size -1 ){
        entries.add(Entry(days[i], cumulative[i]))
    }
    return entries
}
fun cumulativelast(list: MutableList<String>): Int{
    var tosend = 0
    for (i in list){
        tosend = tosend + i.toInt()
    }
    return tosend
}