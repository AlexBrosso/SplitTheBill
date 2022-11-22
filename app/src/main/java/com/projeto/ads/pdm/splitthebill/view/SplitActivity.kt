package com.projeto.ads.pdm.splitthebill.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projeto.ads.pdm.splitthebill.adapter.ConfirmedPersonAdapter
import com.projeto.ads.pdm.splitthebill.databinding.ActivitySplitBinding
import com.projeto.ads.pdm.splitthebill.model.Constant.PERSON_LIST
import com.projeto.ads.pdm.splitthebill.model.Person

class SplitActivity : AppCompatActivity() {
    private val activitySplitBinding: ActivitySplitBinding by lazy {
        ActivitySplitBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySplitBinding.root)

        val personList =  intent.getParcelableArrayListExtra<Person>(PERSON_LIST)?.toMutableList()
        personList?.let{ _receivedPersonList ->
            var sum : Double = 0.00

            _receivedPersonList.forEach{
                sum += it.paidAmount
            }

            activitySplitBinding.titleTv.text = "Gasto Total: R$ " + sum

            _receivedPersonList.forEach{
                it.debt = sum / _receivedPersonList.size

                if(it.paidAmount > it.debt){
                    it.shouldReceive = it.paidAmount - it.debt
                    it.debt = 0.00
                }
                else if(it.paidAmount < it.debt){
                    it.shouldReceive = 0.00
                    it.debt = it.debt - it.paidAmount
                }
                else{
                    it.shouldReceive = 0.00
                    it.debt = 0.00
                }
            }

            activitySplitBinding.confirmedPersonLv.adapter = ConfirmedPersonAdapter(this, _receivedPersonList)
        }

        activitySplitBinding.cancelBt.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

    }
}