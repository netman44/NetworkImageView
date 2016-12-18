package demo.network44.com.networkimageview.card;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demo.network44.com.networkimageview.R;

/**
 * 在此写用途
 *
 * @author: zhiwei
 * @date: 2016-12-15 17:11
 * @version: 9.1.0
 */
public class RececViewFragment extends Fragment {
    private static final String TAG = "RececViewFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_layout, container, false);
        return view;
    }


    private List<String> getListData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("我是一个item i=" + i);
        }
        return list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        MAdapter mAdapter = new MAdapter(getListData());
        recyclerView.setAdapter(mAdapter);
    }

    private class MViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public MViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.recycle_item);
        }

        void bindView(String text) {
            mTextView.setText(text);
        }

    }

    private class MAdapter extends RecyclerView.Adapter<MViewHolder> {
        List<String> mdata = null;

        public MAdapter(List<String> list) {
            mdata = list;
        }

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rececy_item, parent, false);
            return new MViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MViewHolder holder, int position) {
            holder.bindView(getItem(position));
        }

        public String getItem(int position) {
            return mdata.get(position);
        }

        @Override
        public int getItemCount() {
            return mdata != null ? mdata.size() : 0;
        }
    }
}
