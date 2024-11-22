package com.example.celery_sticks.ui.admin;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.celery_sticks.R;
import com.example.celery_sticks.databinding.FragmentAdminBrowseImagesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class ImageBrowseFragment extends Fragment implements DeletePromptFragment.deleteImageDialogListener{

    private FragmentAdminBrowseImagesBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String[]> images = new ArrayList<>();
    private ListView imagesListView;
    private ImageArrayAdapter adapter;


    @Override
    public void deleteImage(String id, String type) {


        if (type.equals("Type: user profile")) {
            HashMap<String, Object> userData = new HashMap<>();
            userData.put("encodedImage", "");

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(id).update(userData)
                    .addOnSuccessListener(aVoid -> {
                        refreshList();
                    });
        }
        else {
            HashMap<String, Object> eventData = new HashMap<>();
            eventData.put("image", "");

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("events").document(id).update(eventData)
                    .addOnSuccessListener(aVoid -> {
                        refreshList();
                    });
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminBrowseImagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imagesListView = root.findViewById(R.id.admin_image_browse_list_view);
        adapter = new ImageArrayAdapter(getContext(), images);
        imagesListView.setAdapter(adapter);


        // get all images from users
        CollectionReference users = db.collection("users");
        users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        String title = "Origin: " + document.getString("firstName") + " " + document.getString("lastName");
                        String userID = document.getId();
                        String userImage = document.getString("encodedImage");

                        if (!userImage.equals("")) {
                            String[] entry = {"Type: user profile", title, userID, userImage};
                            images.add(entry);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // get all images from events
        CollectionReference events = db.collection("events");
        events.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        String title = "Origin: " + document.getString("name");
                        String eventID = document.getId();
                        String eventImage = document.getString("image");

                        if (!eventImage.equals("")) {
                            String[] entry = {"Type: event poster", title, eventID, eventImage};
                            images.add(entry);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        // when an item is clicked
        imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] imageChosen = (String[]) imagesListView.getItemAtPosition(i);
                new com.example.celery_sticks.ui.admin.DeletePromptFragment(imageChosen[2], imageChosen[0]).show(getChildFragmentManager(), "Remove this image?");
            }
        });


        return root;

    }


    public void refreshList() {
        images.clear();

        CollectionReference users = db.collection("users");
        users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        String title = "Origin: " + document.getString("firstName") + " " + document.getString("lastName");
                        String userID = document.getId();
                        String userImage = document.getString("encodedImage");

                        if (!userImage.equals("")) {
                            String[] entry = {"Type: user profile", title, userID, userImage};
                            images.add(entry);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // get all images from events
        CollectionReference events = db.collection("events");
        events.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        String title = "Origin: " + document.getString("name");
                        String eventID = document.getId();
                        String eventImage = document.getString("image");

                        if (!eventImage.equals("")) {
                            String[] entry = {"Type: event poster", title, eventID, eventImage};
                            images.add(entry);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}

