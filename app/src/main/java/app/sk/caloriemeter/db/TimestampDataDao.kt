package app.sk.caloriemeter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
abstract class TimestampDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(food: Food)

    fun insertWithTimestamp(food: Food) {
        insert(food.apply {
            createdAt = System.currentTimeMillis()
        }
        )
    }
}