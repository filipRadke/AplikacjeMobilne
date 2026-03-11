package zsk.app.a2026_01_21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    int login = 1, register = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void registerClick(View view) {
        startActivityForResult(new Intent(this, SecondActivity.class), register);
    }
    public void loginClick(View view) {
        EditText eName = findViewById(R.id.name);
        String name = eName.getText().toString();
        EditText ePassword = findViewById(R.id.password);
        String password = ePassword.getText().toString();

        String checkPassword = getJsonValue(this, name);

        if(password.equals(checkPassword)){
            startActivityForResult(new Intent(this, ThirdActivity.class), login);
        }
    }
    public String getJsonValue(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = prefs.getString(key, null);

        if (json == null) return null;

        Gson gson = new Gson();
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);

        return map.get(key);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == register && resultCode == RESULT_OK) {
            String imie = data.getStringExtra("imie");
            Toast.makeText(this, "Zarejestrowano użytkownika " + imie, Toast.LENGTH_SHORT).show();

            EditText name = findViewById(R.id.name);
            name.setText(imie);
            EditText password = findViewById(R.id.password);
            password.setText("");
        }
        if (requestCode == login){
            Toast.makeText(this, "Wylogowano", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}