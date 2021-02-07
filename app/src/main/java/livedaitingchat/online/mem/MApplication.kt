package livedaitingchat.online.mem

import android.app.Application
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val config =
            YandexMetricaConfig.newConfigBuilder("380d84a4-a157-475e-8799-f519d2660376").build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)


        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
    }

    private fun iniica() {

    }

    private fun initOneCoig() {

    }

}