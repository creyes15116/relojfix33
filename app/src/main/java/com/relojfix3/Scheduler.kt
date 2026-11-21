package com.relojfix3

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

object Scheduler {
    const val ACTION_ADD = "ADD"
    const val ACTION_SUB = "SUB"

    fun scheduleAll(ctx: Context) {
        schedule(ctx, 7, 57, ACTION_ADD, 1)
        schedule(ctx, 8, 7, ACTION_SUB, 2)
    }

    fun schedule(ctx: Context, hour: Int, min: Int, action: String, code: Int) {
        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (!am.canScheduleExactAlarms()) return

        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) add(Calendar.DAY_OF_YEAR, 1)
        }
        val intent = Intent(ctx, AlarmReceiver::class.java).putExtra("action", action)
        val pi = PendingIntent.getBroadcast(
            ctx, code, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
    }
}
