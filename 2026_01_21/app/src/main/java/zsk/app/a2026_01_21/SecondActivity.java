package zsk.app.a2026_01_21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
    public void onClick(View view) {
        EditText name = findViewById(R.id.name);
        String vName = name.getText().toString();
        EditText mail = findViewById(R.id.mail);
        String vMail = mail.getText().toString();
        EditText pass = findViewById(R.id.password);
        String vPass = pass.getText().toString();

        if(!vName.isEmpty() && vMail.contains("@") && vPass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")){
            Intent intent = new Intent();
            intent.putExtra("imie", vName);
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            String message = "Błędne dane: ";
            if(vName.isEmpty()){
                message += "Puste imie; ";
            }
            if(!vMail.contains("@")){
                message += "Błędny mail; ";
            }
            if(!vPass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")){
                message += "Błędne hasło; ";
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}