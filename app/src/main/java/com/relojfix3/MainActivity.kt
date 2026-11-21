package com.relojfix3

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        status = TextView(this).apply {
            textSize = 14f
            gravity = android.view.Gravity.CENTER
            setPadding(32, 32, 32, 32)
        }
        setContentView(status)
    }

    override fun onResume() {
        super.onResume()
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        when {
            !Settings.System.canWrite(this) -> {
                status.text = "Concede el permiso de modificar ajustes del sistema"
                startActivity(
                    Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:$packageName"))
                )
            }
            !am.canScheduleExactAlarms() -> {
                status.text = "Concede el permiso de alarmas exactas"
                startActivity(
                    Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:$packageName"))
                )
            }
            else -> {
                Scheduler.scheduleAll(this)
                status.text = "Reloj Fix3 activo\n7:57 +10 min\n8:07 -10 min"
            }
        }
    }
}
