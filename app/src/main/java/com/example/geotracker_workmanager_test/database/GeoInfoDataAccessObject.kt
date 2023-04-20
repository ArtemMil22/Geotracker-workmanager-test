package com.example.geotracker_workmanager_test.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface GeoInfoDataAccessObject {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllInfo(): List<GeoInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(info: GeoInfo): Single<Unit>

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()

}