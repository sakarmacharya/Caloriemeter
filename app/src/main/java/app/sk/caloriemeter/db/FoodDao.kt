package app.sk.caloriemeter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.sql.Date


@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: Food)

    @Query("SELECT * FROM food")
    fun getAllFood(): List<Food>

    @Query("SELECT SUM(calorie) as Total FROM food where created_at / 86400000 =:todayDate / 86400000")
    fun getCalorie(todayDate: Long): Int

    @Query("SELECT * FROM food WHERE food_type IN (:foodType)")
    fun getFoodByName(foodType: String): List<Food>

    //all data
    @Query("SELECT * from Food Order by created_at")
    fun fetchByDate(): List<Food>

    @Query("SELECT * FROM Food WHERE calorie BETWEEN :from AND :to")
    fun calorieBetween(from: Date, to: Date): List<User>

    //daily food
    @Query("select * from Food where created_at / 86400000 =:todayDate / 86400000 ")
    fun getTodaysFood(todayDate: Long): List<Food>

    //weekly food
    @Query("SELECT * FROM Food WHERE created_at >= (1000 * strftime('%s', datetime('now', '-7 day')))")
    fun getWeekFood(): List<Food>

    //get monthly food
    @Query("SELECT * FROM Food WHERE datetime(created_at/1000,'unixepoch','start of month') = datetime(:dateOfMonth/1000,'unixepoch','start of month')")
    fun getDataMonth(dateOfMonth: Long): List<Food>

    //Query to insert data into table
// Insert into food (id, food_type,food_name,food_in_gm,calorie,fat, carbohydrate,protein,created_at) values(null, "Breakfast", "Cheese", 10,555,10,10,10,1676175910000)

//    // Week:
//    @Query("SELECT * FROM expense_table WHERE week = strftime('%W', 'now')")
//    LiveData<List<Expense>> getExpensesWeek();
//
//    //  Month:
//    @Query("SELECT * FROM expense_table WHERE month = strftime('%m', 'now')")
//    LiveData<List<Expense>> getExpensesMonth();
}