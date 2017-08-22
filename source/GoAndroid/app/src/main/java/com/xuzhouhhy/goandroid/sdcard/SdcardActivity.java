package com.xuzhouhhy.goandroid.sdcard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.xuzhouhhy.goandroid.R;
import com.xuzhouhhy.goandroid.util.UtilSDCardManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 操作SDcard
 * Created by xuzhouhhy on 2017/8/22.
 */

public class SdcardActivity extends Activity {

    private static final String TAG = "SdcardActivity_tag";

    private TextView mTvSdcard;

    private String mPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard);
        List<String> sdcards = UtilSDCardManager.GetAllExtenalSDCards();
        StringBuilder sb = new StringBuilder();
        for (String string : sdcards) {
            if (string.contains("storage") && string.contains("sdcard1")) {
                mPath = string;
            }
            Log.d(TAG, string);
            sb.append(string);
            sb.append("\r\n");
        }
        mTvSdcard = (TextView) findViewById(R.id.tvSdcard);
        mTvSdcard.setText(sb.toString());
        writeFile(sb.toString());
        deleteTestFile();
    }

    private void deleteTestFile() {
        File file = new File(mPath + File.separator + "test");
        if (file.exists()) {
            if(file.delete()){
                Log.e(TAG, "delete file success");
            }else {
                Log.e(TAG, "delete file fail");
            }
        }else {
            Log.e(TAG, "test file not exist");
        }

    }

    private void writeFile(String s) {
        try {
            File file = new File(mPath + File.separator + "test.txt");
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return;
                }
            }
            FileWriter fw = new FileWriter(file, true);
            fw.write(s);
            fw.close();
            Log.e(TAG, "write file success");
        } catch (IOException e) {
            Log.e(TAG, "write file fail io exception");
            e.printStackTrace();
        }
    }
}
