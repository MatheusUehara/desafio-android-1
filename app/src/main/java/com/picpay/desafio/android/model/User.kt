package com.picpay.desafio.android.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("img") val img: String? = String(),
    @SerializedName("name") val name: String? = String(),
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("username") val username: String? = String()
) : Parcelable