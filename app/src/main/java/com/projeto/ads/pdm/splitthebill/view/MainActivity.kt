package com.projeto.ads.pdm.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.projeto.ads.pdm.splitthebill.R
import com.projeto.ads.pdm.splitthebill.databinding.ActivityMainBinding
import com.projeto.ads.pdm.splitthebill.model.Constant.EXTRA_PERSON
import com.projeto.ads.pdm.splitthebill.model.Person

class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var personActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        personActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                result ->
            if (result.resultCode == RESULT_OK){
                val person = result.data?.getParcelableExtra<Person>(EXTRA_PERSON)
                person?.let {_person ->
                    activityMainBinding.test.text = _person.name
                }
            }
            else Toast.makeText(this, "Operação cancelada.", Toast.LENGTH_SHORT).show()
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
            else -> { false }
        }
    }
}