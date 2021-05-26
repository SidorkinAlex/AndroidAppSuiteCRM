package app.suiteCRM

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import app.suiteCRM.settings.PreferenceConstant

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_settings), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val hasRequireParams = checkPreference()
        if(hasRequireParams) {
            checkNetvorkActive()
            getModuleList()
        } else {
            activeSettingsPage(navController)
        }
    }

    private fun checkNetvorkActive() {
       
    }

    private fun getModuleList() {

    }

    private fun activeSettingsPage(navController: NavController) {
        val graph = navController.graph
        graph.startDestination = R.id.nav_settings
        navController.setGraph(graph)
    }

    private fun checkPreference(): Boolean {
        val sharedPreferences: SharedPreferences = this.getPreferences(Context.MODE_PRIVATE)!!
        val url = sharedPreferences.getString(PreferenceConstant.URL,"")
        val login = sharedPreferences.getString(PreferenceConstant.LOGIN,"")
        val pass = sharedPreferences.getString(PreferenceConstant.PASSWORD,"")

        if(!url.equals("") && !login.equals("") && !pass.equals("")){
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}