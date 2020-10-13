package com.anwesh.uiprojects.newsweeparcfillview

/**
 * Created by anweshmishra on 14/10/20.
 */

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.content.Context

val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 0.02f
val sizeFactor : Float = 2.9f
val deg : Float = 90f
val totDeg : Float = 360f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")
val colors : Array<Int> = arrayOf(
        "#F44336",
        "#4CAF50",
        "#2196F3",
        "#FF9800",
        "#673AB7"
).map {
    Color.parseColor(it)
}.toTypedArray()

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.sin(this * Math.PI).toFloat()
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawNewSweepArcFill(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    save()
    translate(w / 2, h / 2)
    for (j in 0..1) {
        save()
        rotate(90f * j)
        drawLine(0f, 0f, -size * sf1, 0f, paint)
        restore()
    }
    for (j in 1..2) {
        val sfj : Float = sf.divideScale(j, parts)
        if (j == 1) {
            paint.style = Paint.Style.STROKE
        } else {
            paint.style = Paint.Style.FILL
        }
        drawArc(RectF(-size, -size, size, size), -deg, (totDeg - deg) * sfj, j % 2 == 0, paint)
    }
    restore()
}

fun Canvas.drawNSAFNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawNewSweepArcFill(scale, w, h, paint)
}
