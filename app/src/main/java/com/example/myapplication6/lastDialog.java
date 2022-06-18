package com.example.myapplication6;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

class lastDialog extends AlertDialog.Builder
{

    public lastDialog(Context context)
    {
        super(context);
        this.setTitle("Ошибка подключения");
        this.setMessage("Проверьте ваше интренет соединение")

                .setCancelable(false)

                .setNegativeButton("Выход", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();

                        ((Activity)context).finishAffinity();


                    }
                });

    }
}
