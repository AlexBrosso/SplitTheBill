package com.projeto.ads.pdm.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projeto.ads.pdm.splitthebill.databinding.ActivityPersonBinding
import com.projeto.ads.pdm.splitthebill.model.Constant.EXTRA_PERSON
import com.projeto.ads.pdm.splitthebill.model.Constant.VIEW_PERSON
import com.projeto.ads.pdm.splitthebill.model.Person
import kotlin.random.Random

class PersonActivity : AppCompatActivity() {
    private val activityPersonBinding: ActivityPersonBinding by lazy {
        ActivityPersonBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityPersonBinding.root)

        val receivedPerson =  intent.getParcelableExtra<Person>(EXTRA_PERSON)
        receivedPerson?.let{ _receivedPerson ->
            activityPersonBinding.titleTv.text = "Editar Pessoa"
            activityPersonBinding.nameEt.isEnabled = false
            with(activityPersonBinding)
            {
                with(_receivedPerson)
                {
                    nameEt.setText(name)
                    paidAmountEt.setText(paidAmount.toString())
                    purchasedItemEt.setText(purchasedItem)
                }
            }
        }

        if (intent.getBooleanExtra(VIEW_PERSON, false)) {
            activityPersonBinding.titleTv.text = "Visualizar Pessoa"
            activityPersonBinding.nameEt.isEnabled = false
            activityPersonBinding.paidAmountEt.isEnabled = false
            activityPersonBinding.purchasedItemEt.isEnabled = false
            activityPersonBinding.saveBt.visibility = View.GONE
        }

        activityPersonBinding.saveBt.setOnClickListener {
            val personName = activityPersonBinding.nameEt.text.toString()
            val personPaidAmount = activityPersonBinding.paidAmountEt.text.toString()
            val personPurchasedItem = activityPersonBinding.purchasedItemEt.text.toString()

            if (!personName.isNullOrEmpty() && !personPaidAmount.isNullOrEmpty() && !personPurchasedItem.isNullOrEmpty())
            {
                val person = Person(
                    name = personName,
                    paidAmount = personPaidAmount.toDouble(),
                    purchasedItem = personPurchasedItem,
                    debt = 0.00,
                    shouldReceive = 0.00,
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_PERSON, person)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
            else Toast.makeText(this, "Existem campos preenchidos incorretamente.", Toast.LENGTH_SHORT).show()
        }

        activityPersonBinding.cancelBt.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}