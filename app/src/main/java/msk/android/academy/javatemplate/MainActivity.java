package msk.android.academy.javatemplate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements OpenListCallBack {
    private FrameLayout container;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


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
