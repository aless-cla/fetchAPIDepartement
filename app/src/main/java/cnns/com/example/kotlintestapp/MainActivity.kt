package cnns.com.example.kotlintestapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), ExampleAdapter.OnItemClickListener {
    val exampleList = generateDummyList(25)
    val adapter = ExampleAdapter(exampleList, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        //Système de cache
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val intent = intent
        val name = intent.getStringExtra("Name")
        val descriptionTexte = intent.getStringExtra("Description")
        val regionTexte = intent.getStringExtra("Region")

        if(name != null && descriptionTexte != null){
            val newItem = DepartementObject(
                name,
                descriptionTexte,
                0,
                regionTexte
            )
            adapter.notifyItemInserted(0)

            val db = DataBaseHandler(this)
            db.insertData(newItem)
        }

        generateDummyList(25)
        adapter.notifyDataSetChanged()

    }

    public override fun onStart() {

        super.onStart()
        generateDummyList(25)
                val newItem = DepartementObject(
            "Departement vide",
            "Ce département est vide (exemple de présentation d'un département)",
            0, ""
        )
        exampleList.add(0, newItem)
        adapter.notifyItemInserted(0)
        adapter.notifyDataSetChanged()
    }

    public override fun onResume() {

        super.onResume()
        generateDummyList(25)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Event click sur le département $position ", Toast.LENGTH_SHORT).show()
        val clickedItem = exampleList[position]
        adapter.notifyItemChanged(position)
        val intent = Intent(this, DetailElement::class.java)
        intent.putExtra("Name", clickedItem.text1)
        intent.putExtra("Description", clickedItem.text2)
        intent.putExtra("Region", clickedItem.text3)

        startActivity(intent)
    }

    fun insertItemPageOpen(view: View) {
        val intent = Intent(this, AddingElement::class.java)
        startActivity(intent)
    }

     fun generateDummyList(size: Int): ArrayList<DepartementObject> {
         val context = this
         val db = DataBaseHandler(context)
         val list = ArrayList<DepartementObject>()

         title = "Liste des départements Français"
             for ( i in 0 until 25) {
                 GlobalScope.launch(Dispatchers.Main) {
                     try {
                         if(db.readData(25).size < 3){
                             val response = ApiClient.apiService.getDepartement(i+10)
                             if (response.isSuccessful && response.body() != null) {
                                 val content = response.body()
                                 println(content)

                                 if(content?.nom?.length!! > 2 ) {
                                     val item = DepartementObject(content?.nom, content?.code, content?.id, content?.codeRegion)
                                     list += item
                                     db.insertData(item)
                                 }
                             } else {
                                 //                        Toast.makeText(
                                 //                            this@MainActivity,
                                 //                            "Error Occurred: ${response.message()}",
                                 //                            Toast.LENGTH_LONG
                                 //                        ).show()
                             }
                         } else {
                             val a = db.readData(size-1).get(i)
                             val item = DepartementObject( a.text1, a.text2, a.id, a.text3)
                             list += item
                         }

                     } catch (e: Exception) {
                         println(e)
                         //                    Toast.makeText(
                         //                        this@MainActivity,
                         //                        "Error Occurred: ${e.message}",
                         //                        Toast.LENGTH_LONG
                         //                    ).show()
                     }
                 }
         }

        return list
    }

}