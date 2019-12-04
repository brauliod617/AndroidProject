package com.duarte.androidproject2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends ArrayAdapter<Answer> {

    private Context context;
    private List<Answer> answerList;


    public AnswerAdapter(@NonNull Context context, ArrayList<Answer> answerList) {
        super(context, 0, answerList);
        this.context = context;
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.answer_template_layout
                    , parent, false);
        }

        final Answer currentAnswer = answerList.get(position);

        TextView txvUserEmail = listItem.findViewById(R.id.txvUserEmailAswr);
        txvUserEmail.setText(currentAnswer.getOpEmail());

        TextView txvAnswerContent = listItem.findViewById(R.id.txvAnswerContent);
        txvAnswerContent.setText(currentAnswer.getAnswerContent());

        //TODO: maybe add a listener, make it clickable and allow users to add comments on a answer?
        //      Can probably just reuse postReply activity.. LOW PRIORITY

        return listItem;

    }

}
