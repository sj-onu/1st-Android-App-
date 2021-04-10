package uap.cse43.hydroauto
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import kotlinx.android.synthetic.main.activity_main.*

class NotificationActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId ="uap.cse43.hydroauto"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



        btn_notify.setOnClickListener {
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val contentView = RemoteViews(packageName,R.layout.activity_notification)
            contentView.setTextViewText(R.id.tv_title,"Hydroponic")
            contentView.setTextViewText(R.id.tv_content,"Text Notificaton")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId,description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor=Color.GREEN
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this,channelId)
                        .setContent(contentView)
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                        .setContentIntent(pendingIntent)
            }else{
                builder = Notification.Builder(this)
                        .setContent(contentView)
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                        .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())
        }
    }
}