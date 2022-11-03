package com.projeto.ads.pdm.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Int,
    var name: String,
    var purchasedItem : String,
    var paidAmount : Double,
    var debt : Double,
    var shouldReceive : Double
) : Parcelable