package com.example.tianshijie1.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class PostUtil {
	String result;

	public PostUtil() {
		// TODO Auto-generated constructor stub
	}

	public String DoPostNew(List<NameValuePair> pairList, String url) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		try {
			UrlEncodedFormEntity entityIn = new UrlEncodedFormEntity(pairList,
					"UTF-8");
			httpPost.setEntity(entityIn);
			HttpResponse response = httpClient.execute(httpPost);

			try {
				HttpEntity httpEntity = response.getEntity();
				InputStream inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				result = "";
				String line = "";
				while (null != (line = reader.readLine())) {
					result += line;

				}

				Log.i("main", "Response Content from server: " + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
