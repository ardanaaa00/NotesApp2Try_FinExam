package com.example.notesapp2try;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPw extends AppCompatActivity {

    private EditText mforgotpassword;
    private Button mpasswordrecoverybutton;
    private TextView mgobacktologin;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pw);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mforgotpassword = findViewById(R.id.forgotpassword);
        mpasswordrecoverybutton = findViewById(R.id.passwordrecoverybutton);
        mgobacktologin = findViewById(R.id.gobacktologin);

        firebaseAuth= FirebaseAuth.getInstance();

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPw.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mpasswordrecoverybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mforgotpassword.getText().toString().trim();
                if (mail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter your email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // we have to send pw recover email
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Mail Sent, You can recover your password using mail",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgetPw.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Email or Account Not Exist",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}