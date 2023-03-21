package app.sk.caloriemeter.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.sk.caloriemeter.databinding.FragmentChartBinding
import app.sk.caloriemeter.db.AppDatabase
import app.sk.caloriemeter.db.Food
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ChartFragment : Fragment() {

    private lateinit var todaysFood: List<Food>
    private lateinit var monthFood: List<Food>
    private lateinit var weekFood: List<Food>
    private var _binding: FragmentChartBinding? = null

    private val binding get() = _binding!!

    lateinit var appDb: AppDatabase

    // variable for our bar data.
    var barData: BarData? = null
    var barDataWeek: BarData? = null
    var barDataMonth: BarData? = null

    // variable for our bar data set.
    // array list for storing entries.
    var barEntriesArrayList: MutableList<BarEntry>? = ArrayList<BarEntry>()
    var barEntriesWeek: MutableList<BarEntry>? = ArrayList<BarEntry>()
    var barEntriesMonth: MutableList<BarEntry>? = ArrayList<BarEntry>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this).get(ChartViewModel::class.java)

        appDb = AppDatabase.getDatabase(requireContext())

        _binding = FragmentChartBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val barChart: BarChart = binding.barChartDay
        val barChartWeek: BarChart = binding.barChartWeek
        val barMonth: BarChart = binding.barChartMonth

        val btnToday: Button = binding.btnToday
        val btnWeek: Button = binding.btnWeek
        val btnMonth: Button = binding.btnMonth


        GlobalScope.launch {

            var monthly = appDb.foodDao().fetchByDate()
          //  Log.e("month", monthly.joinToString(" "))

            if (!monthly.size.equals(0)) {
                var myDate2 = convertLongToTime(monthly.get(0).createdAt)
               // Log.e("xox2", myDate2)
            }

            //working
            todaysFood = appDb.foodDao().getTodaysFood(System.currentTimeMillis())
          //  Log.e("Food Today", todaysFood.joinToString(""));

            weekFood = appDb.foodDao().getWeekFood()
          //  Log.e("week food", weekFood.joinToString(""));


            monthFood = appDb.foodDao().getDataMonth(System.currentTimeMillis())
          //  Log.e("getDataMonth food", monthFood.joinToString(""));

            getBarEntries(todaysFood)
            getBarEntriesWeek(weekFood)
            getBarEntriesMonth(monthFood)

            btnToday.setOnClickListener(View.OnClickListener {
                barChart.visibility = View.VISIBLE
                barChartWeek.visibility = View.INVISIBLE
                barMonth.visibility = View.INVISIBLE

            })

            btnWeek.setOnClickListener(View.OnClickListener {
                barChart.visibility = View.INVISIBLE
                barChartWeek.visibility = View.VISIBLE
                barMonth.visibility = View.INVISIBLE

            })

            btnMonth.setOnClickListener(View.OnClickListener {
                barChart.visibility = View.INVISIBLE
                barChartWeek.visibility = View.INVISIBLE
                barMonth.visibility = View.VISIBLE
            })


            // creating a new bar data set.
            val barDataSet = BarDataSet(barEntriesArrayList, "Daily Calorie")
            val barDataSetWeek = BarDataSet(barEntriesWeek, "Weekly Calorie")
            val barDataSetMonth = BarDataSet(barEntriesMonth, "Monthly Calorie")

            // creating a new bar data and passing our bar data set.
            barData = BarData(BarDataSet(barEntriesArrayList, "Daily Calorie"))
            barDataWeek = BarData(BarDataSet(barEntriesWeek, "Weekly Calorie"))
            barDataMonth = BarData(BarDataSet(barEntriesMonth, "Monthly Calorie"))

            // below line is to set data to our bar chart.
            barChart.data = barData
            barChartWeek.data = barDataWeek
            barMonth.data = barDataMonth

            // adding color to our bar data set.
            barDataSet!!.setColors(Color.rgb(60,179,133))
            barDataSetWeek!!.setColors(Color.rgb(153,204,255))
            barDataSetMonth!!.setColors(Color.LTGRAY)

            // setting text color.
            barDataSet!!.valueTextColor = Color.BLACK
            barDataSetWeek!!.valueTextColor = Color.BLACK
            barDataSetMonth!!.valueTextColor = Color.BLACK

            // setting text size
            barDataSet!!.valueTextSize = 12f
            barDataSetWeek!!.valueTextSize = 12f
            barDataSetMonth!!.valueTextSize = 12f
            barChart.description.isEnabled = false
            barChartWeek.description.isEnabled = false
            barMonth.description.isEnabled = false
        }
        return root
    }

    private fun getBarEntries(todayFood: List<Food>) {
        for (food in todayFood) {
            barEntriesArrayList?.add(
                    BarEntry(
                            food.id!!.toFloat(),
                            food.calorie!!.toFloat()
                    )
            )
        }
    }

    private fun getBarEntriesWeek(weekFood: List<Food>) {
        for (food in weekFood) {
            barEntriesWeek?.add(
                    BarEntry(
                            food.id!!.toFloat(),
                            food.calorie!!.toFloat()
                    )
            )
        }
    }


    private fun getBarEntriesMonth(monthFood: List<Food>) {
        for (food in monthFood) {
            barEntriesMonth?.add(
                    BarEntry(
                            food.id!!.toFloat(),
                            food.calorie!!.toFloat()
                    )
            )
        }
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
