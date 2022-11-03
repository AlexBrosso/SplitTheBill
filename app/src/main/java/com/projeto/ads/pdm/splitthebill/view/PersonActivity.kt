package com.projeto.ads.pdm.splitthebill.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projeto.ads.pdm.splitthebill.databinding.ActivityPersonBinding
import com.projeto.ads.pdm.splitthebill.model.Constant.EXTRA_PERSON
import com.projeto.ads.pdm.splitthebill.model.Person
import kotlin.random.Random

class PersonActivity : AppCompatActivity() {
    private val activityPersonBinding: ActivityPersonBinding by lazy {
        ActivityPersonBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityPersonBinding.root)

        activityPersonBinding.saveBt.setOnClickListener{
            val person = Person(
                id = Random(System.currentTimeMillis()).nextInt(),
                name = activityPersonBinding.nameEt.text.toString(),
                paidAmount = activityPersonBinding.paidAmountEt.text.toString().toDouble(),
                purchasedItem = activityPersonBinding.purchasedItemEt.text.toString(),
                debt = 0.00,
                shouldReceive = 0.00,
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_PERSON, person)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        activityPersonBinding.cancelBt.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}