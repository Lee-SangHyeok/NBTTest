package com.nbt.oopssang.nbttest.control;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import com.nbt.oopssang.nbttest.R;
import com.nbt.oopssang.nbttest.WebViewActivity;
import com.nbt.oopssang.nbttest.data.Result;
import com.nbt.oopssang.nbttest.view.STGVImageView;
import com.nbt.oopssang.nbttest.view.TimesTitleView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class STGVAdapter extends BaseAdapter {
    private Context mContext;
    private Application mAppContext;
    private List<Result> mItems;

    private final int IMAGE_URL_POSITION = 3;
    // 색상은 임의로 지정
    private final String[] TITLE_COLOR = {
            "#FF5555",
            "#F06161",
            "#CC3D3D",
            "#FF5E00",
            "#EDC6C6",
            "#FFBB00"
    };

    public STGVAdapter(Context context, Application app) {
        mContext = context;
        mAppContext = app;
    }

    public void setData(List<Result> data) {
        mItems = data;
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public Result getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        final Result item = getItem(position);

        if (convertView == null) {
            Holder holder = new Holder();
            view = View.inflate(mContext, R.layout.cell_stgv, null);
            holder.img_content = (STGVImageView) view.findViewById(R.id.img_content);
            holder.title = (TimesTitleView) view.findViewById(R.id.title);

            view.setTag(holder);
        } else {
            view = convertView;
        }

        Holder holder = (Holder) view.getTag();

        holder.title.setUrl(item.getUrl());
        holder.title.setText(item.getTitle());
        holder.title.setBackgroundColor(Color.parseColor(TITLE_COLOR[position % 6]));

        if (item.getMultimedia().size() > IMAGE_URL_POSITION) {
            holder.img_content.setVisibility(View.VISIBLE);
            String url = item.getMultimedia().get(IMAGE_URL_POSITION).getUrl();
            holder.img_content.mHeight = item.getMultimedia().get(IMAGE_URL_POSITION).getHeight();
            holder.img_content.mWidth = item.getMultimedia().get(IMAGE_URL_POSITION).getWidth();
            Picasso.with(mAppContext).load(url).into(holder.img_content);
        } else {
            holder.img_content.setVisibility(View.GONE);
        }

        holder.img_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ((TimesTitleView) v).getUrl();
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TIMES_URL_EXTRA, url);
                intent.putExtra(WebViewActivity.CHECK_PLAY_URL_EXTRA, WebViewActivity.NEWYORKTIMES_CONTAINS_URL);
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    class Holder {
        STGVImageView img_content;
        TimesTitleView title;
    }

    public static class HeaderFooterListAdapter implements WrapperListAdapter {
        private ListAdapter mAdapter;
        View mHeaderView;
        View mFooterView;
        boolean mAreAllFixedViewsSelectable;
        private int headerSize = -1;
        private int footerSize = -1;

        public static int TYPE_COUNT_WITHOUT_REFRESHABLE_LIST = 2;
        public static int HEADER_TYPE = 0;
        public static int FOOTER_TYPE = HEADER_TYPE + 1;
        public static int REFRESHABLE_LIST_TYPE = FOOTER_TYPE + 1;

        public HeaderFooterListAdapter(View headerView, View footerView, ListAdapter adapter) {
            mAdapter = adapter;

            if (headerView == null) {
                headerSize = 0;
            } else {
                headerSize = 1;
                mHeaderView = headerView;
            }

            if (footerView == null) {
                footerSize = 0;
            } else {
                footerSize = 1;
                mFooterView = footerView;
            }

            mAreAllFixedViewsSelectable = true;
        }

        @Override
        public ListAdapter getWrappedAdapter() {
            return mAdapter;
        }

        @Override
        public boolean areAllItemsEnabled() {
            if (mAdapter != null) {
                return mAreAllFixedViewsSelectable && mAdapter.areAllItemsEnabled();
            } else {
                return true;
            }
        }

        @Override
        public boolean isEnabled(int position) {
            if (position < headerSize) {
                return true;
            }

            int adjPosition = position - headerSize;
            if (mAdapter != null) {
                if (adjPosition < mAdapter.getCount()) {
                    return mAdapter.isEnabled(adjPosition);
                }
            }

            return false;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            if (mAdapter != null) {
                mAdapter.registerDataSetObserver(observer);
            }
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (mAdapter != null) {
                mAdapter.unregisterDataSetObserver(observer);
            }
        }

        @Override
        public int getCount() {
            if (mAdapter != null) {
                return headerSize + footerSize + mAdapter.getCount();
            } else {
                return headerSize + footerSize;
            }
        }

        @Override
        public Object getItem(int position) {
            if (position < headerSize) {
                return mHeaderView;
            }

            final int adjPosition = position - headerSize;
            int adapterCount = 0;
            if (mAdapter != null) {
                adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItem(adjPosition);
                }
            }

            return mFooterView;
        }

        @Override
        public long getItemId(int position) {
            if (mAdapter != null && position >= headerSize) {
                int adjPosition = position - headerSize;
                int adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public boolean hasStableIds() {
            if (mAdapter != null) {
                return mAdapter.hasStableIds();
            }
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position < headerSize) {
                return mHeaderView;
            }

            final int adjPosition = position - headerSize;
            int adapterCount = 0;
            if (mAdapter != null) {
                adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getView(adjPosition, convertView, parent);
                }
            }

            return mFooterView;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < headerSize) {
                return HEADER_TYPE;
            }

            if (mAdapter != null && position >= headerSize) {
                int adjPosition = position - headerSize;
                int adapterCount = mAdapter.getCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemViewType(adjPosition) + REFRESHABLE_LIST_TYPE;
                }
            }

            return FOOTER_TYPE;
        }

        @Override
        public int getViewTypeCount() {
            if (mAdapter != null) {
                return mAdapter.getViewTypeCount() + TYPE_COUNT_WITHOUT_REFRESHABLE_LIST;
            }
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return mAdapter == null || mAdapter.isEmpty();
        }
    }
}
