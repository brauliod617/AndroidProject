package com.duarte.androidproject2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends ArrayAdapter<Questions> {
    private Context context;
    private List<Questions> questionsList;
    LoadQuestionInteface loadQuestionInteface;

    public QuestionsAdapter(@NonNull Context context, ArrayList<Questions> questionsList) {
        super(context, 0, questionsList);
        this.context = context;
        this.questionsList = questionsList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.question_template
                    , parent, false);
        }

        final Questions currentQuestion = questionsList.get(position);

        ImageView background = listItem.findViewById(R.id.imvQuestionBG);
        background.setImageResource(R.drawable.ic_questionsbg);

        ImageView userLogo = listItem.findViewById(R.id.imvUserLogo);
        userLogo.setImageResource(R.drawable.userlogo);

        TextView txvUserName = listItem.findViewById(R.id.txvUserName);
        txvUserName.setText(currentQuestion.getOpEmail());

        TextView txvQuestionTitle = listItem.findViewById(R.id.txvQuestionTitle);
        txvQuestionTitle.setText(currentQuestion.getQuestionTitle());

        ImageView commentCloud = listItem.findViewById(R.id.clouldLogo);
        commentCloud.setImageResource(R.drawable.ic_cloud);

        TextView txvCommentNumber = listItem.findViewById(R.id.txvCommentNumbers);
        if(currentQuestion.getNumberOfComments() > 0){
            txvCommentNumber.setText( (currentQuestion.getNumberOfComments() + R.string.comments));
        }else{
            txvCommentNumber.setText("");
        }

        //Add on click listener to each question Item on UI
        //calls function in QuestionPage
        background.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.println(Log.DEBUG, "Log","List Item Clicked");
                loadQuestionInteface.onQuestionClicked(currentQuestion);
            }
        });

        return listItem;

    }

    public void setLoadQuestionInteface(LoadQuestionInteface loadQuestionInteface){
        this.loadQuestionInteface = loadQuestionInteface;
    }

}
