package com.example.penjualan_desianasf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_desianasf.room.DbPenjualan
import com.example.penjualan_desianasf.room.TbBarang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { DbPenjualan(this) }
    private var idBarang :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setUpListener()
        setUpView()
        idBarang = intent.getIntExtra("intent_id",0)
        Toast.makeText(this,idBarang.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setUpListener() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btSave.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBrgDao().addTbBarang(
                    TbBarang(etIdBrg.text.toString().toInt(),
                        etNmaBrg.text.toString(),
                        etHrgBrg.text.toString().toInt(),
                        etStok.text.toString().toInt())
                )
                finish()
            }
        }
        btUpdate.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
                db.tbBrgDao().updateTbBarang(
                    TbBarang(idBarang,
                        etNmaBrg.text.toString(),
                        etHrgBrg.text.toString().toInt(),
                        etStok.text.toString().toInt())
                )
                finish()
            }
        }
    }

    fun setUpView() {
        val intentType = intent.getIntExtra("intent_type",0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                btUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btSave.visibility = View.GONE
                btUpdate.visibility = View.GONE
                tampilData()
            }
            Constant.TYPE_UPDATE -> {
                btSave.visibility = View.GONE
                tampilData()
            }
        }
    }

    private fun tampilData() {
        idBarang = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val barangId = db.tbBrgDao().tampilTbBarang(idBarang)[0]
            val id : String = barangId.id_brg.toString()
            val harga : String = barangId.hrg_brg.toString()
            val stok : String = barangId.stok.toString()
            etIdBrg.setText(id)
            etNmaBrg.setText(barangId.nm_brg)
            etHrgBrg.setText(harga)
            etStok.setText(stok)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}