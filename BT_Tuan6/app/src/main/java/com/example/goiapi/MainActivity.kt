package com.example.goiapi

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvDes: TextView
    private lateinit var tvPrice: TextView
    private lateinit var imgProduct: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ view
        tvName = findViewById(R.id.tvName)
        tvDes = findViewById(R.id.tvDes)
        tvPrice = findViewById(R.id.tvPrice)
        imgProduct = findViewById(R.id.imgProduct)

        fetchProducts()
    }

    private fun fetchProducts() {
        val call = RetrofitClient.apiService.getProduct()
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    Log.d("API", "${product?.name} - ${product?.price}")

                    product?.let {
                        tvName.text = it.name
                        tvDes.text = it.des
                        tvPrice.text = "$${it.price}"

                        Glide.with(this@MainActivity)
                            .load(it.imgURL)
                            .into(imgProduct)
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
