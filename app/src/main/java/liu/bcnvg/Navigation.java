package liu.bcnvg;

import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

import liu.bcnvg.Navi.CustomAmapRouteActivity;

/**
 * Created by Administrator on 2019/8/27.
 */

public class Navigation extends Fragment implements INaviInfoCallback {

    AMapNaviView aMapNaviView = null;
    LatLng p2 = new LatLng(39.917337, 116.397056);//故宫博物院
    LatLng p3 = new LatLng(39.904556, 116.427231);//北京站

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_navi, container, false);
        AmapNaviParams params = new AmapNaviParams(new Poi("北京站", p3, ""), null, new Poi("故宫博物院", p2, ""), AmapNaviType.WALK);
        params.setUseInnerVoice(true);
        AmapNaviPage.getInstance().showRouteActivity(getContext(), params, Navigation.this,CustomAmapRouteActivity.class);
        return view;
    }

    private  void initView(View view){
        aMapNaviView = view.findViewById(R.id.navi_view);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }


    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public View getCustomMiddleView() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getCustomNaviBottomView() {
        //返回null则不显示自定义区域
        return getCustomView("底部自定义区域");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getCustomNaviView() {
        //返回null则不显示自定义区域
        return getCustomView("中部自定义区域");
    }

    TextView text1;
    TextView text2;
    @RequiresApi(api = Build.VERSION_CODES.M)
    private View getCustomView(String title) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        try {
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            text1 = new TextView(getContext());
            text1.setGravity(Gravity.CENTER);
            text1.setHeight(90);
            text1.setMinWidth(300);
            text1.setText(title);

            text2 = new TextView(getContext());
            text2.setGravity(Gravity.CENTER);
            text1.setHeight(90);
            text2.setMinWidth(300);
            text2.setText(title);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.addView(text1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(text2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = 100;
            linearLayout.setLayoutParams(params);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return linearLayout;
    }



}
