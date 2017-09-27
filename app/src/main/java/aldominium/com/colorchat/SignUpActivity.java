package aldominium.com.colorchat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = (EditText) findViewById(R.id.signupUsername);
        passwordEditText = (EditText) findViewById(R.id.signupPassword);


        firebaseAuth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null){
                    Toast.makeText(SignUpActivity.this, "El usuario fue creado", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SignUpActivity.this, "El usuario salio  de la sesion", Toast.LENGTH_SHORT).show();
                }

            }
        };


    }

    public void signUp(View view) {

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Hubo un error al crear usuario", Toast.LENGTH_LONG).show();
                }else {

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification();

                }

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null)
            firebaseAuth.removeAuthStateListener(authStateListener);
    }




}
