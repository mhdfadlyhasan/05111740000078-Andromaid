package com.hzzzey.andromaid;

import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class PopUpRename extends DialogFragment {
    User user = User.getInstance();
    private PopUpRenameListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_pop_up_rename,null);
        final EditText editText = view.findViewById(R.id.NewName);
        if(user.get_UserName()!="Non")editText.setText(user.get_UserName());
        builder.setView(view);
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            String Name = editText.getText().toString();
            listener.changeName(Name);
        });
    return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (PopUpRenameListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Implement the listener!");
        }
    }

    public interface PopUpRenameListener{
        void changeName(String Name);
    }
}
