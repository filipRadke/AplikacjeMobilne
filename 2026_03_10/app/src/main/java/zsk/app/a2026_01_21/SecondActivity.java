package zsk.app.a2026_01_21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
    public void saveJson(Context context, String key, String value) {

        HashMap<String, String> map = new HashMap<>();
        map.put(key, value);

        Gson gson = new Gson();
        String json = gson.toJson(map);

        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(key, json);
        editor.apply();
    }

    public String getJsonValue(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = prefs.getString(key, null);

        if (json == null) return null;

        Gson gson = new Gson();
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);

        return map.get(key);
    }

    public void onClick(View view) {
        EditText eName = findViewById(R.id.name);
        String name = eName.getText().toString();
        EditText ePassword = findViewById(R.id.password);
        String password = ePassword.getText().toString();
        EditText eRPassword = findViewById(R.id.passwordRepeat);
        String passwordRepeat = eRPassword.getText().toString();
        EditText ePesel = findViewById(R.id.PESEL);
        String pesel = ePesel.getText().toString();

        boolean nameOK = false, passwordOK = false, peselOK = false;

        if(!name.isEmpty()){
            String userCheck = getJsonValue(this,name);
            if (userCheck == null){
                nameOK = true;
            }
        }

        if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$") && password.equals(passwordRepeat)) {
            passwordOK = true;
        }

        if(pesel.length() == 11){
            int sum = 0;
            int[] values = new int[10];

            for (int i=0; i<10; i++){
                values[i] = Integer.parseInt(String.valueOf(pesel.charAt(i)));
            }

            sum += values[0] + values[4] + values[8] + values[1] * 3 + values[5] * 3 + values[9] * 3 + values[2] * 7 + values[6] * 7 + values[3] * 9 + values[7] * 9;
            sum = (10 - (sum % 10)) % 10;

            if(Integer.parseInt(String.valueOf(pesel.charAt(10))) == sum){
                peselOK = true;
            }
        }

        if(nameOK && passwordOK && peselOK){
            saveJson(this,name,password);

            Intent intent = new Intent();
            intent.putExtra("imie", name);
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            TextView errors = findViewById(R.id.errors);

            String message = "Błędne dane:\n";
            if(!nameOK){
                message += "Pusty login/ taki login już istnieje\n";
            }
            if(!passwordOK){
                message += "Błędne hasło\n";
            }
            if(!peselOK){
                message += "Niepoprawny PESEL\n";
            }

            errors.setText(message);
        }
    }
}