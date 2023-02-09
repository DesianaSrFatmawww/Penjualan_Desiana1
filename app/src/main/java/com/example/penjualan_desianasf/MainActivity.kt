package com.example.penjualan_desianasf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_desianasf.room.DbPenjualan
import com.example.penjualan_desianasf.room.TbBarang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { DbPenjualan(this) }
    lateinit var brgAdapter: AdapterBarang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListener()
        setUpRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val penjualan = db.tbBrgDao().getTbBarang()
            Log.d("MainActivity", "dbResponse:$penjualan")
            withContext(Dispatchers.Main) {
                brgAdapter.setData(penjualan)
            }
        }
    }

    fun setUpListener() {
        btCreate.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(idBarang: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", idBarang)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setUpRecyclerView() {
        brgAdapter = AdapterBarang(arrayListOf(), object : AdapterBarang.OnAdapterListener {
            override fun OnClick(barang: TbBarang) {
                intentEdit(barang.id_brg, Constant.TYPE_READ)

            }

            override fun OnUpdate(barang: TbBarang) {
                intentEdit(barang.id_brg, Constant.TYPE_UPDATE)
            }

            override fun OnDelete(barang: TbBarang) {
                deleteDialog(barang)
            }
        })
        listBrg.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = brgAdapter
        }
    }

    fun deleteDialog(barang: TbBarang) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${barang.nm_brg}")
            setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Yes") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbBrgDao().deleteTbBarang(barang)
                    dialogInterface.dismiss()
                    loadData()
                    }
                }
            }
            alertDialog.show()
        }
    }
