package com.example.shoppingmall.activity.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.app.GoodsInfoActivity;
import com.example.shoppingmall.activity.home.bean.HomeResultBean;
import com.example.shoppingmall.activity.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.transformer.RotateYTransformer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;

public class HomeFragmentAdapter extends RecyclerView.Adapter {

    public static final int BANNER = 0;
    public static final int CHANNEL = 1;
    public static final int ACT = 2;
    public static final int SECKILL = 3;
    public static final int RECOMMEND = 4;
    public static final int HOT = 5;

    private int currentType = BANNER;

    private final Context context;
    private final HomeResultBean.ResultBean resultBean;
    private final LayoutInflater inflater;          //用来初始化布局


    public HomeFragmentAdapter(Context context, HomeResultBean.ResultBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 相当于getView
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType 当前的类型
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(context, inflater.inflate(R.layout.banner_home_view, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(context, inflater.inflate(R.layout.channel_home_view, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(context, inflater.inflate(R.layout.act_home_view, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(context, inflater.inflate(R.layout.seckill_home_view, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(context, inflater.inflate(R.layout.recommend_home_view, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(context, inflater.inflate(R.layout.hot_home_view, null));
        }
        return null;
    }

    /**
     * 相当于getview中的绑定数据模块
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    /**
     * 得到类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    /**
     * 共有多少个item
     *
     * @return
     */
    @Override
    public int getItemCount() {
        //开发过程从1---》2
        return 6;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private Banner banner;

        public BannerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            banner = itemView.findViewById(R.id.banner);

        }

        public void setData(List<HomeResultBean.ResultBean.BannerInfoBean> banner_info) {

            //设置Banner的数据
            /*List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                imagesUrl.add(Constants.IMG_URL + banner_info.get(i).getImage());
            }*/

            //—————————————————————————如果你想偷懒，而又只是图片轮播————————————————————————
//            banner.setPageTransformer(new AlphaPageTransformer());
//            banner.setPageTransformer(new DepthPageTransformer());
            banner.setPageTransformer(new RotateYTransformer());       //设置切换效果
            banner.setAdapter(new BannerImageAdapter<HomeResultBean.ResultBean.BannerInfoBean>(banner_info) {
                @Override
                public void onBindView(BannerImageHolder holder, HomeResultBean.ResultBean.BannerInfoBean data, int position, int size) {
                    Log.e(TAG, "onBindView: image:" + Constants.IMG_URL + data.getImage());
                    holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    //图片加载自己实现
                    Glide.with(context)
                            .load(Constants.IMG_URL + data.getImage())
                            //.apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(holder.imageView);
                }
            })
                    .setLoopTime(5000)
                    .addBannerLifecycleObserver(null)//添加生命周期观察者
                    .setIndicator(new CircleIndicator(context));

            //更多使用方法仔细阅读文档，或者查看demo

            //设置点击事件
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    Toast.makeText(context, "点击了第" + position + "个，" + banner_info.get(position).getValue().getUrl(), Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });

        }
    }

    /**
     * 跳转到商品详情页面
     */
    private void startGoodsInfoActivity() {
        Intent intent = new Intent(context, GoodsInfoActivity.class);
        context.startActivity(intent);
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private GridView gridView;
        private ChannelAdapter adapter;

        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            gridView = itemView.findViewById(R.id.gv_channel);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void setData(List<HomeResultBean.ResultBean.ChannelInfoBean> channel_info) {
            adapter = new ChannelAdapter(context, channel_info);
            gridView.setAdapter(adapter);
        }
    }

    private class ActViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private Banner banner;

        public ActViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            banner = itemView.findViewById(R.id.banner_act);
        }

        public void setData(List<HomeResultBean.ResultBean.ActInfoBean> act_info) {

            banner.setPageTransformer(new DepthPageTransformer());
            banner.setAdapter(new BannerImageAdapter<HomeResultBean.ResultBean.ActInfoBean>(act_info) {
                @Override
                public void onBindView(BannerImageHolder holder, HomeResultBean.ResultBean.ActInfoBean data, int position, int size) {
                    Log.e(TAG, "onBindView: image:" + Constants.IMG_URL + data.getIcon_url());
                    holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(context)
                            .load(Constants.IMG_URL + data.getIcon_url())
                            .into(holder.imageView);
                }
            })
                    .addBannerLifecycleObserver(null)//添加生命周期观察者
                    .setIndicator(new CircleIndicator(context));

            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    Toast.makeText(context, "点击了第" + position + "个，" + act_info.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    private class SeckillViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView tvMoreSeckill;
        private TextView tvTimeSeckill;
        private RecyclerView recyclerView;
        private SeckillAdapter adapter;
        /**
         * 想差多少事件--毫秒
         */
        private long dt;
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        public SeckillViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            tvMoreSeckill = itemView.findViewById(R.id.tv_more_seckill);
            tvTimeSeckill = itemView.findViewById(R.id.tv_time_seckill);
            recyclerView = itemView.findViewById(R.id.rv_seckill);

        }

        public void setData(HomeResultBean.ResultBean.SeckillInfoBean seckill_info) {

            adapter = new SeckillAdapter(context, seckill_info.getList());
            recyclerView.setAdapter(adapter);
            //设置布局管理器
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

            //设置item的点击事件
            adapter.setOnSeckillRecyclerView(new SeckillAdapter.OnSeckillRecyclerView() {
                @Override
                public void OnItemClick(int position) {
                    Toast.makeText(context, position + "--" + seckill_info.getList().get(position).getName(), Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });

            //秒杀倒计时--毫秒
            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());
            tvTimeSeckill.setText(simpleDateFormat.format(new Date(dt - 8 * 60 * 60 * 1000)));
            handler.postDelayed(runnable, 1000);//每秒执行一次runnable.

            tvMoreSeckill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "查看更多seckill", Toast.LENGTH_SHORT).show();
                }
            });

        }

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //要做的事情

                dt = dt - 1000;
                tvTimeSeckill.setText(simpleDateFormat.format(new Date(dt - 8 * 60 * 60 * 1000)));

                if (dt < 1000) {
                    tvTimeSeckill.setText("00:00:00");
                    handler.removeCallbacks(this);
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }


    private class RecommendViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private TextView tvMoreRecommend;
        private GridView gvRecommend;
        private RecommendGridViewAdapter adapter;

        public RecommendViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            tvMoreRecommend = itemView.findViewById(R.id.tv_more_recommend);
            gvRecommend = itemView.findViewById(R.id.gv_recommend);

            gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, position + ":" + resultBean.getRecommend_info().get(position).getName(), Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });

            tvMoreRecommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "查看更多recommend", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<HomeResultBean.ResultBean.RecommendInfoBean> recommend_info) {
            adapter = new RecommendGridViewAdapter(context, recommend_info);
            gvRecommend.setAdapter(adapter);
        }
    }

    private class HotViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private TextView tvMoreHot;
        private GridView gvHot;
        private HotGridViewAdapter adapter;

        public HotViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            tvMoreHot = itemView.findViewById(R.id.tv_more_hot);
            gvHot = itemView.findViewById(R.id.gv_hot);

            gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, position + ":" + resultBean.getHot_info().get(position).getName(), Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });

            tvMoreHot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "查看更多hot", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<HomeResultBean.ResultBean.HotInfoBean> hot_info) {
            adapter = new HotGridViewAdapter(context, hot_info);
            gvHot.setAdapter(adapter);
        }
    }
}
