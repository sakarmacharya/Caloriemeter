package app.sk.caloriemeter.ui.addfood

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.sk.caloriemeter.ui.FoodDetailActivity
import app.sk.caloriemeter.R
import app.sk.caloriemeter.databinding.FragmentAddFoodBinding
import app.sk.caloriemeter.db.AppDatabase
import app.sk.caloriemeter.db.Food
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddFoodFragment : Fragment() {

    private var _binding: FragmentAddFoodBinding? = null
    private val binding get() = _binding!!
    lateinit var appDb: AppDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this).get(AddFoodViewModel::class.java)

        appDb = AppDatabase.getDatabase(requireContext())

        _binding = FragmentAddFoodBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val cardBreakFast: CardView = root.findViewById(R.id.card_bf)
        val cardLunch: CardView = root.findViewById(R.id.card_lunch)
        val cardSnacks: CardView = root.findViewById(R.id.card_snacks)
        val cardDinner: CardView = root.findViewById(R.id.card_dinner)


        val btnBreakfast: Button = root.findViewById(R.id.btn_break_fast)
        val btnLunch: Button = root.findViewById(R.id.btn_lunch)
        val btnSnacks: Button = root.findViewById(R.id.btn_snacks)
        val btnDinner: Button = root.findViewById(R.id.btn_dinner)

        cardBreakFast.setOnClickListener {
            val intent = Intent(context, FoodDetailActivity::class.java)
            intent.putExtra(getString(R.string.food_type), "Breakfast")
            startActivity(intent)

        }

        cardLunch.setOnClickListener {
            val intent = Intent(context, FoodDetailActivity::class.java)
            intent.putExtra(getString(R.string.food_type), "Lunch")
            startActivity(intent)

        }

        cardSnacks.setOnClickListener {
            val intent = Intent(context, FoodDetailActivity::class.java)
            intent.putExtra(getString(R.string.food_type), "Snacks")
            startActivity(intent)

        }

        cardDinner.setOnClickListener {
            val intent = Intent(context, FoodDetailActivity::class.java)
            intent.putExtra(getString(R.string.food_type), "Dinner")
            startActivity(intent)

        }

        btnBreakfast.setOnClickListener {
            showBreakFastBottomSheet("Breakfast")
        }

        btnLunch.setOnClickListener {
            showBreakFastBottomSheet("Lunch")
        }

        btnSnacks.setOnClickListener {
            showBreakFastBottomSheet("Snacks")
        }

        btnDinner.setOnClickListener {
            showBreakFastBottomSheet("Dinner")
        }
        return root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showBreakFastBottomSheet(foodType: String) {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.breakfast_bottom_sheet)
        // val btnEdit= dialog.findViewById<RelativeLayout>(R.id.rl_edit)
        val etFoodName = dialog.findViewById<EditText>(R.id.et_food_name)
        val etFoodInGram = dialog.findViewById<EditText>(R.id.et_food_in_gram)
        val etCalorie = dialog.findViewById<EditText>(R.id.et_calorie)
        val etFat = dialog.findViewById<EditText>(R.id.et_fat)
        val etCarbohydrate = dialog.findViewById<EditText>(R.id.et_carbo)
        val etProtein = dialog.findViewById<EditText>(R.id.et_protein)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)

        btnSave?.setOnClickListener(View.OnClickListener {
            var hasError: Boolean = false;

            val foodName = etFoodName?.text.toString()
            val foodInGram = etFoodInGram?.text.toString()
            val calorie = etCalorie?.text.toString()
            val fat = etFat?.text.toString()
            val carbo = etCarbohydrate?.text.toString()
            val protein = etProtein?.text.toString()
            val createdAt = System.currentTimeMillis()


            if (foodName.isEmpty()) {
                etFoodName?.setError(getString(R.string.required))
                hasError = true
            }

            if (foodInGram.isEmpty()) {
                etFoodInGram?.setError(getString(R.string.required))
                hasError = true
            }

            if (calorie.isEmpty()) {
                etCalorie?.setError(getString(R.string.required))
                hasError = true
            }

            if (fat.isEmpty()) {
                etFat?.setError(getString(R.string.required))
                hasError = true
            }

            if (carbo.isEmpty()) {
                etCarbohydrate?.setError(getString(R.string.required))
                hasError = true
            }

            if (protein.isEmpty()) {
                etProtein?.setError(getString(R.string.required))
                hasError = true
            }

            if (!hasError) {
                val food = Food(
                    null,
                    foodType,
                    foodName,
                    foodInGram,
                    calorie.toInt(),
                    fat,
                    carbo,
                    protein,
                    createdAt
                )

                GlobalScope.launch {
                    appDb.foodDao().insert(food)
                    etFoodName?.setText("")
                    etFoodInGram?.setText("")
                    etCalorie?.setText("")
                    etFat?.setText("")
                    etCarbohydrate?.setText("")
                    etProtein?.setText("")
                }

                Toast.makeText(context, foodType + " added successfully", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        })


        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
