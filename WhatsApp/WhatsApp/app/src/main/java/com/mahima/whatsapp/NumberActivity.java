package com.mahima.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.mahima.whatsapp.databinding.ActivityNumberBinding;
import com.mahima.whatsapp.databinding.ActivitySignInBinding;

import java.util.concurrent.TimeUnit;

public class NumberActivity extends AppCompatActivity {
 ActivityNumberBinding binding;

    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationId;
    EditText  editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(NumberActivity.this);
        progressDialog.setTitle("Sign In");
        progressDialog.setMessage("Login to your account");



                // Initialize the PhoneAuthProvider callback
                callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // This callback will be invoked in two situations:
                        // 1. Instant verification. In some cases, the phone number can be instantly
                        //    verified without needing to send or enter a verification code.
                        // 2. Auto-retrieval. The onCodeSent method will be called, and the
                        //    verification code will be automatically retrieved.
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // Handle verification failure
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        // The verification code has been sent to the provided phone number.
                        // Save the verification ID and send it to the next screen for manual verification.
                        NumberActivity.this.verificationId = verificationId;
                    }
                };

                // Set an OnClickListener for the sign-in button
                Button signInButton = findViewById(R.id.btnNumberSignIn);
                signInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phoneNumber = "+1" + editTextPhoneNumber.getText().toString().trim();
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phoneNumber,
                                60,
                                TimeUnit.SECONDS,
                                NumberActivity.this,
                                callbacks);
//                        Intent intent = new Intent(NumberActivity.this,  MainActivity.class);
//                        startActivity(intent);
                    }
                });
            }

            // Method to sign in with the PhoneAuthCredential
            private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Phone sign-in was successful.
                                    FirebaseUser user = task.getResult().getUser();
                                    // Do something with the user information.
                                    Intent intent = new Intent(NumberActivity.this,  MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Phone sign-in failed.
                                    Toast.makeText(NumberActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(NumberActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
}