package com.example.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.broadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelId = "TEST_NOTIF"
    private val notifId = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }

        val pendingIntent = PendingIntent.getBroadcast(this,0,intent,flag)

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        with(binding){
            btnNotif.setOnClickListener{
                val builder = NotificationCompat.Builder(this@MainActivity, channelId)
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setContentTitle("Notification Test")
//                    .setContentText("Hello World")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(0, "baca notip", pendingIntent)
                    .setStyle(NotificationCompat.BigTextStyle().
                    bigText("halo brodiii jjsjaksuodwde"))



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notifChannel = NotificationChannel(
                        channelId, // Id channel
                        "Notifku", // Nama channel notifikasi
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    with(notifManager) {
                        createNotificationChannel(notifChannel)
                        notify(notifId, builder.build())
                    }
                }
                else {
                    notifManager.notify(notifId, builder.build())
                }

            }
        }
    }
}