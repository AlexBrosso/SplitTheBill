package com.projeto.ads.pdm.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.projeto.ads.pdm.splitthebill.R
import com.projeto.ads.pdm.splitthebill.model.Person

class PersonAdapter( context : Context, private val personList: MutableList<Person>) : ArrayAdapter<Person>(context, R.layout.tile_person, personList) {

        private data class TilePersonHolder(val nameTv: TextView, val paidAmountTv: TextView)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val person = personList[position]
            var personTileView = convertView
            if (personTileView == null) {

                personTileView =
                    (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                        R.layout.tile_person,
                        parent,
                        false
                    )

                val tilePersonHolder = TilePersonHolder(
                    personTileView.findViewById(R.id.nameTv),
                    personTileView.findViewById(R.id.paidAmountTv),
                )
                personTileView.tag = tilePersonHolder
            }

            with(personTileView?.tag as TilePersonHolder) {
                nameTv.text = person.name
                paidAmountTv.text = "R$ " + person.paidAmount.toString()
            }

            return personTileView
        }
}