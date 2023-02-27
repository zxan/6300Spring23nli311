package edu.gatech.seclass.sdpencryptor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText entryText;
    private EditText argInput1;
    private EditText argInput2;
    private EditText textEncrypted;
    private Button encryptButton;
    Button btn_viewAll;
    ListView lv_text;

    public String myString;
    public String result;
    public int arg1;
    public int arg2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entryText = (EditText) findViewById(R.id.entryTextID);
        argInput1 = (EditText) findViewById(R.id.argInput1ID);
        argInput2 = (EditText) findViewById(R.id.argInput2ID);
        textEncrypted = (EditText) findViewById(R.id.textEncryptedID);
        encryptButton = (Button) findViewById(R.id.encryptButtonID);
        argInput1.setText("1");
        argInput2.setText("1");
        btn_viewAll = findViewById(R.id.btn_viewAll);
        lv_text = findViewById(R.id.lv_text);

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<SdpModel> everyone = dataBaseHelper.getEveryone();
                //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();

                ArrayAdapter encryptArrayAdapter = new ArrayAdapter<SdpModel>(MainActivity.this, android.R.layout.simple_list_item_1, everyone);
                lv_text.setAdapter(encryptArrayAdapter);
            }
        });
    }

    public void handleClick(View view) {
        myString = "";
        myString = entryText.getText().toString();
        arg1 = Integer.parseInt(argInput1.getText().toString());
        arg2 = Integer.parseInt(argInput2.getText().toString());
        boolean a = false;
        boolean b = false;
        boolean c = false;
        if (encryptButton.isPressed()) {
            if ((myString.length() == 0) || !myString.matches(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890].*")) {
                entryText.setError("Invalid Entry Text");
                a = false;
            }
            else {
                a = true;
            }

            if ((arg1 % 2 == 0) || arg1 >= 62 || arg1 <= 0 || arg1 == 31) {
                argInput1.setError("Invalid Arg Input 1");
                b = false;
            }
            else{
                b = true;
            }

            if (arg2 >= 62 || arg2 <= 0) {
                argInput2.setError("Invalid Arg Input 2");
                c = false;
            }
            else{
                c = true;
            }
            if (a && b && c) {
                String result = encrypt(arg1, arg2);
                textEncrypted.setText(result);
            }

            SdpModel sdpmodel = new SdpModel(myString, arg1, arg2, textEncrypted.getText().toString());

            DataBaseHelper databaseHelper = new DataBaseHelper(MainActivity.this);
            //Toast.makeText(MainActivity.this, sdpmodel.toString(), Toast.LENGTH_SHORT).show();

            boolean success = databaseHelper.addOne(sdpmodel);
            Toast.makeText(MainActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();

        }
    }


    public String encrypt(int arg1, int arg2) {
        String encrypted = "";

            for (int c = 0; c < myString.length(); c++) {
                int char_num = 0;
                int char_enc = 0;
                String f = "";
                f = String.valueOf(myString.charAt(c));
                if (f.matches(".*[ABCDEFGHIJKLMNOPQRSTUVWXYZ].*")){
                    char_num = (int) myString.charAt(c) - 39;
                    char_enc = ((char_num * arg1) + arg2) % 62;
                    if (char_enc <= 25){
                        char_enc += 97;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 51) {
                        char_enc += 39;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 61){
                        char_enc -= 4;
                        encrypted += (char)char_enc;
                    }
                } else if (f.matches(".*[abcdefghijklmnopqrstuvwxyz].*")) {
                    char_num = (int) myString.charAt(c) - 97;
                    char_enc = ((char_num * arg1) + arg2) % 62;
                    if (char_enc <= 25){
                        char_enc += 97;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 51) {
                        char_enc += 39;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 61){
                        char_enc -= 4;
                        encrypted += (char)char_enc;
                    }
                } else if (f.matches(".*[1234567890].*")) {
                    char_num = (int) myString.charAt(c) + 4;
                    char_enc = ((char_num * arg1) + arg2) % 62;
                    if (char_enc <= 25){
                        char_enc += 97;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 51) {
                        char_enc += 39;
                        encrypted += (char)char_enc;
                    } else if (char_enc <= 61){
                        char_enc -= 4;
                        encrypted += (char)char_enc;
                    }
                }
                else{
                    encrypted += myString.charAt(c);
                }
            }
            return encrypted;

    }
}