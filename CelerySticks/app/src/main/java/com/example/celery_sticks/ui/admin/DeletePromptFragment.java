package com.example.celery_sticks.ui.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.celery_sticks.R;

public class DeletePromptFragment extends DialogFragment {
    private String id;
    private String type;

    public DeletePromptFragment(String id, String type) {
        this.id = id;
        this.type = type;
    }

    interface deleteImageDialogListener {
        void deleteImage(String id, String type);
    }

    private deleteImageDialogListener listener;

    /*
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof deleteImageDialogListener) {
            listener = (deleteImageDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement deleteImageDialogListener");
        }
    }
    */
     

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.admin_browse_images_prompt, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        return builder
                .setView(view)
                .setTitle("Remove this image?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Remove", (dialog, which) -> { // delete the item
                    if (this.id != null && this.type != null) {
                        listener.deleteImage(this.id, this.type);
                    }
                }).create();

    }
}
