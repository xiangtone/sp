package com.lulu.player.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lulu.player.R;
import com.lulu.player.model.Video;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zxc
 * @time 2016/9/26 0026下午 2:40
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;

    private List<Video> mVideo;

    public GridViewAdapter() {
        super();
    }

    public GridViewAdapter(Context mContext, List<Video> mVideo) {
        super();
        this.mContext = mContext;
        this.mVideo = mVideo;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mVideo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mVideo.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_view, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        String name = mVideo.get(position).getName() + "";
        String imgUrl = mVideo.get(position).getImgUrl() + "";
        //
        mHolder.title.setText(mVideo.get(position).getName());
        Glide.with(mContext).load(imgUrl == null || imgUrl.equals("") ? R.drawable.black_bg : imgUrl)
                .placeholder(R.drawable.black_bg)
                .error(R.drawable.exit_mm)
                .into(mHolder.img);

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.img_grid_view)
        ImageView img;

        @Bind(R.id.title_grid_view)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
