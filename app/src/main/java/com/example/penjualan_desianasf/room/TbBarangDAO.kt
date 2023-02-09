package com.example.penjualan_desianasf.room

import androidx.room.*

@Dao
interface TbBarangDAO {

    @Insert
    fun addTbBarang (tbBrg: TbBarang)

    @Update
    fun updateTbBarang (tbBrg: TbBarang)

    @Delete
    fun deleteTbBarang (tbBrg: TbBarang)

    @Query("SELECT * FROM tbbarang")
    fun getTbBarang():List<TbBarang>

    @Query("SELECT * FROM tbbarang WHERE id_brg =:brgId")
    fun tampilTbBarang(brgId: Int):List<TbBarang>
}