package com.emircan.expandablerecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

        val textAdapter = TextAdapter(list) { position, data ->
            Toast.makeText(this, "$position : $data", Toast.LENGTH_SHORT).show()
        }

        val imageAdapter = ImageAdapter {
            Toast.makeText(this, "item : $it", Toast.LENGTH_SHORT).show()
        }

        val defaultView = findViewById<ExpandableItemView>(R.id.defaultView)
        defaultView.setData(textAdapter)

        val gridView = findViewById<ExpandableItemView>(R.id.gridView)
        gridView.setData(textAdapter)

        val customGridView = findViewById<ExpandableItemView>(R.id.customGridView)
        customGridView.setData(textAdapter)

        val imageGridView = findViewById<ExpandableItemView>(R.id.imageGridView)
        imageGridView.setData(imageAdapter)

        val imageHorizontalView = findViewById<ExpandableItemView>(R.id.imageHorizontalView)
        imageHorizontalView.setData(imageAdapter)

        val imageVerticalView = findViewById<ExpandableItemView>(R.id.imageVerticalView)
        imageVerticalView.setData(imageAdapter)

        val imageStaggeredGridView = findViewById<ExpandableItemView>(R.id.imageStaggeredGridView)
        imageStaggeredGridView.setData(imageAdapter)



        findViewById<Button>(R.id.btnExpandableRecyclerView).setOnClickListener {
            startActivity(Intent(this, ExpandableRecyclerViewActivity::class.java))
        }
    }

}