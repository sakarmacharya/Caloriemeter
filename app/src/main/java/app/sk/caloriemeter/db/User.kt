package app.sk.caloriemeter.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") val firstName: String?,
    @ColumnInfo(name = "weight") val weight: String?,
    @ColumnInfo(name = "height") val height: String?,
    @ColumnInfo(name = "age") val age: String?,
    @ColumnInfo(name = "daily_target") val dailyTarget: Int?,

)