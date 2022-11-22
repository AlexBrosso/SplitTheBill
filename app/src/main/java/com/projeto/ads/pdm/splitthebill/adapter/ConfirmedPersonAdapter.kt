package com.projeto.ads.pdm.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.projeto.ads.pdm.splitthebill.R
import com.projeto.ads.pdm.splitthebill.model.Person

class ConfirmedPersonAdapter (context : Context, private val personList: MutableList<Person>) : ArrayAdapter<Person>(context, R.layout.tile_person, personList) {

    private data class TileConfirmedPersonHolder(val nameTv: TextView, val paidAmountTv: TextView, val debtTv: TextView, val shouldReceiveTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val person = personList[position]
        var confirmedPersonTileView = convertView
        if (confirmedPersonTileView == null) {

            confirmedPersonTileView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_confirmed_person,
                    parent,
                    false
                )

            val tilePersonHolder = TileConfirmedPersonHolder(
                confirmedPersonTileView.findViewById(R.id.nameTv),
                confirmedPersonTileView.findViewById(R.id.paidAmountTv),
                confirmedPersonTileView.findViewById(R.id.debtTv),
                confirmedPersonTileView.findViewById(R.id.shouldReceiveTv),
            )
            confirmedPersonTileView.tag = tilePersonHolder
        }

        with(confirmedPersonTileView?.tag as TileConfirmedPersonHolder) {
            nameTv.text = person.name
            paidAmountTv.text = "Valor Pago: R$ " + person.paidAmount.toString()
            debtTv.text = "Deve pagar: R$ " + person.debt.toString()
            shouldReceiveTv.text = "Deve receber: R$ " + person.shouldReceive.toString()
        }

        return confirmedPersonTileView
    }
}