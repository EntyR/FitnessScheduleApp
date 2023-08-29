package health.fit.bodyz.app.view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import health.fit.bodyz.app.R
import java.util.concurrent.TimeUnit

class TimePicker(ctx: Context, attributeSet: AttributeSet): FrameLayout(ctx, attributeSet) {


    lateinit var hour: DateNumberPicker
    lateinit var minutes: DateNumberPicker
    lateinit var secundos: DateNumberPicker

    init {
        inflate(context, R.layout.time_picker, this);
        hour = findViewById<DateNumberPicker>(R.id.tpHour).apply {
            maxValue = 23
        }
        minutes = findViewById<DateNumberPicker>(R.id.tpMinutes).apply {
            maxValue = 59
        }
        secundos = findViewById<DateNumberPicker>(R.id.tpSeconds).apply {
            maxValue = 59

        }
    }
    companion object {
        fun convertStringToMillis(date: String): Long {

            val subFate = date.split(':')

            val hour: Long = TimeUnit.HOURS.toMillis(subFate[0].toLong())
            val minutes: Long = TimeUnit.MINUTES.toMillis(subFate[1].toLong())
            val seconds: Long = TimeUnit.SECONDS.toMillis(subFate[2].toLong())
            return hour+minutes+seconds
        }


        fun getMinusSecondValue(date: Long): String {
            var leftMilis = date
            val hours = TimeUnit.MILLISECONDS.toHours(leftMilis)
            leftMilis -=TimeUnit.HOURS.toMillis(hours)

            val minutes = TimeUnit.MILLISECONDS.toMinutes(leftMilis)
            leftMilis -=TimeUnit.MINUTES.toMillis(minutes)

            val secons = TimeUnit.MILLISECONDS.toSeconds(leftMilis)
            leftMilis -=TimeUnit.SECONDS.toMillis(secons)

            return "${String.format("%02d",hours)}:${String.format("%02d", minutes)}:${String.format("%02d", secons)}"
        }
    }

    public fun getTime(): String {
        return "${String.format("%02d", hour.value)}:" +
                String.format("%02d", minutes.value) +
                ":${String.format("%02d", secundos.value)}"
    }

    fun setTime(date: String) {
        val subFate = date.split(':')
        hour.value = subFate[0].toInt()
        minutes.value = subFate[1].toInt()
        secundos.value = subFate[2].toInt()
    }
}