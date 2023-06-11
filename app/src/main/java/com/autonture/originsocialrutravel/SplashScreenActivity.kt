package com.autonture.originsocialrutravel

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.autonture.originsocialrutravel.PartUI.Authorization.SecurityCodeAuthenticationActivity
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.ActivitySplashScreenBinding
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressCircular.apply {
            progressBarColor = Color.BLACK
            backgroundProgressBarColor = 656565;
        }
        val delayInMillis = 5000L
        val intervalMillis = 2000L
        fadeIn(intervalMillis)
        launchActivityAfterDelay(this, delayInMillis, SecurityCodeAuthenticationActivity::class.java, MainActivity::class.java)
    }

    private fun fadeIn(intervalInMillis: Long){
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                binding.splashTxt.visibility = View.VISIBLE
                val animationFadeIn = AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.fade_in)
                binding.splashTxt.startAnimation(animationFadeIn)
            }
        }
        timer.schedule(timerTask, 0, intervalInMillis)
    }

    fun launchActivityAfterDelay(context: Context, delayInMillis: Long, securityActivity: Class<*>, anonymActivity: Class<*>) {
        val handler = Handler()
        handler.postDelayed({
            val currentTime = System.currentTimeMillis()
            val scheduledTime = currentTime + delayInMillis

            if (scheduledTime > currentTime) {
                val isLogin = PrefsManager(this).isCode()
                if(isLogin){
                    val intent = Intent(context, securityActivity)
                    context.startActivity(intent)
                }
                else{
                    val intent = Intent(context, anonymActivity)
                    context.startActivity(intent)
                }

            }
            else{
                //запрос разрешения и проверка вытаскиваем данные

            }
        }, delayInMillis)
    }
}