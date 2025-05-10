package com.example.bookstore_management;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.etSignUpUsername);
        passwordInput = findViewById(R.id.etSignUpPassword);
        Button loginButton = findViewById(R.id.loginButton);
//        TextView registerButton = findViewById(R.id.tvSignup);
//        dbHelper = new DatabaseHelper(this);

        ImageView ivTogglePassword = findViewById(R.id.ivTogglePassword);




        ivTogglePassword.setOnClickListener(v -> {
            if ( passwordInput.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ivTogglePassword.setImageResource(R.drawable.ic_eye_off);
            } else {
                passwordInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivTogglePassword.setImageResource(R.drawable.ic_eye_on);
            }
            passwordInput.setSelection( passwordInput.length()); // Giữ con trỏ ở cuối
        });


        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

//            if (checkLogin(username, password)) {
//                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//
            if(username.equals("admin") && password.equals("123456")){
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

//        registerButton.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//            startActivity(intent);
//        });
    }

//    private boolean checkLogin(String username, String password) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
//                new String[]{username, password});
//        boolean result = cursor.getCount() > 0;
//        cursor.close();
//        return result;
//    }
}
