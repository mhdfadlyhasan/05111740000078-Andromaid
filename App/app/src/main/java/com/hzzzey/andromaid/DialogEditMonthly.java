package com.hzzzey.andromaid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogEditMonthly extends DialogFragment  {

    public DialogEditMonthlyListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_dialog_edit_monthly,null);
        builder.setView(view);
        builder.setTitle("How much money for your monthly allocation?");
        final EditText newMoney = view.findViewById(R.id.NewMoneyMonthly);
        if(User.getInstance().get_MonthlyMoney()!=0.01)newMoney.setText(User.getInstance().get_MonthlyMoney()+"");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Double FixedMoney = Double.parseDouble(newMoney.getText().toString());
                listener.MonthlyChangeIsYes(FixedMoney);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogEditMonthlyListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Implement the listener!");
        }
    }
    public interface DialogEditMonthlyListener
    {
        void MonthlyChangeIsYes(Double newMoney);
    }
}
