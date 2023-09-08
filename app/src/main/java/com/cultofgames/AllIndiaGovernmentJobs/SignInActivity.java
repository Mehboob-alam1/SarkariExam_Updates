package com.cultofgames.AllIndiaGovernmentJobs;


import static com.cultofgames.AllIndiaGovernmentJobs.AppInit.Constants.PASSWORD_PATTERN;
import static com.cultofgames.AllIndiaGovernmentJobs.AppInit.Utils.showSnackBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;

import com.cultofgames.AllIndiaGovernmentJobs.databinding.ActivitySignInBinding;
import com.cultofgames.AllIndiaGovernmentJobs.databinding.HandloadingDialogLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private FirebaseAuth mAuth;
    AlertDialog loadingDialog;
    private String TAG="SignActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        loadingAlertDialog();
        binding.btnLogin.setOnClickListener(view -> {


            if (binding.etEmail.getText().toString().isEmpty()){
             showSnackBar(this,"Kindly add an email");
             binding.etEmail.requestFocus();

            }else if (binding.etPassword.getText().toString().isEmpty()){
                showSnackBar(this,"Password must be added");
                binding.etPassword.requestFocus();
            }else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString()).matches()){
                showSnackBar(this,"Enter valid email !");
                binding.etEmail.requestFocus();
            }else if (!PASSWORD_PATTERN.matcher(binding.etPassword.getText().toString()).matches()){
                showSnackBar(this,"Password is weak");
                binding.etPassword.requestFocus();
            }else{
 loadingDialog.show();
                doLogin(binding.etPassword.getText().toString(),binding.etEmail.getText().toString());
            }
        });


    }

    private void doLogin(String password, String email) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loadingDialog.dismiss();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            loadingDialog.dismiss();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            showSnackBar(SignInActivity.this,"Authentication failed !" +task.getException());

                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        Intent i = new Intent(SignInActivity.this,MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void loadingAlertDialog() {

        HandloadingDialogLayoutBinding handloadingBinding = HandloadingDialogLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(SignInActivity.this)
                .setView(handloadingBinding.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }
}