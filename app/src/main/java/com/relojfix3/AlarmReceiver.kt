package com.relojfix3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        val action = intent.getStringExtra("action") ?: return
        val prefs = ctx.getSharedPreferences("fix3", Context.MODE_PRIVATE)

        if (Settings.System.canWrite(ctx)) {
            val resolver = ctx.contentResolver
            val key = Settings.System.SCREEN_OFF_TIMEOUT
            if (action == Scheduler.ACTION_ADD) {
                val current = Settings.System.getInt(resolver, key, 300_000)
                prefs.edit().putInt("original", current).apply()
                Settings.System.putInt(resolver, key, current + 600_000) // +10 min
            } else {
                val original = prefs.getInt("original", 300_000)
                Settings.System.putInt(resolver, key, original) // -10 min
            }
        }

        // Reprograma para mañana
        if (action == Scheduler.ACTION_ADD) Scheduler.schedule(ctx, 7, 57, action, 1)
        else Scheduler.schedule(ctx, 8, 7, action, 2)
    }
}
