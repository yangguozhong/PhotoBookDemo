package com.yang.test.photobook;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.yang.test.photobook.weiget.PhotoBookView;
import com.yang.test.photobook.weiget.VHTextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class VHTextActivity extends AppCompatActivity {
    private PhotoBookView photobookView;
    private ImageView imageView;
    private VHTextView vhTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhtext);
        final BookBean bookbean = new Gson().fromJson(getJson(this, "json/book_photo.json"), BookBean.class);
        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photobookView.setBookBean(bookbean, 0);
                photobookView.updateView();
            }
        });


//        VHTextView verTextView = (VHTextView)findViewById(R.id.vertextview);
//        verTextView.setFontSize(40);
//        verTextView.setTextStartAlign(VHTextView.LEFT);
//        verTextView.setText(getResources().getString(R.string.poem));
//
//        VHTextView verTextView1 = (VHTextView)findViewById(R.id.vertextview1);
//        verTextView1.setText(getResources().getString(R.string.poem));
//        verTextView1.setIsOpenUnderLine(true);
//        verTextView1.setFontSize(40);
//        verTextView1.setUnderLineColor(Color.RED);
//        verTextView1.setTextStartAlign(VHTextView.RIGHT);
//        verTextView1.setUnderLineWidth(3);
//        verTextView1.setUnderLineSpacing(10);
        photobookView = (PhotoBookView) findViewById(R.id.photobookView);
        photobookView.setScale(dip2px(this, 1));
        photobookView.setBookBean(bookbean, 2);
        photobookView.updateView();
        imageView = (ImageView) findViewById(R.id.drawImage);
        imageView.setBackgroundResource(R.mipmap.ic_launcher);
        vhTextView = (VHTextView) findViewById(R.id.vh_textView);
        vhTextView.setOritation(true);
        vhTextView.setText(getResources().getString(R.string.poem));
        vhTextView.setIsOpenUnderLine(true);
        vhTextView.setFontSize(40);
        vhTextView.setUnderLineColor(Color.RED);
        vhTextView.setUnderLineWidth(3);
        vhTextView.setUnderLineSpacing(10);


    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 读取assets目录下的json文件夹里的json文件
     */
    public static String getJson(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
