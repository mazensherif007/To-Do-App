package com.example.todo.app.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Task (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id:Int? = null,
    @ColumnInfo
    var title:String? = null,
    @ColumnInfo(index = true)
    var description:String? = null,
    @ColumnInfo
    var date:Long? = null,
    @ColumnInfo
    var time:Long? = null,
    @ColumnInfo
    var status:Boolean = false
): Parcelable