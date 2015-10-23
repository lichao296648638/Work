package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.city.CharacterParser;
import com.example.tianshijie1.city.City;
import com.example.tianshijie1.city.PinyinComparator;
import com.example.tianshijie1.city.SideBar;
import com.example.tianshijie1.city.SideBar.OnTouchingLetterChangedListener;
import com.example.tianshijie1.city.SortAdapter;
import com.example.tianshijie1.city.SortModel;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.LvHeightUtil;
import com.example.tianshijie1.util.PostUtil;

public class CityChoiseActivity extends Activity {
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private EditText mClearEditText;
	private ScrollView slv_city;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	Handler handler;
	String result;
	List<String> listcity;
	List<City> lisCities;
	List<City> liCitiesid;
	List<City> listCityhot;
	private Button btn_btn1, btn_btn2, btn_btn3, btn_btn4, btn_btn5, btn_btn6,
			btn_btn7;
	ImageView iv_backcity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_city_choise);
		listcity = new ArrayList<String>();
		lisCities = new ArrayList<City>();
		liCitiesid = new ArrayList<City>();
		listCityhot = new ArrayList<City>();
		initViews();
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String[] data = listcity
							.toArray(new String[listcity.size()]);
					SourceDateList = filledData(data);
					// 根据a-z进行排序源数据
					Collections.sort(SourceDateList, pinyinComparator);
					adapter = new SortAdapter(CityChoiseActivity.this,
							SourceDateList);
					sortListView.setAdapter(adapter);
					LvHeightUtil.setListViewHeightBasedOnChildren(sortListView);
					btn_btn1.setText(listCityhot.get(0).getCityname());
					btn_btn1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(CityChoiseActivity.this,
									MainActivity.class);
							intent.putExtra("cityid", listCityhot.get(0)
									.getCityid());
							intent.putExtra("cityname", listCityhot.get(0)
									.getCityname());
							intent.putExtra("realname", MainActivity.REALNAME);
							intent.putExtra("invest_type",
									MainActivity.INVEST_TYPEi);
							intent.putExtra("avatar", MainActivity.AVATAR);
							startActivity(intent);
							finish();
						}
					});
					btn_btn2.setText(listCityhot.get(1).getCityname());
					btn_btn2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(CityChoiseActivity.this,
									MainActivity.class);
							intent.putExtra("city", listCityhot.get(1)
									.getCityid());
							intent.putExtra("cityname", listCityhot.get(1)
									.getCityname());
							intent.putExtra("realname", MainActivity.REALNAME);
							intent.putExtra("invest_type",
									MainActivity.INVEST_TYPEi);
							intent.putExtra("avatar", MainActivity.AVATAR);
							intent.putExtra("cityname", listCityhot.get(1)
									.getCityname());
							startActivity(intent);
							finish();
						}
					});
					btn_btn3.setText(listCityhot.get(2).getCityname());
					btn_btn3.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(CityChoiseActivity.this,
									MainActivity.class);
							intent.putExtra("city", listCityhot.get(2)
									.getCityid());
							intent.putExtra("cityname", listCityhot.get(2)
									.getCityname());

							intent.putExtra("realname", MainActivity.REALNAME);
							intent.putExtra("invest_type",
									MainActivity.INVEST_TYPEi);
							intent.putExtra("avatar", MainActivity.AVATAR);
							startActivity(intent);
							finish();
						}
					});
					btn_btn4.setText(listCityhot.get(3).getCityname());
					btn_btn4.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(CityChoiseActivity.this,
									MainActivity.class);
							intent.putExtra("city", listCityhot.get(3)
									.getCityid());
							intent.putExtra("cityname", listCityhot.get(3)
									.getCityname());

							intent.putExtra("realname", MainActivity.REALNAME);
							intent.putExtra("invest_type",
									MainActivity.INVEST_TYPEi);
							intent.putExtra("avatar", MainActivity.AVATAR);
							startActivity(intent);
							finish();
						}
					});
					btn_btn5.setText(listCityhot.get(4).getCityname());
					btn_btn5.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(CityChoiseActivity.this,
									MainActivity.class);
							intent.putExtra("city", listCityhot.get(4)
									.getCityid());
							intent.putExtra("cityname", listCityhot.get(4)
									.getCityname());
							intent.putExtra("realname", MainActivity.REALNAME);
							intent.putExtra("invest_type",
									MainActivity.INVEST_TYPEi);
							intent.putExtra("avatar", MainActivity.AVATAR);
							startActivity(intent);
							finish();
						}
					});
					btn_btn6.setText(listCityhot.get(5).getCityname());
					btn_btn6.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(CityChoiseActivity.this,
									MainActivity.class);
							intent.putExtra("city", listCityhot.get(5)
									.getCityid());
							intent.putExtra("cityname", listCityhot.get(5)
									.getCityname());
							intent.putExtra("realname", MainActivity.REALNAME);
							intent.putExtra("invest_type",
									MainActivity.INVEST_TYPEi);
							intent.putExtra("avatar", MainActivity.AVATAR);
							startActivity(intent);
							finish();
						}
					});
					btn_btn7.setText(listCityhot.get(6).getCityname());
					btn_btn7.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(CityChoiseActivity.this,
									MainActivity.class);
							intent.putExtra("city", listCityhot.get(6)
									.getCityid());
							intent.putExtra("cityname", listCityhot.get(6)
									.getCityname());
							intent.putExtra("realname", MainActivity.REALNAME);
							intent.putExtra("invest_type",
									MainActivity.INVEST_TYPEi);
							intent.putExtra("avatar", MainActivity.AVATAR);
							startActivity(intent);
							finish();
						}
					});
					// 设置右侧触摸监听
					sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

						@Override
						public void onTouchingLetterChanged(String s) {
							// 该字母首次出现的位置
							int position = adapter.getPositionForSection(s
									.charAt(0));
							if (position != -1) {
								slv_city.requestChildFocus(sortListView,
										sortListView.getChildAt(position));
							}

						}
					});
					break;

				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appindex/city";
				result = postUtil.DoPostNew(pairList, url1);
				/**
				 * BugStart
				 * Bug编号：BUG4
				 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
				 * 修复人：李超
				 * 修复日期：2015-10-23
				 */
				if(result == null){
					CToast.makeText(CityChoiseActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
					return;
				}
				//BugEnd
				Log.v("url", "1" + result);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					JSONObject jsonObject3 = jsonObject2
							.getJSONObject("substation");
					Iterator it = jsonObject3.keys();
					while (it.hasNext()) {
						City city = new City();
						String key = (String) it.next();
						String value = jsonObject3.getString(key);
						city.setCityid(key);
						city.setCityname(value);
						lisCities.add(city);
						listcity.add(value);
					}
					JSONObject jsonObject4 = jsonObject2.getJSONObject("city");
					Iterator itcity = jsonObject4.keys();
					while (itcity.hasNext()) {
						City city = new City();
						String key = (String) itcity.next();
						String value = jsonObject4.getString(key);
						city.setCityid(key);
						city.setCityname(value);
						liCitiesid.add(city);

					}
					JSONObject jsonObject5 = jsonObject2.getJSONObject("hot");
					Iterator ithot = jsonObject5.keys();
					while (ithot.hasNext()) {
						City city = new City();
						String key = (String) ithot.next();
						String value = jsonObject5.getString(key);
						city.setCityid(key);
						city.setCityname(value);
						listCityhot.add(city);
					}
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	private void initViews() {
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		btn_btn1 = (Button) findViewById(R.id.btn_btn1);
		btn_btn2 = (Button) findViewById(R.id.btn_btn2);
		btn_btn3 = (Button) findViewById(R.id.btn_btn3);
		btn_btn4 = (Button) findViewById(R.id.btn_btn4);
		btn_btn5 = (Button) findViewById(R.id.btn_btn5);
		btn_btn6 = (Button) findViewById(R.id.btn_btn6);
		btn_btn7 = (Button) findViewById(R.id.btn_btn7);
		iv_backcity = (ImageView) findViewById(R.id.iv_backcity);
		slv_city = (ScrollView) findViewById(R.id.slv_city);
		iv_backcity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		sideBar.setTextView(dialog);

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象

				for (int i = 0; i < lisCities.size(); i++) {
					String a = lisCities.get(i).getCityname();
					if (a.equals(((SortModel) adapter.getItem(position))
							.getName())) {

						Intent intent = new Intent();
						intent.setClass(CityChoiseActivity.this,
								MainActivity.class);
						intent.putExtra("city", liCitiesid.get(i).getCityname());
						intent.putExtra("cityname", a);
						intent.putExtra("realname", MainActivity.REALNAME);
						intent.putExtra("invest_type",
								MainActivity.INVEST_TYPEi);
						intent.putExtra("avatar", MainActivity.AVATAR);
						startActivity(intent);
						finish();
					}

				}

			}
		});

		mClearEditText = (EditText) findViewById(R.id.filter_edit);
		mClearEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slv_city.scrollTo(0,
						LvHeightUtil.dip2px(CityChoiseActivity.this, 205));

			}
		});

		mClearEditText.clearFocus();
		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {

			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	protected void onStart() {

		super.onStart();

		MyApplication mApp = (MyApplication) getApplication();

		if (mApp.isExit()) {

			finish();

		}

	}
}
