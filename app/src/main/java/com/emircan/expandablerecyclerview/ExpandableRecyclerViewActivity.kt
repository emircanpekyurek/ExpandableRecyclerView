package com.emircan.expandablerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ExpandableRecyclerViewActivity : AppCompatActivity() {

    private val list = mutableListOf<ExpandableItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initRecyclerView()

        initButton()
    }

    private fun initRecyclerView() {

        val textList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

        val textAdapter = TextAdapter(textList) { position, data ->
            Toast.makeText(this, "$position : $data", Toast.LENGTH_SHORT).show()
        }
        list.add(ExpandableItem("Item 1 (Text)", textAdapter))
        list.add(ExpandableItem("Item 2 (Text)", textAdapter))
        for (i in 3..26) {
            if (i % 6 == 0) {
                list.add(ExpandableItem("Item Not Extendable $i") {
                    Toast.makeText(this, "Item : $i", Toast.LENGTH_SHORT).show()
                })
                continue
            }
            list.add(ExpandableItem("Item $i (Image)", ImageAdapter(i) {
                Toast.makeText(this, "Item : $it", Toast.LENGTH_SHORT).show()
            }))
        }
    }

    private fun initButton() {
        val expandableRecyclerView =
            findViewById<ExpandableRecyclerView>(R.id.expandableRecyclerView)
        expandableRecyclerView.setData(R.layout.custom_expandable_recycler_view, list)
    }
}