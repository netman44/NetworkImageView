package demo.network44.com.networkimageview.card;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.network44.com.networkimageview.R;

/**
 * 在此写用途
 *
 * @author: zhiwei
 * @date: 2016-12-15 17:23
 * @version: 9.1.0
 */
public class ScrollViewFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nested_scrolling, container, false);
        return view;
    }
}
