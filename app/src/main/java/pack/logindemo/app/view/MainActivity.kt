package pack.logindemo.app.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pack.logindemo.app.R
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, FrameFragment()).commit()

    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.frameLayout)) {
            is LoginFragment, is SignUpFragment, is DisplayPageFragment -> {
                supportFragmentManager.popBackStack()
            }
            is ProfilePageFragment -> {
                if (doubleBackToExitPressedOnce) {
                    System.exit(1)
                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, "Press back again to exit..", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed(
                    { doubleBackToExitPressedOnce = false },
                    2000
                )
            }
            is FrameFragment -> {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, "Press back again to exit..", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed(
                    { doubleBackToExitPressedOnce = false },
                    2000
                )
            }
        }
    }

}