package app.sk.caloriemeter.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import app.sk.caloriemeter.R
import app.sk.caloriemeter.db.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FoodDetailActivity : AppCompatActivity() {

    lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        appDb = AppDatabase.getDatabase(this)

        val foodType: String = intent.getStringExtra(getString(R.string.food_type)).toString()
        Log.e("xxx intent", foodType)
        val tvTitle = findViewById<TextView>(R.id.tvFoodTitle)
        val imgFood = findViewById<ImageView>(R.id.imgFood)

        setFoodImage(foodType, imgFood)
        tvTitle.setText(foodType)

        val groupEmpty = findViewById<Group>(R.id.group_empty)
        val groupData = findViewById<Group>(R.id.group_data)


        GlobalScope.launch {
            val food = appDb.foodDao().getFoodByName(foodType)
            if (food.isEmpty()) {
                groupEmpty.visibility = View.VISIBLE
                groupData.visibility = View.INVISIBLE
            } else {
                groupEmpty.visibility = View.INVISIBLE
                groupData.visibility = View.VISIBLE
            }

            Log.e("xxx", food.joinToString(" "))

            val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
            for (f in food) {
                val view =
                    LayoutInflater.from(applicationContext).inflate(R.layout.food_layout, null);
                val foodName = view.findViewById<TextView>(R.id.tvName)
                val calorie = view.findViewById<TextView>(R.id.tvCalo)

                foodName.setText(f.foodName)
                calorie.setText(f.calorie.toString())

                linearLayout.addView(view)

            }
        }
    }

    private fun setFoodImage(foodType: String, imgFood: ImageView) {

        when (foodType) {
            "Breakfast" ->
                imgFood.setImageResource(R.drawable.breakfast)
            "Lunch" -> imgFood.setImageResource(R.drawable.lunch)
            "Snacks" -> imgFood.setImageResource(R.drawable.snakcs)
            "Dinner" -> imgFood.setImageResource(R.drawable.dinner)
        }
    }
}