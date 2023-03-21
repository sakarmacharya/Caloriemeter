package app.sk.caloriemeter.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.sk.caloriemeter.databinding.FragmentHomeBinding
import app.sk.caloriemeter.db.AppDatabase
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var tvComment: TextView
    private lateinit var lottieHappy: LottieAnimationView
    private lateinit var lottieSad: LottieAnimationView
    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var appDb: AppDatabase

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        appDb = AppDatabase.getDatabase(requireContext())

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
        val tvCalorieToday: TextView = binding.tvCalorieToday
        tvComment = binding.tvComment
        lottieHappy = binding.lottieHappy
        lottieSad = binding.lottieSad



        homeViewModel.text.observe(viewLifecycleOwner) {
            // textView.text = it
        }


        GlobalScope.launch {

            val dailyTarget = appDb.userDao().getUser().dailyTarget
            val calSum = appDb.foodDao().getCalorie(System.currentTimeMillis())
            val res = dailyTarget?.minus(calSum)

            if (calSum > dailyTarget!!) {
                lottieHappy.visibility = View.INVISIBLE
                lottieSad.visibility = View.VISIBLE
                tvCalorieToday.setTextColor(Color.RED)
                tvCalorieToday.setText(dailyTarget.toString() + " - " + calSum + " = " + res + " over flow")
                tvComment.setText("Oops! you've gone over the limit")
                tvComment.setTextColor(Color.RED)
            } else {
                lottieHappy.visibility = View.VISIBLE
                lottieSad.visibility = View.INVISIBLE
                tvCalorieToday.setText(dailyTarget.toString() + " - " + calSum + " = " + res + " remaining")
                tvComment.setText("You're doing great, Keep it up")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}