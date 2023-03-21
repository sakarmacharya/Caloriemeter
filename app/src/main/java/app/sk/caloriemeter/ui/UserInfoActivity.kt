package app.sk.caloriemeter.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import app.sk.caloriemeter.R
import app.sk.caloriemeter.db.AppDatabase
import app.sk.caloriemeter.db.User
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * This class takes user information and those
 * informations are saved in the database which
 * are later shown in profile page
 *
 */
class UserInfoActivity : AppCompatActivity() {

    lateinit var tvName: TextInputLayout
    lateinit var tvWeight: TextInputLayout
    lateinit var tvHeight: TextInputLayout
    lateinit var tvAge: TextInputLayout
    lateinit var tvDailyCalorie: TextInputLayout


    private lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val isFirstLaunch = sharedPref.getBoolean(getString(R.string.first_launch), true)

        if (!isFirstLaunch) {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        appDb = AppDatabase.getDatabase(this)

        val button: Button = findViewById(R.id.btn_continue)
        tvName = findViewById(R.id.tv_name)
        tvWeight = findViewById(R.id.tv_weight)
        tvHeight = findViewById(R.id.tv_height)
        tvAge = findViewById(R.id.tv_age)
        tvDailyCalorie = findViewById(R.id.tv_daily_cal)

        button.setOnClickListener {
            // Do something in response to button click
            // val intent = Intent(this, DashboardActivity::class.java)
            //startActivity(intent)
            saveUserInfo()

        }
    }

    private fun saveUserInfo() {

        var hasError: Boolean = false
        val name = tvName.editText?.text.toString()
        val weight = tvWeight.editText?.text.toString()
        val height = tvHeight.editText?.text.toString()
        val age = tvAge.editText?.text.toString()
        val calorie = tvDailyCalorie.editText?.text.toString()

        if (name.isEmpty()) {
            tvName.setError(getString(R.string.required))
            hasError = true
        }

        if (weight.isEmpty()) {
            tvWeight.setError(getString(R.string.required))
            hasError = true
        }

        if (height.isEmpty()) {
            tvHeight.setError(getString(R.string.required))
            hasError = true
        }

        if (age.isEmpty()) {
            tvAge.setError(getString(R.string.required))
            hasError = true
        }

        if (calorie.isEmpty()) {
            tvDailyCalorie.setError(getString(R.string.required))
            hasError = true
        }

        if (!hasError) {
            val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.first_launch), false)
                apply()
            }

            val userObj = User(null, name, weight, height, age, calorie.toInt())

            GlobalScope.launch(Dispatchers.IO) {
                appDb.userDao().insert(userObj)
            }


            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}