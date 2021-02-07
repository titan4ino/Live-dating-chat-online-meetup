package test_livedaitingchat.online.mem

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    enum class UIState { FIRST, SECOND, THIRD, LOADING }

    private val currentState = MutableLiveData<UIState>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentState.postValue(UIState.FIRST)

        currentState.observe(this) { newState ->
            when (newState) {
                UIState.FIRST -> {
                    first.setVisible(true)
                    second.setVisible(false)
                    third.setVisible(false)
                    progress.setVisible(false)
                }
                UIState.SECOND -> {
                    first.setVisible(false)
                    second.setVisible(true)
                    third.setVisible(false)
                    progress.setVisible(false)
                }
                UIState.THIRD -> {
                    first.setVisible(false)
                    second.setVisible(false)
                    third.setVisible(true)
                    progress.setVisible(false)
                }
                else -> {
                    first.setVisible(false)
                    second.setVisible(false)
                    third.setVisible(false)
                    progress.setVisible(true)
                }
            }
        }

        no.setOnClickListener {
            showAlert("You should be 18+ to proceed.")
        }
        yes.setOnClickListener {
            currentState.postValue(UIState.SECOND)
        }
        no2.setOnClickListener {
            currentState.postValue(UIState.THIRD)
        }
        yes2.setOnClickListener {
            currentState.postValue(UIState.THIRD)
        }
        terms.paintFlags = terms.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        terms.setOnClickListener {
            startActivity(Intent(this, TermsActivity::class.java))
        }
        signupButton.setOnClickListener {
            currentState.postValue(UIState.LOADING)
            Handler().postDelayed({
                currentState.postValue(UIState.THIRD)
                showAlert("Server is unavailable")
            }, 1000)
        }
        loginButton.setOnClickListener {
            currentState.postValue(UIState.LOADING)
            Handler().postDelayed({
                currentState.postValue(UIState.THIRD)
                showAlert("Server is unavailable")
            }, 1000)
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target) || target == null) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun showAlert(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(android.R.string.yes, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun View.setVisible(visible : Boolean) {
        if (visible) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        when (currentState.value) {
            UIState.FIRST -> {
                super.onBackPressed()
            }
            UIState.SECOND -> {
                currentState.postValue(UIState.FIRST)
            }
            UIState.THIRD -> {
                currentState.postValue(UIState.SECOND)
            }
            else -> {
                //nothing
            }
        }
    }
}