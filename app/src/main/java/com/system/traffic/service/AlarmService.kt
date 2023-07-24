package com.system.traffic.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action
import androidx.fragment.app.activityViewModels
import com.system.traffic.R
import com.system.traffic.dataModel.BusArriveModel
import com.system.traffic.main.BusArriveActivity
import com.system.traffic.main.MainActivity
import com.system.traffic.main.viewModel.DataStoreViewModel
import com.system.traffic.main.viewModel.MainViewModel
import com.system.traffic.repository.NetWorkRepository
import com.system.traffic.main.BusArriveActivity.Companion.ACTION_START
import com.system.traffic.main.BusArriveActivity.Companion.ACTION_STOP
import kotlinx.coroutines.*


class AlarmService : Service() {

    private lateinit var busArriveList: ArrayList<BusArriveModel>

    private val netWorkRepository = NetWorkRepository()
    private val NOTIFICATION_ID = 100
    lateinit var job: Job

    private lateinit var busStopName: String
    private lateinit var busstop_id: String
    private lateinit var line_name: String
    private lateinit var remain_stop: String
    private lateinit var reloadTime: String
    private lateinit var arsId: String

    private var isSoonArrive: Boolean = false

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        stopSelf()
        isSoonArrive = false
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {

            ACTION_START -> {
                busStopName = intent.getStringExtra("busStopName")!!
                busstop_id = intent.getStringExtra("busstop_id")!!
                line_name = intent.getStringExtra("line_name")!!
                remain_stop = intent.getStringExtra("remain_stop")!!
                reloadTime = intent.getStringExtra("reloadTime")!!
                arsId = intent.getStringExtra("arsId")!!

                isSoonArrive = true

                job = CoroutineScope(Dispatchers.Default).launch {
                    while (isSoonArrive) {
                        startForeground(NOTIFICATION_ID, makeNotification())
                        delay(reloadTime.toLong())
                    }
                }
            }

            ACTION_STOP -> {
                try {
                    isSoonArrive = false
                    job.cancel()

                    //stopForeground(true)
                    stopSelf()
                } catch (e: Exception) {

                }
            }
        }

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private suspend fun makeNotification(): Notification {

        val result = getBusArriveInfo()

        var title = "$busStopName - $line_name"
        var content = ""
        var remainMin = ""
        var remainStop = ""

        if (result.isNotEmpty()) {
            for (i in result) {
                remainMin = i.remain_min!!
                remainStop = i.remain_stop!!
                if (i.arrive_flag == "1") {
                    // 1. Vibrator 객체를 얻어온 다음
                    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                    // 2. 진동 구현: 1000ms 동안 100의 강도로 울린다.
                    vibrator.vibrate(VibrationEffect.createOneShot(3000, 200))

                    content = "버스가 곧 도착할 예정입니다."
                } else {
                    content = "$remainMin 분 후 도착예정($remainStop 정거장 전)"
                }
            }

        } else {
            title = "버스정보가 없습니다"
            content = ""
        }

        val intent = Intent(this, BusArriveActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        intent.putExtra("ars_id", arsId)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_ONE_SHOT
        )

        val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.bus_arrive)
            .setContentTitle(title)
            .setContentText(content)
            //.addAction(R.drawable.alarm, "닫기", pendingIntent)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setAutoCancel(true)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        return builder.build()
    }

    suspend fun getBusArriveInfo(): ArrayList<BusArriveModel> {
        val result = netWorkRepository.getBusArriveList(busstop_id)

        busArriveList = ArrayList()

        if (result.row_count == "0") {


        } else {
            try {
                for (arrive in result.itemList) {
                    if (arrive.line_name == line_name) {
                        busArriveList.add(arrive)
                    }
                }
            } catch (e : java.lang.Exception){

            }

        }

        return busArriveList
    }
}