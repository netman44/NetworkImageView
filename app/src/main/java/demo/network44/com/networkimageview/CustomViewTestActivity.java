package demo.network44.com.networkimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import demo.network44.com.networkimageview.testView.CustomAnimatorView;

public class CustomViewTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_test);
    }

    void onClickRestart(View view) {
        CustomAnimatorView view1 = (CustomAnimatorView) view;
        view1.restart();
    }
}
