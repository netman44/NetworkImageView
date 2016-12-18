package demo.network44.com.networkimageview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import demo.network44.com.networkimageview.card.ScrollingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCustomView(View view) {
        Intent intent = new Intent(this, CustomViewTestActivity.class);
        startActivity(intent);
    }

    public void onClickPaintView(View view) {
        Intent intent = new Intent(this, PaintTestActivity.class);
        startActivity(intent);
    }

    public void scrollFlagsScroll(View view) {
        Intent intent = new Intent(this, ScrollingActivity.class);
        startActivity(intent);
    }


}
