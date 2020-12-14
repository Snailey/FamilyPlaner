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
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.nav_header_main.*
import no.hiof.snailey.familyplaner.Global
import no.hiof.snailey.familyplaner.R
import no.hiof.snailey.familyplaner.ui.auth.LogInActivity
import no.hiof.snailey.familyplaner.ui.calendar.CalendarFragment
import no.hiof.snailey.familyplaner.ui.profile.ProfileFragment
import no.hiof.snailey.familyplaner.ui.shopping.ShoppingsFragment
import no.hiof.snailey.familyplaner.ui.todo.ToDosFragment


class MainActivity : AppCompatActivity() {

    private var userName: TextView? = null
    private var userFamily: TextView? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("user")
        mAuth = FirebaseAuth.getInstance()

        //bottom navigation
        setupNavigation()

        //main navigation
        setSupportActionBar(findViewById(R.id.toolbar))

        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    true
                }
                R.id.profil -> {
                    switchToProfileFragment()
                    drawer.closeDrawer(GravityCompat.START)
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
        userFamily = header.findViewById<TextView>(R.id.header_family) as TextView

        val uid = mDatabaseReference!!.child(mAuth!!.currentUser!!.uid)

        uid.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName!!.text = snapshot.child("name").value as String
                userFamily!!.text = snapshot.child("family").value as String
                Global.setFamilyName = snapshot.child("family").value as String
                val media = snapshot.child("picture").value as String
                if (media !== null) {
                    Glide.with(this@MainActivity)
                        .load(media)
                        .into(header_imageview!!)
                } else {
                    // userImg!!.setImageResource(R.drawable.ic_launcher_background)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
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
        manager.beginTransaction().replace(R.id.fragment, ToDosFragment()).addToBackStack(null)
            .commit()
    }

    private fun switchToShoppingFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, ShoppingsFragment()).addToBackStack(null)
            .commit()
    }

    private fun switchToCalendarFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, CalendarFragment()).addToBackStack(null)
            .commit()
    }

    private fun switchToProfileFragment() {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment, ProfileFragment()).addToBackStack(null)
            .commit()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LogInActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}