package com.example.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.broadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelId = "TEST_NOTIF"
    private val notifId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }


        val intent = Intent(this@MainActivity, AplikasiActivity::class.java)
        intent.putExtra("MESSAGE", "Notification clicked!") // Pesan yang akan ditampilkan saat notifikasi diklik
        val pendingIntent = (PendingIntent.getBroadcast(this@MainActivity, 0, intent, flag))

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        with(binding) {
            btnNotif.setOnClickListener {
                val builder = NotificationCompat.Builder(this@MainActivity, channelId)
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.askar2))
                    .setContentTitle("Notification Test")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(0, "baca notip", pendingIntent)
                    .setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.askar2))
                            .bigLargeIcon(null) // Optional, jika ingin mengganti ikon besar
                            .setSummaryText("halo brodiii jjsjaksuodwde")
                    )



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notifChannel = NotificationChannel(
                        channelId,
                        "Notifku",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    with(notifManager) {
                        createNotificationChannel(notifChannel)
                        notify(notifId, builder.build())
                    }
                } else {
                    notifManager.notify(notifId, builder.build())
                }
            }
        }
    }
}
