package com.example.project

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


class Question (var id :String?,var Type :String?,var question: String?,var option1: String?,var option2: String?,var option3: String?,var option4: String?,var answer: String?,var description: String?,var uid :String?) : Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    //Constructor
    public constructor():this("","","","","","","","","","")
    {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(Type)
        dest?.writeString(question)
        dest?.writeString(option1)
        dest?.writeString(option2)
        dest?.writeString(option3)
        dest?.writeString(option4)
        dest?.writeString(answer)
        dest?.writeString(description)
        dest?.writeString(uid)
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }

}