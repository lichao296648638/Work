package DBManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.tianshijie1.R;

public class DBManager {
	private final int BUFFER_SIZE = 400000;
	public static final String DB_NAME = "countries.db"; // 淇濆瓨鐨勬暟鎹簱鏂囦欢鍚?
	public static final String PACKAGE_NAME = "com.example.tianshijie1";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME; // 鍦ㄦ墜鏈洪噷瀛樻斁鏁版嵁搴撶殑浣嶇疆

	private SQLiteDatabase database;
	private Context context;

	public DBManager(Context context) {
		this.context = context;
	}

	public void openDatabase() {
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}

	private SQLiteDatabase openDatabase(String dbfile) {
		try {
			if (!(new File(dbfile).exists())) {// 鍒ゆ柇鏁版嵁搴撴枃浠舵槸鍚﹀瓨鍦紝鑻ヤ笉瀛樺湪鍒欐墽琛屽鍏ワ紝鍚﹀垯鐩存帴鎵撳紑鏁版嵁搴?
				InputStream is = this.context.getResources().openRawResource(
						R.raw.area); // 娆插鍏ョ殑鏁版嵁搴?
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
					null);
			return db;
		} catch (FileNotFoundException e) {
			Log.e("Database", "File not found");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Database", "IO exception");
			e.printStackTrace();
		}
		return null;
	}

	public void closeDatabase() {
		this.database.close();
	}
}