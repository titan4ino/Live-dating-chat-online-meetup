package livedaitingchat.online.mem

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_terms.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException


class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        val largeTextString = getStringFromRawRes(R.raw.terms)
        if (largeTextString != null) {  //null check is optional
            termsText.text = largeTextString
        } else {
            termsText.text = "Sorry\nSomething went wrong"
        }

        back.setOnClickListener {
            finish()
        }
    }

    @Nullable
    private fun getStringFromRawRes(rawRes: Int): String? {
        val inputStream: InputStream
        inputStream = try {
            resources.openRawResource(rawRes)
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
            return null
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        try {
            while (inputStream.read(buffer).also { length = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, length)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                inputStream.close()
                byteArrayOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val resultString: String
        resultString = try {
            byteArrayOutputStream.toString("UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }
        return resultString
    }
}