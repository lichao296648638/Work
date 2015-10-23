package com.example.tianshijie1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import DBManager.DBManager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.User;
import com.example.tianshijie1.citygundong.CityPicker;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.FileUtil;
import com.example.tianshijie1.util.PostUtil;
import com.example.tianshijie1.util.UploadUtil;
import com.example.tianshijie1.util.UploadUtil.OnUploadProcessListener;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;

public class MineXinxiActivity extends Activity implements
        OnUploadProcessListener {
    public SharedPreferences sharedPrefrences;
    public Editor editor;
    private RelativeLayout rl_touxiang, rl_shenfen, rl_name, rl_shenfenzheng,
            rl_realname, rl_sex, rl_email, rl_city;
    private TextView tv_shenfen, tv_name, tv_shenfenzheng, tv_realname, tv_sex,
            tv_email, tv_city;
    private ImageView iv_touxiang, imageView, iv_shenfenzheng, iv_realname,
            iv_backxinxi;
    String result;
    ImageLoader imageLoader;
    /**
     * 获取到的图片路径
     */
    private static final String TAG = "uploadImage";
    private String picPath;
    private Uri photoUri;
    private Context mContext;
    private User user;
    public DBManager dbHelper;
    private SQLiteDatabase database;
    /***
     * 这里的这个URL是我服务器的javaEE环境URL
     */
    private static String requestURL = "http://wap.tianshijie.com.cn/appuser/resetphoto";
    /**
     * 去上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2; //
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    private static final int UPLOAD_IN_PROCESS = 5;
    /**
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

    /***
     * 从Intent获取图片路径的KEY
     */
    public static String KEY_PHOTO_PATH = "photo_path";
    /**
     * 在家两个ImageLoader
     */
    DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoaderConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_mine_xinxi);
        sharedPrefrences = this.getSharedPreferences("Xinxi",
                Activity.MODE_PRIVATE);
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.zhanwei_fang)
                        // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.zhanwei_fang)
                        // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.zhanwei_fang)
                        // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)
                        // 是否緩存都內存中
                .cacheOnDisc(true)
                        // 是否緩存到sd卡上
                .displayer(new RoundedBitmapDisplayer(360, false)).build();

        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        rl_touxiang = (RelativeLayout) findViewById(R.id.rl_touxiang);
        rl_shenfen = (RelativeLayout) findViewById(R.id.rl_shenfen);
        rl_name = (RelativeLayout) findViewById(R.id.rl_name);
        rl_shenfenzheng = (RelativeLayout) findViewById(R.id.rl_shenfenzheng);
        rl_realname = (RelativeLayout) findViewById(R.id.rl_realname);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_email = (RelativeLayout) findViewById(R.id.rl_email);
        rl_city = (RelativeLayout) findViewById(R.id.rl_city);
        tv_shenfen = (TextView) findViewById(R.id.tv_shenfen);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_shenfenzheng = (TextView) findViewById(R.id.tv_shenfenzheng);
        tv_realname = (TextView) findViewById(R.id.tv_realname);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_city = (TextView) findViewById(R.id.tv_city);
        iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
        iv_shenfenzheng = (ImageView) findViewById(R.id.iv_shenfenzheng);
        iv_realname = (ImageView) findViewById(R.id.iv_realname);
        iv_backxinxi = (ImageView) findViewById(R.id.iv_backxinxi);
        iv_backxinxi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MineXinxiActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("Xinxi",
                Context.MODE_PRIVATE);
        String shenfen = sharedPreferences.getString("tv_shenfen", "null");
        if (!shenfen.equals("null")) {
            tv_shenfen.setText(shenfen);
        }
        String name = sharedPreferences.getString("tv_name", "null");
        if (!name.equals("null")) {
            tv_name.setText(name);
        }
        String shenfenzheng = sharedPreferences.getString("tv_shenfenzheng",
                "null");
        if (!shenfenzheng.equals("-1")) {
            tv_shenfenzheng.setText(shenfenzheng);
        }
        String realname = sharedPreferences.getString("tv_realname", "null");
        if (!realname.equals("")) {
            tv_realname.setText(realname);
        }
        String sex = sharedPreferences.getString("tv_sex", "null");
        if (!sex.equals("-1")) {
            tv_sex.setText(sex);
        }
        String email = sharedPreferences.getString("tv_email", "null");
        if (!email.equals("null")) {
            tv_email.setText(email);
            Log.v("email", email);
        }
        String city = sharedPreferences.getString("tv_city", "null");
        if (!city.equals("null")) {
            tv_city.setText(city);
        }
        mypost();
        rl_city.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final AlertDialog dlg = new AlertDialog.Builder(
                        MineXinxiActivity.this).create();
                dlg.setCancelable(true);
                dlg.show();
                Window window = dlg.getWindow();
                // *** 主要就是在这里实现这种效果的.
                // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
                window.setContentView(R.layout.city_xuanze);
                // 为确认按钮添加事件,执行退出应用操作
                final CityPicker citypicker = (CityPicker) window
                        .findViewById(R.id.citypicker);
                TextView tv_quxiao = (TextView) window
                        .findViewById(R.id.tv_quxiao);
                TextView tv_wanchen = (TextView) window
                        .findViewById(R.id.tv_wanchen);
                dlg.setCanceledOnTouchOutside(true);
                tv_quxiao.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dlg.dismiss();
                    }
                });
                tv_wanchen.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        /**
                         * BugStart
                         * Bug编号：BUG1
                         * BUG描述：我的资料页面选取所在城市，默认北京情况下从县切换到市辖区，程序崩溃
                         * 修复日期：2015-10-21
                         * 修复人：李超
                         */
                        String jie = citypicker.getCity_string();
                        String str = null;
                        if (jie.equals("市辖区") || jie.equals("县") || jie.equals("市")) {
                            str = citypicker.proviceString();
                            Log.i("lichao", "str1" + str);
                        }else{
                            str = jie;
                        }
                         str = str.substring(0, str.length() - 1);
                        //EndBug
                        tv_city.setText(str);
                        dbHelper = new DBManager(MineXinxiActivity.this);
                        dbHelper.openDatabase();
                        dbHelper.closeDatabase();
                        database = SQLiteDatabase.openOrCreateDatabase(
                                DBManager.DB_PATH + "/" + DBManager.DB_NAME,
                                null);
                        Cursor cur = database.query("Area", null, "area_name="
                                + "'" + str + "'", null, null, null, null);
                        final List<String> aliList = new ArrayList<String>();
                        while (cur.moveToNext()) {
                            String a = cur.getString(cur
                                    .getColumnIndex("area_id"));
                            aliList.add(a);
                            Log.v("a", a);

                        }
                        new Thread() {
                            public void run() {
                                NameValuePair pair1 = new BasicNameValuePair(
                                        "uid", LoginActivity.UID);
                                NameValuePair pair2 = new BasicNameValuePair(
                                        "filed", "cityid");
                                NameValuePair pair3 = new BasicNameValuePair(
                                        "value", aliList.get(0));
                                final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                                pairList.add(pair1);
                                pairList.add(pair2);
                                pairList.add(pair3);
                                PostUtil postUtil = new PostUtil();
                                String url1 = "http://wap.tianshijie.com.cn/appuser/update";
                                result = postUtil.DoPostNew(pairList, url1);
                                /**
                                 * BugStart
                                 * Bug编号：BUG4
                                 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
                                 * 修复人：李超
                                 * 修复日期：2015-10-23
                                 */
                                if(result == null){
                                    CToast.makeText(MineXinxiActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
                                    return;
                                }
                                //BugEnd
                                Log.v("url", "1" + result);

                                try {
                                    JSONObject jsonObject = new JSONObject(
                                            result);
                                    String info = jsonObject.getString("info");
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        dlg.dismiss();
                    }

                });

            }
        });
        rl_name.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (tv_name.getText().toString().equals("未填写")) {
                    Intent intent = new Intent();
                    intent.setClass(MineXinxiActivity.this,
                            MineXinxiChangeActivity.class);
                    intent.putExtra("change", "0");
                    intent.putExtra("changename", "用户名");
                    intent.putExtra("key", "username");
                    startActivity(intent);
                }
            }
        });
        rl_shenfenzheng.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (tv_shenfenzheng.getText().toString().equals("未填写")) {
                    Intent intent = new Intent();
                    intent.setClass(MineXinxiActivity.this,
                            IdcordChangeActivity.class);
                    intent.putExtra("change", "0");
                    intent.putExtra("key", "idcardnumber");
                    intent.putExtra("changename", "身份证");
                    startActivity(intent);
                }
            }
        });
        rl_realname.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (tv_realname.getText().toString().equals("未填写")) {
                    Intent intent = new Intent();
                    intent.setClass(MineXinxiActivity.this,
                            MineXinxiChangeActivity.class);
                    intent.putExtra("change", "0");
                    intent.putExtra("key", "realname");
                    intent.putExtra("changename", "真实姓名");
                    startActivity(intent);
                }
            }
        });
        rl_email.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MineXinxiActivity.this,
                        MineXinxiChangeActivity.class);
                intent.putExtra("name", tv_email.getText().toString());
                intent.putExtra("change", "1");
                intent.putExtra("key", "email");
                intent.putExtra("changename", "邮箱");
                startActivity(intent);

            }
        });
        rl_sex.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final AlertDialog dlg = new AlertDialog.Builder(
                        MineXinxiActivity.this).create();
                dlg.show();
                dlg.setCancelable(true);
                Window window = dlg.getWindow();
                // *** 主要就是在这里实现这种效果的.
                // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
                window.setContentView(R.layout.sex);
                // 为确认按钮添加事件,执行退出应用操作
                final Button btn_nan = (Button) window
                        .findViewById(R.id.btn_nan);
                final Button btn_nv = (Button) window.findViewById(R.id.btn_nv);
                if (tv_sex.getText().toString().equals("男")) {
                    btn_nan.setTextColor(Color.parseColor("#e62f17"));
                } else {
                    btn_nv.setTextColor(Color.parseColor("#e62f17"));
                }
                // 关闭alert对话框架
                Button btn_back = (Button) window.findViewById(R.id.btn_back);

                btn_back.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dlg.cancel();
                    }
                });
                btn_nan.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        btn_nan.setTextColor(Color.parseColor("#e62f17"));
                        dlg.dismiss();
                        new Thread() {
                            public void run() {
                                NameValuePair pair1 = new BasicNameValuePair(
                                        "uid", LoginActivity.UID);
                                NameValuePair pair2 = new BasicNameValuePair(
                                        "filed", "sex");
                                NameValuePair pair3 = new BasicNameValuePair(
                                        "value", "1");
                                final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                                pairList.add(pair1);
                                pairList.add(pair2);
                                pairList.add(pair3);
                                PostUtil postUtil = new PostUtil();
                                String url1 = "http://wap.tianshijie.com.cn/appuser/update";
                                result = postUtil.DoPostNew(pairList, url1);
                                /**
                                 * BugStart
                                 * Bug编号：BUG4
                                 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
                                 * 修复人：李超
                                 * 修复日期：2015-10-23
                                 */
                                if(result == null){
                                    CToast.makeText(MineXinxiActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
                                    return;
                                }
                                //BugEnd
                                Log.v("url", "1" + result);

                                try {
                                    JSONObject jsonObject = new JSONObject(
                                            result);
                                    String info = jsonObject.getString("info");

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        tv_sex.setText("男");
                    }
                });
                dlg.setCanceledOnTouchOutside(true);
                btn_nv.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        btn_nv.setTextColor(Color.parseColor("#e62f17"));
                        dlg.dismiss();
                        new Thread() {
                            public void run() {
                                NameValuePair pair1 = new BasicNameValuePair(
                                        "uid", LoginActivity.UID);
                                NameValuePair pair2 = new BasicNameValuePair(
                                        "filed", "sex");
                                NameValuePair pair3 = new BasicNameValuePair(
                                        "value", "2");
                                final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                                pairList.add(pair1);
                                pairList.add(pair2);
                                pairList.add(pair3);
                                PostUtil postUtil = new PostUtil();
                                String url1 = "http://wap.tianshijie.com.cn/appuser/update";
                                result = postUtil.DoPostNew(pairList, url1);
                                /**
                                 * BugStart
                                 * Bug编号：BUG4
                                 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
                                 * 修复人：李超
                                 * 修复日期：2015-10-23
                                 */
                                if(result == null){
                                    CToast.makeText(MineXinxiActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
                                    return;
                                }
                                //BugEnd
                                Log.v("url", "1" + result);

                                try {
                                    JSONObject jsonObject = new JSONObject(
                                            result);
                                    String info = jsonObject.getString("info");

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        tv_sex.setText("女");
                    }
                });

            }

        });
        /**
         * 上传头像的代码
         */
        rl_touxiang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final AlertDialog dlg = new AlertDialog.Builder(
                        MineXinxiActivity.this).create();
                dlg.show();
                Window window = dlg.getWindow();
                // *** 主要就是在这里实现这种效果的.
                // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
                window.setContentView(R.layout.dal);
                // 为确认按钮添加事件,执行退出应用操作
                Button btn_formphotoalbum = (Button) window
                        .findViewById(R.id.btn_formphotoalbum);
                Button btn_takephoto = (Button) window
                        .findViewById(R.id.btn_takephoto);
                imageView = (ImageView) window.findViewById(R.id.imageView);
                // 关闭alert对话框架
                Button btn_back = (Button) window.findViewById(R.id.btn_back);
                btn_back.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dlg.cancel();
                    }
                });
                btn_formphotoalbum.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dlg.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setType("image/*");
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);

                    }
                });
                btn_takephoto.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // 执行拍照前，应该先判断SD卡是否存在
                        dlg.dismiss();
                        String SDState = Environment.getExternalStorageState();
                        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"

                            /***
                             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
                             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
                             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
                             */
                            ContentValues values = new ContentValues();
                            photoUri = MineXinxiActivity.this
                                    .getContentResolver()
                                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            values);
                            intent.putExtra(
                                    android.provider.MediaStore.EXTRA_OUTPUT,
                                    photoUri);
                            /** ----------------- */
                            startActivityForResult(intent,
                                    SELECT_PIC_BY_TACK_PHOTO);
                        } else {
                            Toast.makeText(MineXinxiActivity.this, "内存卡不存在",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        });

    }

    public void mypost() {
        new Thread() {
            public void run() {
                NameValuePair pair1 = new BasicNameValuePair("username",
                        LoginActivity.USERNAME);
                NameValuePair pair2 = new BasicNameValuePair("password",
                        LoginActivity.PASSWORD);
                Log.v("username", LoginActivity.USERNAME);
                Log.v("password", LoginActivity.PASSWORD);
                final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                pairList.add(pair1);
                pairList.add(pair2);
                PostUtil postUtil = new PostUtil();
                String url1 = "http://wap.tianshijie.com.cn/appuser/login";
                result = postUtil.DoPostNew(pairList, url1);
                /**
                 * BugStart
                 * Bug编号：BUG4
                 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
                 * 修复人：李超
                 * 修复日期：2015-10-23
                 */
                if(result == null){
                    CToast.makeText(MineXinxiActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
                    return;
                }
                //BugEnd
                Log.v("url", "1" + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                    user = new User();
                    user.setUsername(jsonObject2.getString("username"));
                    user.setIdcardnumber(jsonObject2.getString("idcardnumber"));
                    user.setRealname(jsonObject2.getString("realname"));
                    user.setSex(jsonObject2.getString("sex"));
                    user.setEmail(jsonObject2.getString("email"));
                    user.setCity(jsonObject2.getString("cityid"));
                    user.setUser_img(jsonObject2.getString("avatar"));
                    user.setInvest_type(jsonObject2.getString("invest_type"));
                    user.setUser_img(jsonObject2.getString("avatar"));
                    user.setCity(jsonObject2.getString("cityid"));

                    Message msg = Message.obtain();
                    msg.what = 3;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK
                && requestCode == SELECT_PIC_BY_PICK_PHOTO
                || requestCode == SELECT_PIC_BY_TACK_PHOTO) {
            doPhoto(requestCode, data);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 13) {
            Bundle extras = data.getExtras();
            Bitmap photo = extras.getParcelable("data");
            picPath = FileUtil.saveFile(mContext, "temp_head.jpg", photo);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            iv_touxiang.setImageBitmap(bm);
            Log.v(TAG, "最终选择的图片=" + picPath);

            imageView.setImageBitmap(bm);
            if (picPath != null) {
                Log.i(TAG, "最终选择的图片 picPath   = " + picPath);
                handler.sendEmptyMessage(TO_UPLOAD_FILE);
            } else {
                Log.i(TAG, "上传的文件路径出错");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) // 从相册取图片，有些手机有异常情况，请注意
        {
            if (data == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            // cursor.close();
        }
        Log.i(TAG, "imagePath = " + picPath);
        if (picPath != null
                && (picPath.endsWith(".png") || picPath.endsWith(".PNG")
                || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {

            // handler.sendEmptyMessage(TO_UPLOAD_FILE);
            KEY_PHOTO_PATH = picPath;
            Log.i(TAG, "2  KEY_PHOTO_PATH = " + KEY_PHOTO_PATH);
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri;
            if (data == null) {
                uri = photoUri;
            } else {
                uri = data.getData();
            }
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 150);
            intent.putExtra("outputY", 150);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 13);

        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 上传服务器响应回调
     */
    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    private void toUploadFile() {

        String fileKey = "avatar";
        UploadUtil uploadUtil = UploadUtil.getInstance();

        uploadUtil.setOnUploadProcessListener(MineXinxiActivity.this); // 设置监听器监听上传状态

        Map<String, String> params = new HashMap<String, String>();

        params.put("uid", LoginActivity.UID);

        uploadUtil.uploadFile(picPath, fileKey, requestURL, params);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    toUploadFile();
                    break;

                case UPLOAD_FILE_DONE:
                    String result = "响应码：" + msg.arg1 + "\n响应信息：" + msg.obj
                            + "\n耗时：" + UploadUtil.getRequestTime() + "秒";
                    Log.v("123456", result);
                    if (imageLoader != null) {
                        imageLoader.clearMemoryCache();
                        imageLoader.clearDiscCache();
                    }
                    mypost();
                    break;

                case 3:
                    if (!user.getUsername().equals("null")) {
                        tv_name.setText(user.getUsername());
                    } else {
                        tv_name.setText("未填写");
                    }
                    if (!user.getIdcardnumber().equals("")) {
                        String str = user.getIdcardnumber().substring(0, 4);
                        String substr = user.getIdcardnumber().substring(
                                user.getIdcardnumber().length() - 4,
                                user.getIdcardnumber().length());

                        tv_shenfenzheng.setText(str + "**********" + substr);
                        iv_shenfenzheng.setVisibility(View.GONE);
                    } else {
                        tv_shenfenzheng.setText("未填写");
                    }
                    if (!user.getEmail().equals("")) {
                        tv_email.setText(user.getEmail());
                    } else {
                        tv_email.setText("未填写");
                    }
                    if (!user.getRealname().equals("")) {
                        tv_realname.setText(user.getRealname());
                        iv_realname.setVisibility(View.GONE);
                    } else {
                        tv_realname.setText("未填写");
                    }

                    int sex = Integer.parseInt(user.getSex());
                    if (sex == 0) {
                        tv_sex.setText("男");
                    } else if (sex == 1) {
                        tv_sex.setText("男");
                    } else if (sex == 2) {
                        tv_sex.setText("女");
                    } else {
                        tv_sex.setText("男");
                    }
                    int invest_type = Integer.parseInt(user.getInvest_type());
                    if (invest_type == 0) {
                        tv_shenfen.setText("创业者");
                    } else if (invest_type == 1) {
                        tv_shenfen.setText("投资者");
                    } else if (invest_type == 2) {
                        tv_shenfen.setText("投资者");
                    }

                    imageLoader = ImageLoader.getInstance();
                    imageLoader.displayImage(
                            MainActivity.PJURl + user.getUser_img(), iv_touxiang,
                            options, animateFirstListener);
                    dbHelper = new DBManager(MineXinxiActivity.this);
                    dbHelper.openDatabase();
                    dbHelper.closeDatabase();
                    database = SQLiteDatabase.openOrCreateDatabase(
                            DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
                    Cursor cur = database.query("Area", null,
                            "area_id=" + user.getCity(), null, null, null, null);
                    while (cur.moveToNext()) {
                        String a = cur.getString(cur.getColumnIndex("area_name"));
                        tv_city.setText(a);
                        Log.v("a", a);
                    }
                    editor = getSharedPreferences("Xinxi", Activity.MODE_PRIVATE)
                            .edit();
                    editor.putString("tv_shenfen", tv_shenfen.getText().toString());
                    editor.putString("tv_name", tv_name.getText().toString());
                    editor.putString("tv_shenfenzheng", tv_shenfenzheng.getText()
                            .toString());
                    editor.putString("tv_realname", tv_realname.getText()
                            .toString());
                    editor.putString("tv_sex", tv_sex.getText().toString());
                    editor.putString("tv_email", tv_email.getText().toString());
                    editor.putString("tv_city", tv_city.getText().toString());

                    editor.commit();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
        handler.sendMessage(msg);
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
        handler.sendMessage(msg);
    }

    public ImageLoader initImageLoader(Context context,
                                       ImageLoader imageLoader, String dirName) {
        imageLoader = ImageLoader.getInstance();
        if (imageLoader.isInited()) {
            imageLoader.destroy();
        }
        imageLoader.init(initImageLoaderConfig(context, dirName));
        return imageLoader;
    }

    /**
     * 閰嶇疆鍥剧墖涓嬭浇鍣?
     *
     * @param dirName 鏂囦欢鍚?
     */
    private ImageLoaderConfiguration initImageLoaderConfig(Context context,
                                                           String dirName) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCache(new UnlimitedDiscCache(new File(dirName)))
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        return config;
    }

    private int getMemoryCacheSize(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
            // limit
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
        return memoryCacheSize;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mine_xinxi, menu);
        return true;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void onStart() {

        super.onStart();

        MyApplication mApp = (MyApplication) getApplication();

        if (mApp.isExit()) {

            finish();

        }

    }
//////BugStart//////
//    Bug编号：BUG7
//    Bug描述：原代码采用keydown方式，未考虑到一直按住不松开的情况，这里改为keyup
//    修复人：李超
//    修复日期:2015-10-23
    public boolean onKeyUp(int keyCode, KeyEvent event) {
//////BugEnd//////
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something...
            Intent intent = new Intent();
            intent.setClass(MineXinxiActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
