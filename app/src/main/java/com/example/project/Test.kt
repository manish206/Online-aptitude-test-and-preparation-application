package com.example.project

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.test_result_dialog_box.view.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class Test : AppCompatActivity() {

    public lateinit var question_list:MutableList<Question>
    lateinit var ToastBack:Toast
    var backPressedTime:Long = 0
    var timer:Long=0
    var performed:Int=0
    var totalquestion:Int=0
    var score:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        question_list= mutableListOf<Question>()

        val intent:Intent=getIntent()

        var i:Int=0
        val counterQuestionNo=findViewById<TextView>(R.id.txtView_counter)
        val question=findViewById<TextView>(R.id.txt_question)
        val option1=findViewById<TextView>(R.id.option1)
        val option2=findViewById<TextView>(R.id.option2)
        val option3=findViewById<TextView>(R.id.option3)
        val option4=findViewById<TextView>(R.id.option4)
        val CheckBox1=findViewById<CheckBox>(R.id.CheckBox1)
        val CheckBox2=findViewById<CheckBox>(R.id.CheckBox2)
        val CheckBox3=findViewById<CheckBox>(R.id.CheckBox3)
        val CheckBox4=findViewById<CheckBox>(R.id.CheckBox4)
        val nextButton=findViewById<Button>(R.id.nextButton)
        val showResult=findViewById<Button>(R.id.showResult)
        val previouseButton=findViewById<Button>(R.id.previouseButton)
        val questionCounter=findViewById<TextView>(R.id.questionCounter)

        var data=intent.getStringArrayListExtra("data")
        for(x in data!!){
            totalquestion++
            Log.v("EXTRA DATA",x.toString())

            FirebaseDatabase.getInstance().getReference("/Questions/").orderByChild("type").equalTo(x).limitToLast(5)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if(p0.exists()){
                            for(snapShot in p0.children){
                                var question= snapShot.getValue(Question::class.java)!!
                                question_list.add(question)
                                Log.v("question",question.question)
                                totalquestion++
                                Log.v("total question",totalquestion.toString())
                            }
                        }
                        counterQuestionNo.text=("Q. "+(i+1)+" ").toString()
                        questionCounter.text=((i+1).toString()+"/"+question_list.size)
                        question.text=question_list[i].question.toString()
                        option1.text=question_list[i].option1.toString()
                        option2.text=question_list[i].option2.toString()
                        option3.text=question_list[i].option3.toString()
                        option4.text=question_list[i].option4.toString()
                        totalquestion=question_list.size
                    }
                })
        }
        Log.v("total question",totalquestion.toString())
        var CheckboxAnswerarray= IntArray(5*totalquestion){0}
       // Log.v("checkbox array",CheckboxAnswerarray.size.toString())
        nextButton.setOnClickListener(View.OnClickListener {
            i++
            if(i<question_list.size && i>=0) {
                counterQuestionNo.text=("Q. "+(i+1)+" ").toString()
                questionCounter.text=((i+1).toString()+"/"+question_list.size)
                question.text = question_list[i].question.toString()
                option1.text = question_list[i].option1.toString()
                option2.text = question_list[i].option2.toString()
                option3.text = question_list[i].option3.toString()
                option4.text = question_list[i].option4.toString()

                when(CheckboxAnswerarray[i]){
                    1->CheckBox1.isChecked=true
                    2->CheckBox2.isChecked=true
                    3->CheckBox3.isChecked=true
                    4->CheckBox4.isChecked=true
                    else->{CheckBox1.isChecked=false
                            CheckBox2.isChecked=false
                            CheckBox3.isChecked=false
                            CheckBox4.isChecked=false}
                }
            }
            if((nextButton.text).equals("submit")){

                nextButton.isEnabled=false
                previouseButton.isEnabled=false
                var perf_question:Int=0
                for(i in CheckboxAnswerarray) {
                    if(i!=0){
                    perf_question++}
                    Log.v("Checkbox value", i.toString())
                }

                var builder=AlertDialog.Builder(this)
                if(perf_question<totalquestion) {
                    builder.setMessage("Are you sure you want to submit the test now?\n you attempted only " + perf_question.toString() + " out of " + totalquestion.toString() + " questions.")
                }
                else{
                    builder.setMessage("Are you sure you want to submit the test now?")
                }
                builder.setTitle("App says")
                builder.setCancelable(false)
                var IsbtnPressed:String?=null
                builder.setPositiveButton("Yes"){
                    dialog, which ->
                    IsbtnPressed="Yes"
                    Log.v("total question",totalquestion.toString())
                    Log.v("performed question",perf_question.toString())
                    showResultDialog(totalquestion,perf_question,score)
                }
                builder.setNegativeButton("No"){
                    dialog, which ->
                    nextButton.isEnabled=true
                    previouseButton.isEnabled=true
                }
                var resultDialog=builder.create()
                resultDialog.show()
            }

            if((i+1).equals(question_list.size)){
                nextButton.text="submit"
            }
            if((i+1)< question_list.size){
                nextButton.text="next"
            }
        })
        previouseButton.setOnClickListener(View.OnClickListener {
            i--
            if(i<question_list.size && i>=0) {
                counterQuestionNo.text=("Q. "+(i+1)+" ").toString()
                questionCounter.text=((i+1).toString()+"/"+question_list.size)
                question.text = question_list[i].question.toString()
                option1.text = question_list[i].option1.toString()
                option2.text = question_list[i].option2.toString()
                option3.text = question_list[i].option3.toString()
                option4.text = question_list[i].option4.toString()
                //set the checked option on checkbox
                when(CheckboxAnswerarray[i]){
                    1->CheckBox1.isChecked=true
                    2->CheckBox2.isChecked=true
                    3->CheckBox3.isChecked=true
                    4->CheckBox4.isChecked=true
                    else->{CheckBox1.isChecked=false
                        CheckBox2.isChecked=false
                        CheckBox3.isChecked=false
                        CheckBox4.isChecked=false}
                }
            }
            if((i+1).equals(question_list.size)){
                nextButton.text="submit"
            }
            if((i+1)< question_list.size){
                nextButton.text="next"
            }
        })

        CheckBox1.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{

            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    if(option1.text.equals(question_list[i].answer)){
                        score++
                        Log.v("score",score.toString())
                    }
                    if(CheckBox2.isChecked){CheckBox2.isChecked=false}
                    if(CheckBox3.isChecked){CheckBox3.isChecked=false}
                    if(CheckBox4.isChecked){CheckBox4.isChecked=false}
                    performed++
                    CheckboxAnswerarray.set(i,1)
                }
                else{ if(option1.text==question_list[i].answer){
                    score--
                    Log.v("score",score.toString())
                    performed--
                }
                    CheckboxAnswerarray.set(i,0)
                }
            }
        })
        CheckBox2.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{

            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    if(option2.text.equals(question_list[i].answer)){ score++
                        Log.v("score",score.toString())}

                    if(CheckBox1.isChecked){CheckBox1.isChecked=false}
                    if(CheckBox3.isChecked){CheckBox3.isChecked=false}
                    if(CheckBox4.isChecked){CheckBox4.isChecked=false}
                    performed++
                    CheckboxAnswerarray.set(i,2)
                }
                else{ if(option2.text==question_list[i].answer){score--
                    Log.v("score",score.toString())}
                    performed--
                    CheckboxAnswerarray.set(i,0)
                }
            }
        })
        CheckBox3.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{

            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    if(option3.text.equals(question_list[i].answer)){ score++
                        Log.v("score",score.toString())}

                    if(CheckBox1.isChecked){CheckBox1.isChecked=false}
                    if(CheckBox2.isChecked){CheckBox2.isChecked=false}
                    if(CheckBox4.isChecked){CheckBox4.isChecked=false}
                    performed++
                    CheckboxAnswerarray.set(i,3)
                }
                else{ if(option3.text==question_list[i].answer){score--
                    Log.v("score",score.toString())}
                    performed--
                    CheckboxAnswerarray.set(i,0)
                }
            }
        })
        CheckBox4.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{

            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    if(option4.text.equals(question_list[i].answer)){ score++
                        Log.v("score",score.toString())}

                    if(CheckBox1.isChecked){CheckBox1.isChecked=false}
                    if(CheckBox2.isChecked){CheckBox2.isChecked=false}
                    if(CheckBox3.isChecked){CheckBox3.isChecked=false}
                    performed++
                    CheckboxAnswerarray.set(i,4)
                }
                else{ if(option4.text==question_list[i].answer){score--
                    Log.v("score",score.toString())}
                    performed--
                    CheckboxAnswerarray.set(i,0)
                }
            }
        })

        showResult.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent:Intent= Intent(this@Test,ShowAnswer::class.java)
                intent.putParcelableArrayListExtra("QUESTION_LIST",java.util.ArrayList(question_list))
                var UserAnswerArray:IntArray= CheckboxAnswerarray
                intent.putExtra("USER_ANSWER",UserAnswerArray)
                startActivity(intent)
            }
        })
    }

    private fun showResultDialog(totalquestion:Int,performed_question:Int,correct:Int) {

        val showResult=findViewById<Button>(R.id.showResult)
        showResult.visibility=View.VISIBLE
        Log.v("total question",totalquestion.toString())
        Log.v("performed question",performed_question.toString())
        val viewgroup: ViewGroup=findViewById(android.R.id.content)
        val dialogView=LayoutInflater.from(this).inflate(R.layout.test_result_dialog_box,null)
        val builder= AlertDialog.Builder(this).setView(dialogView)
        val alertDialog=builder.show()
        dialogView.total_questions.text=totalquestion.toString()
        dialogView.answered_question.text=performed_question.toString()
        dialogView.unanswered_question.text=(totalquestion-performed_question).toString()
        dialogView.correct_answer.text=(correct+1).toString()
        dialogView.incorrect_answer.text=(performed_question-(correct+1)).toString()
        dialogView.buttonOk.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()
        })

    }


    override fun onBackPressed() {
        if(backPressedTime+2000>System.currentTimeMillis()){
            super.onBackPressed()
            return
        }
        else{
            ToastBack= Toast.makeText(this,"Press again to leave the test", Toast.LENGTH_SHORT)
            ToastBack.show()        }
        backPressedTime=System.currentTimeMillis()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.timer_menu,menu)
        var counter: MenuItem=menu!!.findItem(R.id.counter)
        var size:Int=0
        var data=intent.getStringArrayListExtra("data")
        for(x in data){
            size++
        }
        Log.v("Test size",size.toString())
        var countDownTimer=object : CountDownTimer((5*size*60*1000).toLong(),1000){
            override fun onFinish(){
                showResultDialog(totalquestion,performed,score)
                nextButton.isEnabled=false
                previouseButton.isEnabled=false
                showResult.isEnabled=true
                showResult.visibility=View.VISIBLE
            }
            override fun onTick(millisUntilFinished: Long) {
                var millis: Long=millisUntilFinished
                var hour=(TimeUnit.MILLISECONDS.toHours(millis))
                var minute=(TimeUnit.MILLISECONDS.toMinutes(millis)) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours((millis)))
                var seconds=(TimeUnit.MILLISECONDS.toSeconds(millis)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))

                counter.setTitle(hour.toString()+":"+minute.toString()+":"+seconds.toString())
                timer=millis
            }
        }
        countDownTimer.start()
        return true
    }
}