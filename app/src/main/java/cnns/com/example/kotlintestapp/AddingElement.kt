package cnns.com.example.kotlintestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_adding_element.*

class AddingElement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_element)

        val actionBar = supportActionBar

        actionBar!!.title = "Ajout d'un département"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    fun insertItem(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        var editDepartementName = editDepartementName.text.toString()
        var editDepartementCode = editDepartementCode.text.toString()
        var editDepartementCodeRegion= editDepartementCodeRegion.text.toString()

        intent.putExtra("Name", editDepartementName)
        intent.putExtra("Description", editDepartementCode)
        intent.putExtra("Region", editDepartementCodeRegion)

        startActivity(intent)
    }
}