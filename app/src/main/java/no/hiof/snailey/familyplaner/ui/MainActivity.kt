package no.hiof.snailey.familyplaner.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.data.NODE_USER
import no.hiof.snailey.familyplaner.ui.calendar.CalendarFragment
import no.hiof.snailey.familyplaner.ui.shopping.ShoppingsFragment
import no.hiof.snailey.familyplaner.ui.todo.ToDosFragment


class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private var userName: TextView? = null
    private var userEmail: TextView? = null
    private var userFamily: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbUser = FirebaseDatabase.getInstance().getReference(NODE_USER)

        auth = FirebaseAuth.getInstance();

    //bottom navigation
            setupNavigation()

    //main navigation
            setSupportActionBar(findViewById(R.id.toolbar))

            navigation_view.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        true
                    }
                    R.id.logout_btn -> {
                        logout()
                    }
                }
                true
            }

            val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
            drawer.addDrawerListener(drawerToggle)
            drawerToggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

    //Navigation header
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        var header: View = navigationView.inflateHeaderView(R.layout.nav_header_main)

        userName = header.findViewById<TextView>(R.id.header_name) as TextView
        userEmail = header.findViewById<TextView>(R.id.header_email) as TextView
        userFamily = header.findViewById<TextView>(R.id.header_family) as TextView


        //Get auth uid
        val user = auth?.currentUser
        val userId = user!!.uid

        //Get Firebase User Data...


        //Put user.data
        userName!!.setText("name").toString()
        userEmail!!.setText("email").toString()
        userFamily!!.setText("family").toString()
        
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

    private fun switchToToDoFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, ToDosFragment()).commit()
    }

    private fun switchToShoppingFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, ShoppingsFragment()).commit()
    }

    private fun switchToCalendarFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, CalendarFragment()).commit()
    }

    private fun logout() {

        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }
}