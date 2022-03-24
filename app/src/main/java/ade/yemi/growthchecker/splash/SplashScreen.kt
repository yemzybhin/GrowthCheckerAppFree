package ade.yemi.growthchecker.splash

import ade.yemi.growthchecker.Data.DataStoreManager
import ade.yemi.growthchecker.Activities.MainActivity
import ade.yemi.growthchecker.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    private var NotFirsttime = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initdata()
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("firstopen", true)
        var splashimage = findViewById<ImageView>(R.id.iv_SplashImage)
        splashimage.alpha = 0f
        splashimage.animate().setDuration(1800).alpha(1f).withEndAction{
            if (NotFirsttime == 1)(
                    startActivity(intent)
            )
            else{
                startActivity(Intent(this, WelcomePage::class.java))
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
    private fun initdata(){
        lifecycleScope.launch {
            val pushresult = async {
                DataStoreManager.getInt(this@SplashScreen , "WelcomePageCheck")
            }

            NotFirsttime = pushresult.await()
        }
    }
    private fun savedata(){
        lifecycleScope.launch {
            DataStoreManager.saveInt(this@SplashScreen, "WelcomePageCheck", NotFirsttime)
        }
    }
}