package liu.bcnvg;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import liu.bcnvg.CheckPermissions.CheckPermissionsActivity;
import liu.bcnvg.Map3D.Navi_3D_Map;

public class MainActivity extends CheckPermissionsActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onResume();     //权限检查
        ActionBar tabsActionBar = getActionBar();
        tabsActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tabArray = tabsActionBar.newTab();
        tabArray.setText(R.string.tab_one);
        tabArray.setTabListener(new clockTabListener(this, LinkActivity.class.getName()));
        tabsActionBar.addTab(tabArray);
        tabArray = tabsActionBar.newTab();
        tabArray.setText(R.string.tab_two);
        tabArray.setTabListener(new clockTabListener(this, Navigation.class.getName()));
        tabsActionBar.addTab(tabArray);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_settings:
                super.startAppSettings();
                break;
            case R.id.wifi_settings:
                this.wifi_setting();
                break;
            case R.id.look_map:
                this.look_map();
                break;
            case R.id.IP_setting:

                break;
        }
        return true;
    }

    private  void wifi_setting(){
        Intent intent = new Intent(
                Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    private void look_map(){
        Intent intent = new Intent(this, Navi_3D_Map.class);
        startActivity(intent);
    }

    private  class clockTabListener implements ActionBar.TabListener{

        private  final Activity activityname;
        private final  String fragmentname;
        private Fragment launchFragment=null;

        public clockTabListener(Activity activityname, String fragmentname) {
            this.activityname = activityname;
            this.fragmentname=fragmentname;
        }


        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            launchFragment = Fragment.instantiate(activityname, fragmentname);
            ft.replace(android.R.id.content, launchFragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.remove(launchFragment);
            launchFragment=null;
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }
    }
}
