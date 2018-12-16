package msk.android.academy.javatemplate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements OpenListCallBack {
    private FrameLayout container;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);

        if(fragment == null){
         fragmentManager.beginTransaction()
                        .add(R.id.container, new SearchFragment())
                        .commit();
        }
    }

    @Override
    public void openList() {
        fragmentManager.beginTransaction()
                       .replace(R.id.container, new ListFragment())
                       .commit();
    }

}
