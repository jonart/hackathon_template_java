package msk.android.academy.javatemplate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import io.reactivex.Single;
import msk.android.academy.javatemplate.network.ApiUtils;

public class MainActivity extends AppCompatActivity {
    private FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);

        if(fragment == null){
         fragmentManager.beginTransaction()
                        .add(R.id.container, new SearchFragment())
                        .commit();
        }
    }
}
