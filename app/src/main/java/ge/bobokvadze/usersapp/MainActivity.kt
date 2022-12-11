package ge.bobokvadze.usersapp

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.fragment.app.FragmentManager
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import ge.bobokvadze.usersapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currencyPages = arrayOf("MainFragment", "EditFragment")

        val adapter = CustomViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = currencyPages[position]
        }.attach()

        buildNotification().build()
    }

    private fun buildNotification(): NotificationCompat.Builder {
        val intent = Intent(this, this::class.java).apply {
            putExtra("MESSAGE", "Clicked")
        }
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else 0

        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, flag
        )
        val clickIntent = Intent(this, this::class.java)
        val clickPendingIntent = PendingIntent.getActivity(
            this, 1, clickIntent, flag
        )
        return NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(0, "icon", clickPendingIntent)
            .setContentIntent(pendingIntent)
    }
}

class CustomViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        private const val NUM_TABS = 2
    }

    override fun getItemCount() = NUM_TABS

    override fun createFragment(
        position: Int
    ) = if (position == 0) MainFragment() else EditFragment()
}