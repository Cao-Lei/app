package com.bwie.caolei.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.bwie.caolei.myapplication.adapter.PhotoWallAdapter;
import com.bwie.caolei.myapplication.img.Images;

public class MainActivity extends AppCompatActivity {

    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;
    /**
     * GridView的适配器
     */
    private PhotoWallAdapter mAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mImageThumbSize = getResources().getDimensionPixelSize( R.dimen.activity_horizontal_margin);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        mPhotoWall = (GridView) findViewById(R.id.photo_wall);

        mAdapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls,
                mPhotoWall);
        mPhotoWall.setAdapter(mAdapter);
        mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        final int numColumns = (int) Math.floor(mPhotoWall
                                .getWidth()
                                / (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            int columnWidth = (mPhotoWall.getWidth() / numColumns)
                                    - mImageThumbSpacing;
                            mAdapter.setItemHeight(columnWidth);
                            mPhotoWall.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });
    }
    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.fluchCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        mAdapter.cancelAllTasks();
    }

}
