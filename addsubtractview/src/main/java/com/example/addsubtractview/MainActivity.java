package com.example.addsubtractview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AddSubtractView addSubtractView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSubtractView = findViewById(R.id.add_subtract_view);

        addSubtractView.setOnNumberChangeListener(new AddSubtractView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                Toast.makeText(MainActivity.this, "当前产品数量==" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
