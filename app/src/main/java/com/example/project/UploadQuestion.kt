package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_upload_question.*
import java.lang.Exception
import java.lang.ref.Reference

class UploadQuestion : AppCompatActivity(){
    lateinit var progressBar: ProgressBar
    public var backPressedTime:Long=0
    public var NoOfQuestion:Int=0
    lateinit var ToastBack : Toast
    lateinit var NotificationRef : DatabaseReference
    lateinit var reward_list:MutableList<Reward>
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_question)
        progressBar=findViewById(R.id.progressBar)
        val optinAnsDropDown=findViewById<Spinner>(R.id.optionAnswerDropDown)
        val optionTypeDropDown=findViewById<Spinner>(R.id.typeDropDown)
        val optionTypeOfQuestionDropDown=findViewById<Spinner>(R.id.typeOfQuesDropDown)
        val editTextQuestion=findViewById<EditText>(R.id.editTextQuestion)
        val editTextOption1=findViewById<EditText>(R.id.editTextOption1)
        val editTextOption2=findViewById<EditText>(R.id.editTextOption2)
        val editTextOption3=findViewById<EditText>(R.id.editTextOption3)
        val editTextOption4=findViewById<EditText>(R.id.editTextOption4)
        val editTextDescription=findViewById<EditText>(R.id.editTextDescription)
        val submitButton=findViewById<Button>(R.id.submit)
        var typeOfQuestion: String? =null
        var Answered:String?=null
        reward_list= mutableListOf()
        NotificationRef=FirebaseDatabase.getInstance().getReference().child("/notification/")

        //Drop down for answer options
        ArrayAdapter.createFromResource(this,R.array.answer_array,android.R.layout.simple_spinner_dropdown_item).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            optionAnswerDropDown.adapter=adapter
        }

        //Drop down for Selecting Type
        ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_dropdown_item).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            typeDropDown.adapter=adapter
        }

        //Drop down for TYpe of question in initial phase
        ArrayAdapter.createFromResource(this,R.array.Arithmetic_aptitude,android.R.layout.simple_spinner_dropdown_item).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeOfQuesDropDown.adapter=adapter
        }
        optionTypeDropDown.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            //Setting Type of Question on onclick of Type Button
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(parent!!.getItemAtPosition(position).toString()){
                    "Arithmetic aptitude"-> {

                        ArrayAdapter.createFromResource(
                            this@UploadQuestion,
                            R.array.Arithmetic_aptitude,
                            android.R.layout.simple_spinner_dropdown_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            typeOfQuesDropDown.adapter = adapter
                        }
                        typeOfQuestion=optionTypeOfQuestionDropDown.getItemAtPosition(0).toString()
                    }
                    "logical reasoning"-> {
                        ArrayAdapter.createFromResource(
                            this@UploadQuestion,
                            R.array.logical_reasoning,
                            android.R.layout.simple_spinner_dropdown_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            typeOfQuesDropDown.adapter = adapter
                        }

                        typeOfQuestion=optionTypeOfQuestionDropDown.getItemAtPosition(0).toString()
                    }
                    "Programming"->{
                        ArrayAdapter.createFromResource(
                            this@UploadQuestion,
                            R.array.programming,
                            android.R.layout.simple_spinner_dropdown_item
                        ).also {
                                adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            typeOfQuesDropDown.adapter=adapter
                        }

                        typeOfQuestion=optionTypeOfQuestionDropDown.getItemAtPosition(0).toString()
                    }
                    "DBMS"->{
                        ArrayAdapter.createFromResource(
                            this@UploadQuestion,
                            R.array.dbms,
                            android.R.layout.simple_spinner_dropdown_item
                        ).also {
                            adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        typeOfQuesDropDown.adapter=adapter
                        }
                    }
                }
            }
        }

        optionAnswerDropDown.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Answered=editTextOption1.text.toString()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.v("position",position.toString())
                when(position){
                    0->Answered=editTextOption1.text.toString()
                    1->Answered=editTextOption2.text.toString()
                    2->Answered=editTextOption3.text.toString()
                    3->Answered=editTextOption4.text.toString()
                }
            }
        }

        submitButton.setOnClickListener() { View ->

            typeOfQuestion=optionTypeOfQuestionDropDown.selectedItem.toString()
            Log.v("Type",typeOfQuestion.toString())

            if ( editTextQuestion.text.isNotEmpty() && editTextOption1.text.isNotEmpty() && editTextOption2.text.isNotEmpty() && editTextOption3.text.isNotEmpty() && editTextOption4.text.isNotEmpty() && editTextDescription.text.isNotEmpty()) {
                var key: String = FirebaseDatabase.getInstance().getReference("/Questions/").push().key.toString()
                    if (key != null && typeOfQuestion != null && Answered!=null && Answered!="" ) {
                        val question = Question(
                            key,
                            typeOfQuestion!!,
                            editTextQuestion.text.toString(),
                            editTextOption1.text.toString(),
                            editTextOption2.text.toString(),
                            editTextOption3.text.toString(),
                            editTextOption4.text.toString(),
                            Answered.toString(),
                            editTextDescription.text.toString(),
                            FirebaseAuth.getInstance().uid.toString()
                    )
                    FirebaseDatabase.getInstance().getReference("/Questions/").child(key)
                        .setValue(question).addOnCompleteListener {

                            var questionUploadingNotification : HashMap<String,String> = HashMap<String,String>()
                            questionUploadingNotification.put("from",FirebaseAuth.getInstance().uid.toString())
                            questionUploadingNotification.put("type","request")
                            var notificationId:String=NotificationRef.push().key.toString()
                            NotificationRef.child(notificationId).setValue(questionUploadingNotification)

                            Toast.makeText(this,"Question uploaded successfully ",Toast.LENGTH_SHORT).show() }.addOnFailureListener(object : OnFailureListener{

                            override fun onFailure(p0: Exception) {
                                Log.v("Uploading Error",p0.toString())
                            } })

                        FirebaseMessaging.getInstance().subscribeToTopic("notification")
                        setUserToReward()
                        }

                editTextQuestion.text.clear()
                editTextOption1.text.clear()
                editTextOption2.text.clear()
                editTextOption3.text.clear()
                editTextOption4.text.clear()
                editTextDescription.text.clear()

            }
            else{
                Toast.makeText(this,"Enter all the fields",Toast.LENGTH_SHORT).show()
            }
            Log.v("Answered",Answered.toString())
        }
    }

    override fun onBackPressed() {

        if(backPressedTime+2000>System.currentTimeMillis()){
            super.onBackPressed()
            return
        }
        else{
            ToastBack=Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT)
            ToastBack.show()        }
        backPressedTime=System.currentTimeMillis()
    }

fun setUserToReward(){
    var rewardKey:String=FirebaseDatabase.getInstance().getReference("/reward/").push().key.toString()
    var reward:Reward= Reward(rewardKey,FirebaseAuth.getInstance().uid.toString(),10)
    FirebaseDatabase.getInstance().getReference("/reward/").child(rewardKey).setValue(reward).addOnCompleteListener {
        Toast.makeText(this,"You get 10 coins ...",Toast.LENGTH_SHORT).show()
    }
}
   fun sendNotification(){

   }


}

