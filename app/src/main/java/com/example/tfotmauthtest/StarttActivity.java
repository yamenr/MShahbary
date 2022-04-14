package com.example.tfotmauthtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.StorageReference;

public class StarttActivity extends AppCompatActivity {


    private EditText etUsername,etPassword;
    private Utilities utils;
    private FirebaseServices fbs;
    private Uri filePath;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startt);
        getSupportActionBar().hide();
        connectComponents();
    }

    private void connectComponents() {
        etUsername = findViewById(R.id.etUsernameMain);
        etPassword = findViewById(R.id.etPasswordMain);
        utils = Utilities.getInstance();
        fbs = FirebaseServices.getInstance();
    }

    public void gotoSelection(View view) {
        Intent i = new Intent(this, Selection.class);
        startActivity(i);
    }

    public void login(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.trim().isEmpty() || password.trim().isEmpty())
        {
            Toast.makeText(this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!utils.correctuser(username) || !utils.correctpass(password))
        {
            Toast.makeText(this, "Incorrect username or password!", Toast.LENGTH_SHORT).show();
            return;
        }

        fbs.getAuth().signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//*check if it works

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(StarttActivity.this, Selection.class);
                            startActivity(i);



                        } else {

                            // TODO: commands if failed
                            Toast.makeText(StarttActivity.this, "Username or password is empty!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                });



    }



    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 40) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        try {
                            filePath = data.getData();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            ivSignup.setBackground(null);
                            ivSignup.setImageBitmap(bitmap);
                            uploadImage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED)  {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        } */



    public void GotoSignUp(View view) {

        Intent i = new Intent(this, Signup.class);
        startActivity(i);
    }
}