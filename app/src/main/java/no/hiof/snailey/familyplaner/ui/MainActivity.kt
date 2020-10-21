package no.hiof.snailey.familyplaner.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.ui.calendar.CalendarFragment
import no.hiof.snailey.familyplaner.ui.shopping.ShoppingsFragment
import no.hiof.snailey.familyplaner.ui.todo.ToDosFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //bottomnavigation
        setupNavigation()


        //mainnavigation
        setSupportActionBar(findViewById(R.id.toolbar))

        navigation_view.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.home -> {
                    true
                }
            }
            true
        }

        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //main navigation

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //bottomnavigation
    private fun setupNavigation() {


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_calendar -> {
                    switchToCalendarFragment()
                    true
                }
                R.id.menu_shoppings -> {
                    switchToShoppingFragment()
                    true
                }
                R.id.menu_toDos -> {
                    switchToToDoFragment()
                    true
                }
                else -> true
            }
        }
    }

    fun switchToToDoFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, ToDosFragment()).commit()
    }

    fun switchToShoppingFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, ShoppingsFragment()).commit()
    }

    fun switchToCalendarFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, CalendarFragment()).commit()
    }
}