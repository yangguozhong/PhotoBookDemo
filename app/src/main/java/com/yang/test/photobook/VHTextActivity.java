package com.yang.test.photobook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.yang.test.photobook.weiget.PhotoBookView;
import com.yang.test.photobook.weiget.VHTextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VHTextActivity extends AppCompatActivity {
    private PhotoBookView photobookView;
    private ImageView imageView;
    private VHTextView vhTextView;
    private int index = 7;
    private BookTemplate.BandTemplate.TextEntity mtextEntity;
    private RelativeLayout rl_chiLayout;
    private double currentScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhtext);
        final BookTemplate bookbean = new Gson().fromJson(getJson(this, "json/book_photo.json"), BookTemplate.class);
        mtextEntity = bookbean.getBand().get(1).getText();

        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
                BookTemplate.BandTemplate template = bookbean.getBand().get(index);
                photobookView.setBookBean(bookbean, index);
                if (template != null) {
                    drawTextView(template.getText(), template.getOrientation(), "请输入文字");
                }
                photobookView.updateView();
            }
        });
        findViewById(R.id.cut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMyBitmap("photo_book_test", ScreenShotUtil.getViewBitmap(photobookView));
            }
        });
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        double scale = (double) screenWidth / 720;
        double ios2Android_scale = (double) 560 / bookbean.getWidth();
        currentScale = scale * ios2Android_scale;
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
        photobookView.setScale(currentScale);
        photobookView.setBookBean(bookbean, 7);
        imageView = (ImageView) findViewById(R.id.drawImage);
        imageView.setBackgroundResource(R.mipmap.test);
        BookTemplate.BandTemplate template = bookbean.getBand().get(index);
        if (template != null) {
            drawTextView(template.getText(), template.getOrientation(), getResources().getString(R.string.poem));
        }

        photobookView.updateView();
//        vhTextView.setUnderLineWidth(3);
//        vhTextView.setUnderLineSpacing(10);


    }


    public void drawTextView(BookTemplate.BandTemplate.TextEntity textEntity, int oritation, String text) {
        if (textEntity == null) return;
        vhTextView = new VHTextView(VHTextActivity.this);
        switch (oritation) {
            case 1:
                vhTextView.setOritation(true);
                break;
            case 2:
                vhTextView.setOritation(false);
                vhTextView.setTextStartAlign(VHTextView.LEFT);
                break;
            case 3:
                vhTextView.setOritation(false);
                vhTextView.setTextStartAlign(VHTextView.RIGHT);
                break;

        }

        vhTextView.setText(text);
        vhTextView.setIsOpenUnderLine(false);
        vhTextView.setFontSize((float) (mtextEntity.getFont() * currentScale));
        vhTextView.setUnderLineColor(Color.RED);
        vhTextView.setmLineSpacing((int) (mtextEntity.getLine_space() * currentScale));
        vhTextView.setTextColor(android.graphics.Color.parseColor(mtextEntity.getColor()));
        photobookView.addTextView(vhTextView);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
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

    public void saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
