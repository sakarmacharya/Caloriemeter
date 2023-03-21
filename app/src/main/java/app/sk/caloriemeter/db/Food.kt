package app.sk.caloriemeter.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food")
data class Food(

    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "food_type") val foodType: String?,
    @ColumnInfo(name = "food_name") val foodName: String?,
    @ColumnInfo(name = "food_in_gm") val goodWeight: String?,
    @ColumnInfo(name = "calorie") val calorie: Int?,
    @ColumnInfo(name = "fat") val fat: String?,
    @ColumnInfo(name = "carbohydrate") val carbohydrate: String?,
    @ColumnInfo(name = "protein") val protein: String?,
    @ColumnInfo(name = "created_at") var createdAt: Long
)

