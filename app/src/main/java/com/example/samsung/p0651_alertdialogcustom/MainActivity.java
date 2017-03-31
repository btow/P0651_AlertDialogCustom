package com.example.samsung.p0651_alertdialogcustom;

import android.app.Dialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.ViewGroup.*;

public class MainActivity extends AppCompatActivity {

    final int DIALOG = 1;
    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    int btn;
    LinearLayout layout;
    TextView tvCount;
    ArrayList<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViews = new ArrayList<TextView>(10);
    }

    public void onClickButton(View view) {
        btn = view.getId();
        showDialog(DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.custom_dialog);
        //Создание layout из dialog.xml
        layout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog, null);
        //Учтановка layout в качестве сдержимого тела диалога
        adb.setView(layout);
        //Нахождение TextViev для отображения количества
        tvCount = (TextView) layout.findViewById(R.id.tvCount);
        return adb.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG) {
            //Нахождение TextView для отображения времени и запись времени в его параметр
            TextView tvTime = (TextView) dialog.getWindow().findViewById(R.id.tvTime);
            tvTime.setText(sdf.format(new Date(System.currentTimeMillis())));
            //Если нажата кнопка "ADD"
            if (btn == R.id.btnAdd) {
                //Создание нового TextView, добавление его в диалог, создание сообщения и запись его в параметр
                TextView textView = new TextView(this);
                layout.addView(textView, new ActionBar.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                textView.setText(getString(R.string.textview) + (textViews.size() + 1));
                //Добавление созданного TextView в коллекцию textViews
                textViews.add(textView);
            } else {
                //Если коллекция созданных TextView не пуста
                if (textViews.size() > 0) {
                    //Получение из этой колеекции последнего TextView
                    TextView textView = textViews.get(textViews.size() - 1);
                    //Удаление TextView из диалога
                    layout.removeView(textView);
                    //Удаление TextView из коллекции textViews
                    textViews.remove(textView);
                }
            }
            //Обновление счётчика TextView
            tvCount.setText("TextViews number = " + textViews.size());
        }
    }
}
