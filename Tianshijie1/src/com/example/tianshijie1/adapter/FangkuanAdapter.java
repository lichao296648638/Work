package com.example.tianshijie1.adapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.WebviewActivity;
import com.example.tianshijie1.bean.Zijin;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;

public class FangkuanAdapter extends BaseAdapter {
    private List<Zijin> list;
    private Context context;
    private int xia = 0;
    Handler handler;
    String info;

    public FangkuanAdapter(Context context, List<Zijin> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.fangkuanqueren_item, null);
            viewHolder.tv_xiangmu = (TextView) convertView
                    .findViewById((R.id.tv_xiangmu));
            viewHolder.tv_zhuangtai = (TextView) convertView
                    .findViewById((R.id.tv_zhuangtai));
            viewHolder.tv_shijian = (TextView) convertView
                    .findViewById((R.id.tv_shijian));
            viewHolder.tv_jine = (TextView) convertView
                    .findViewById((R.id.tv_jine));
            viewHolder.tv_caozuo = (TextView) convertView
                    .findViewById((R.id.tv_caozuo));
            viewHolder.ll_fangkuan = (LinearLayout) convertView
                    .findViewById((R.id.ll_fangkuan));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Zijin zijin = list.get(position);
        viewHolder.tv_caozuo.setText(zijin.getCaozuo());
        viewHolder.tv_jine.setText(zijin.getJine());
        viewHolder.tv_xiangmu.setText(zijin.getXiangmu());
        viewHolder.tv_zhuangtai.setText(zijin.getZhuangtia());
        viewHolder.tv_shijian.setText(getStrTime(zijin.getShijian()));
        final String status = zijin.getStatus();
        final String t_status = zijin.getT_status();
        final String loanstatus = zijin.getLoanstatus();
        final String agree_fangkuan = zijin.getAgree_fangkuan();
        if (status.equals("500")) {
            if (t_status.equals("3")) {
                viewHolder.tv_caozuo.setText("申请退款");
                viewHolder.tv_caozuo.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.ll_fangkuan.setBackgroundResource(R.drawable.shape4);
            } else if (t_status.equals("0")) {
                viewHolder.tv_caozuo.setText("退款审核中");
                viewHolder.tv_caozuo.setTextColor(Color.parseColor("#d1d1d1"));
                viewHolder.ll_fangkuan.setBackgroundResource(R.drawable.shape3);
            } else if (t_status.equals("1")) {
                viewHolder.tv_caozuo.setText("已退款");
                viewHolder.tv_caozuo.setTextColor(Color.parseColor("#d1d1d1"));
                viewHolder.ll_fangkuan.setBackgroundResource(R.drawable.shape3);
            }
        } else if (status.equals("600")) {

            if (loanstatus.equals("0")) {
                /**
                 * 功能添加Start 需求编号XQ1
                 */
                if (agree_fangkuan.equals("0")) {
                    viewHolder.tv_caozuo.setText("确认放款");
                    viewHolder.tv_caozuo.setTextColor(Color.parseColor("#ffffff"));
                    viewHolder.ll_fangkuan.setBackgroundResource(R.drawable.shape8);
                } else if (agree_fangkuan.equals("1")) {
                    viewHolder.tv_caozuo.setText("确认放款");
                    viewHolder.tv_caozuo.setTextColor(Color.parseColor("#ffffff"));
                    viewHolder.ll_fangkuan.setBackgroundResource(R.drawable.shape4);
                }
                //功能添加End
            } else if (loanstatus.equals("1")) {
                viewHolder.tv_caozuo.setText("已放款");
                viewHolder.tv_caozuo.setTextColor(Color.parseColor("#d1d1d1"));
                viewHolder.ll_fangkuan.setBackgroundResource(R.drawable.shape3);
            }
        } else if (status.equals("700")) {
            if (loanstatus.equals("0")) {
                viewHolder.tv_caozuo.setText("申请退款");
                viewHolder.tv_caozuo.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.ll_fangkuan.setBackgroundResource(R.drawable.shape4);
            } else if (loanstatus.equals("2")) {
                viewHolder.tv_caozuo.setText("已退款");
                viewHolder.tv_caozuo.setTextColor(Color.parseColor("#d1d1d1"));
                viewHolder.ll_fangkuan.setBackgroundResource(R.drawable.shape3);
            }
        }
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case 1:
                        viewHolder.tv_caozuo.setText("退款审核中");
                        viewHolder.tv_caozuo.setTextColor(Color
                                .parseColor("#d1d1d1"));
                        viewHolder.ll_fangkuan
                                .setBackgroundResource(R.drawable.shape3);
                        break;
                    case 2:
                        viewHolder.tv_caozuo.setText("已放款");
                        viewHolder.tv_caozuo.setTextColor(Color
                                .parseColor("#d1d1d1"));
                        viewHolder.ll_fangkuan
                                .setBackgroundResource(R.drawable.shape3);
                        break;
                    case 3:
                        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        viewHolder.tv_caozuo.setText("已退款");
                        viewHolder.tv_caozuo.setTextColor(Color
                                .parseColor("#d1d1d1"));
                        viewHolder.ll_fangkuan
                                .setBackgroundResource(R.drawable.shape3);
                        break;
                }
                super.handleMessage(msg);
            }

        };
        viewHolder.ll_fangkuan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (status.equals("500")) {
                    if (t_status.equals("3")) {
                        new Thread() {
                            public void run() {
                                NameValuePair pair2 = new BasicNameValuePair(
                                        "uid", LoginActivity.UID);
                                NameValuePair pair1 = new BasicNameValuePair(
                                        "investid", zijin.getCid());
                                NameValuePair pair3 = new BasicNameValuePair(
                                        "pid", zijin.getPid());

                                final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                                pairList.add(pair2);
                                pairList.add(pair1);
                                pairList.add(pair3);
                                PostUtil postUtil = new PostUtil();
                                String url1 = "http://wap.tianshijie.com.cn/appuser/accountrefund";
                                String result = postUtil.DoPostNew(pairList,
                                        url1);
                                /**
                                 * BugStart
                                 * Bug编号：BUG4
                                 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
                                 * 修复人：李超
                                 * 修复日期：2015-10-23
                                 */
                                if(result == null){
                                    CToast.makeText(context, context.getResources().getText(R.string.toast_error_network), 3000).show();
                                    return;
                                }
                                //BugEnd
                                try {
                                    JSONObject jsonObject = new JSONObject(
                                            result);
                                    info = jsonObject.getString("info");
                                    if (info.equals("提交申请退款成功")) {
                                        Message message = new Message();
                                        message.what = 1;
                                        handler.sendMessage(message);
                                    } else {
                                        Message message = new Message();
                                        message.what = 3;
                                        handler.sendMessage(message);
                                    }

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    }
                }
                if (status.equals("700")) {
                    if (loanstatus.equals("0")) {
                        new Thread() {
                            public void run() {
                                String uri = "http://wap.tianshijie.com.cn/apppay/refund";
                                String uid = "uid/" + LoginActivity.UID;
                                String cid = "/cid/" + zijin.getCid();
                                String uriAPI = uri + uid + cid;
                                /* 建立HTTP Get对象 */
                                HttpGet httpRequest = new HttpGet(uriAPI);

                                try {
                                    HttpResponse httpResponse = new DefaultHttpClient()
                                            .execute(httpRequest);
									/* 若状态码为200 ok */
                                    if (httpResponse.getStatusLine()
                                            .getStatusCode() == 200) {
										/* 读 */
                                        String strResult = EntityUtils
                                                .toString(httpResponse
                                                        .getEntity());
                                        JSONObject jsonObject = new JSONObject(
                                                "strResult");
                                        info = jsonObject.getString("info");
                                        if (info.equals("退款成功")) {
                                            Message message = new Message();
                                            message.what = 4;
                                            handler.sendMessage(message);
                                        } else {
                                            Message message = new Message();
                                            message.what = 3;
                                            handler.sendMessage(message);
                                        }

                                        Log.v("result", strResult);
                                    } else {

                                    }

                                } catch (ClientProtocolException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    }
                }
                if (status.equals("600")) {
                    /**
                     * 功能添加Start
                     * 需求编号：XQ1
                     */
                    if (loanstatus.equals("0") && agree_fangkuan.equals("1")) {
                    //功能添加End
                        String uri = "http://wap.tianshijie.com.cn/apppay/tocptransaction/";
                        String uid = "uid/" + LoginActivity.UID;
                        String cid = "/cid/" + zijin.getCid();
                        String uriAPI = uri + uid + cid;
                        Intent intent = new Intent();
                        intent.setClass(context, WebviewActivity.class);
                        intent.putExtra("title", "确认放款");
                        intent.putExtra("url", uriAPI);
                        context.startActivity(intent);
                    }
                }
            }
        });
        return convertView;
    }

    public String getStrTime(String time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = null;
        if (time.equals("")) {
            return "";
        }
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long loc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(loc_time * 1000L));
        return re_StrTime;
    }

    public class ViewHolder {
        private TextView tv_xiangmu, tv_caozuo, tv_jine, tv_shijian,
                tv_zhuangtai;
        private LinearLayout ll_fangkuan;

    }
}
