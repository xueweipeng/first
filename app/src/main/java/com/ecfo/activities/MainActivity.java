package com.ecfo.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.ecfo.R;
import com.ecfo.modules.discovery.DiscoveryFragment;
import com.ecfo.modules.lesson.LessonFragment;
import com.ecfo.modules.me.MeFragment;
import com.ecfo.modules.recommend.RecommendFragment;
import com.ecfo.modules.warehouse.WarehouseFragment;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.tabView)
    public TabView tabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LogUtils.v(TAG, "onCreate");
        requestPermissions();
        initFragment();
    }

    private void initFragment() {
        LessonFragment lessonFragment = LessonFragment.newInstance("", "");
        MeFragment meFragment = MeFragment.newInstance("", "");
        RecommendFragment recommendFragment = RecommendFragment.newInstance("", "");
        DiscoveryFragment discoveryFragment = DiscoveryFragment.newInstance("", "");
        WarehouseFragment warehouseFragment = WarehouseFragment.newInstance("", "");
        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild(R.drawable.lesson_sel, R.drawable.lesson_unsel, "听课", lessonFragment);
        TabViewChild tabViewChild02 = new TabViewChild(R.drawable.recommend_sel, R.drawable.recommend_unsel, "推荐", recommendFragment);
        TabViewChild tabViewChild03 = new TabViewChild(R.drawable.discovery_sel, R.drawable.discovery_unsel, "发现", discoveryFragment);
        TabViewChild tabViewChild04 = new TabViewChild(R.drawable.warehouse_sel, R.drawable.warehouse_unsel, "智库", warehouseFragment);
        TabViewChild tabViewChild05 = new TabViewChild(R.drawable.me_sel, R.drawable.me_unsel, "我", meFragment);
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        tabViewChildList.add(tabViewChild05);
        tabView.setTabViewDefaultPosition(0);
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
    }

    private void requestPermissions() {
        AndPermission.with(this)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // TODO what to do.
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        CrashUtils.init();
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                // TODO what to do
            }
        }).start();
    }

}
