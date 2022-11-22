package com.projeto.ads.pdm.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.projeto.ads.pdm.splitthebill.R
import com.projeto.ads.pdm.splitthebill.adapter.PersonAdapter
import com.projeto.ads.pdm.splitthebill.databinding.ActivityMainBinding
import com.projeto.ads.pdm.splitthebill.model.Constant.EXTRA_PERSON
import com.projeto.ads.pdm.splitthebill.model.Constant.PERSON_LIST
import com.projeto.ads.pdm.splitthebill.model.Constant.VIEW_PERSON
import com.projeto.ads.pdm.splitthebill.model.Person

class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var personActivityResultLauncher: ActivityResultLauncher<Intent>

    private val personList: MutableList<Person> = mutableListOf()

    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        personAdapter = PersonAdapter(this, personList)
        activityMainBinding.personLv.adapter = personAdapter


        personActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == RESULT_OK) {
                val person = result.data?.getParcelableExtra<Person>(EXTRA_PERSON)
                person?.let { _person->
                    val position = personList.indexOfFirst { it.id == _person.id }
                    if (position != -1) {
                        personList[position] = _person
                    }
                    else {
                        personList.add(_person)
                    }
                    personAdapter.notifyDataSetChanged()
                }
            }
            else Toast.makeText(this, "Operação cancelada.", Toast.LENGTH_SHORT).show()
        }

        registerForContextMenu(activityMainBinding.personLv)

        activityMainBinding.personLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val personIntent = Intent(this@MainActivity, PersonActivity::class.java)
                personIntent.putExtra(EXTRA_PERSON, personList[position])
                personIntent.putExtra(VIEW_PERSON, true)
                startActivity(personIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addPersonMi -> {
                personActivityResultLauncher.launch(Intent(this, PersonActivity::class.java))
                true
            }
            R.id.splitTheBillMi -> {
                if(personList.size > 1) {
                    val splitIntent = Intent(this, SplitActivity::class.java)
                    splitIntent.putParcelableArrayListExtra(PERSON_LIST, ArrayList(personList))
                    startActivity(splitIntent)
                }
                else Toast.makeText(this, "Não há participantes suficientes para dividir a conta.", Toast.LENGTH_LONG).show()
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.editPersonMi -> {
                val person = personList[position]
                val personIntent = Intent(this, PersonActivity::class.java)
                personIntent.putExtra(EXTRA_PERSON, person)
                personIntent.putExtra(VIEW_PERSON, false)
                personActivityResultLauncher.launch(personIntent)
                true
            }
            R.id.removePersonMi -> {
                personList.removeAt(position)
                personAdapter.notifyDataSetChanged()
                true
            }
            else -> { false }
        }
    }
}