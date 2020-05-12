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

public class editLeft extends DialogFragment {
    public DialogEditLeftListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_money_input, null);
        builder.setView(view);

        final EditText InputMoney = view.findViewById(R.id.MoneyInput);
        if(User.getInstance().get_LeftMoney()!=0.01)InputMoney.setText(User.getInstance().get_LeftMoney()+"");
        builder.setTitle("How much should it be?");
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            Double FixedMoney = Double.parseDouble(InputMoney.getText().toString());
            listener.LeftChangeIsYes(FixedMoney);
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogEditLeftListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Implement the listener!");
        }
    }

    public interface DialogEditLeftListener {
        void LeftChangeIsYes(Double newMoney);
    }
}