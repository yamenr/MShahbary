package com.example.tfotmauthtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopScoresActivity extends AppCompatActivity{

    private RecyclerView rvAllRest;
    Adapter adapter;
    FirebaseServices fbs;
    ArrayList<User> users;
    MyCallback myCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);

        Log.i("ggg", "entered top scores");

        fbs = FirebaseServices.getInstance();
        users = new ArrayList<User>();
        readData();
        myCallback = new MyCallback() {
            @Override
            public void onCallback(List<User> restsList) {
                Log.i("ggg", "recycler settings");
                RecyclerView recyclerView = findViewById(R.id.rvTopScores);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(), users);
                recyclerView.setAdapter(adapter);
                Log.i("ggg", "finished recycler settings");
            }
        };


        // set up the RecyclerView
        /*
        RecyclerView recyclerView = findViewById(R.id.rvRestsAllRest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRestaurant(this, rests);
        recyclerView.setAdapter(adapter);*/
    }

    private void readData() {
        try {

            fbs.getFire().collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    users.add(document.toObject(User.class));
                                }

                                myCallback.onCallback(users);
                                Log.i("ggg", "entered users in call back");
                            } else {
                                Log.e("AllRestActivity: readData()", "Error getting documents.", task.getException());
                                Log.i("ggg", "failed!");
                                Log.i("ggg", task.getException().getMessage());
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "error reading!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}