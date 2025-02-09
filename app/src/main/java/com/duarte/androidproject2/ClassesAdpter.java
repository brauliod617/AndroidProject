package com.duarte.androidproject2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ClassesAdpter extends ArrayAdapter<Classes> {
    private Context context;
    private List<Classes> classesList;
    private CreateDialogInterface createDialogInterface;

    public ClassesAdpter(@NonNull Context context, ArrayList<Classes> classesList) {
        super(context, 0, classesList);
        this.context = context;
        this.classesList = classesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.activity_classes_list_template
                    , parent, false);
        }

        final Classes currentClass = classesList.get(position);

        ImageView background = listItem.findViewById(R.id.imv_classes_list_bg);
        background.setImageResource(R.drawable.ic_classcontainer);

        TextView txvClassDay = listItem.findViewById(R.id.txv_classDay);
        txvClassDay.setText(currentClass.getClassDay());

        TextView txvClassLocation = listItem.findViewById(R.id.txv_class_location);
        txvClassLocation.setText(currentClass.getClassLocation());

        TextView txvClassName = listItem.findViewById(R.id.txv_class_name);
        txvClassName.setText(currentClass.getClassName());

        /*
        ImageButton imbNotificationBell = listItem.findViewById(R.id.imb_notifications_bell);
        imbNotificationBell.setImageResource(
                currentClass.getNotification() ? R.drawable.ic_alertsrang : R.drawable.ic_alerts );
         */

        background.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.println(Log.DEBUG, "Log","List Item Clicked");
                createDialogInterface.onClassClicked(currentClass);
            }
        });

        return listItem;
    }

    public void setCreateDialogInterface(CreateDialogInterface createDialogInterface) {
        this.createDialogInterface = createDialogInterface;
    }
}
