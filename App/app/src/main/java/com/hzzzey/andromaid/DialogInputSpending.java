package com.hzzzey.andromaid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;



public class DialogInputSpending extends DialogFragment {
    OnSpendListener onSpendListener;

    final int SPEND_ACCEPTED = 7;
    final int SPEND_DECLINE = 8;

    public interface OnSpendListener{
        void onSpendClick(int result, Double ammount);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSpendListener = (DialogInputSpending.OnSpendListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Implement the listener!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_money_input, null);
        builder.setView(view);
        // Set the dialog title
        builder.setTitle("How much?")
                // Set the action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText InputMoney = view.findViewById(R.id.MoneyInput);
                        Double FixedMoney = Double.parseDouble(InputMoney.getText().toString());
                        onSpendListener.onSpendClick(SPEND_ACCEPTED,FixedMoney);
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        onSpendListener.onSpendClick(SPEND_DECLINE,0.0);
                    }
                });

        return builder.create();
    }
}
