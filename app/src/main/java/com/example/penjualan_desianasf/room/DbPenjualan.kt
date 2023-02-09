package com.example.penjualan_desianasf.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TbBarang::class],
    version = 1
)
abstract class DbPenjualan : RoomDatabase(){
    abstract fun tbBrgDao():TbBarangDAO
    companion object{
        @Volatile private var instance:DbPenjualan?=null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?:buildDatabase(context).also{ instance=it}
        }
        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            DbPenjualan::class.java,
            "penjualan"
        ).build()
    }
}