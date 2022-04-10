package app.com.zirohlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import app.com.zirohlabs.adapter.AlbumAdapter;
import app.com.zirohlabs.databinding.ActivityLoginBinding;
import app.com.zirohlabs.databinding.ActivityMainBinding;
import app.com.zirohlabs.viewModel.MainActivityViewModel;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if (!getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("userName", "").equals("")) {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            binding.loginView.setVisibility(View.GONE);
        }
        else {
            binding.loginView.setVisibility(View.VISIBLE);
        }

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equalsIgnoreCase(binding.username.getText().toString())){
                    Toast.makeText(LoginActivity.this, "PLaese Enter UserName", Toast.LENGTH_SHORT).show();
                }
                else if("".equalsIgnoreCase(binding.password.getText().toString())){
                    Toast.makeText(LoginActivity.this, "PLaese Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    setPref("userName",binding.username.getText().toString());
                    setPref("password",binding.password.getText().toString());
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private void setPref(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
