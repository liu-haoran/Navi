package liu.bcnvg;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import liu.bcnvg.R;
import liu.bcnvg.WIFI.SendThread;

/**
 * Created by Administrator on 2019/8/12.
 */

public class LinkActivity extends Fragment{

    EditText IP, PORT;
    TextView showMessage=null;
    Button link;
    private String mIp = "192.168.4.1";
    private int mPort = 8888;
    private SendThread sendthread;
    String receive_Msg;
    String l;
    Toast toast;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_port, container, false);
       initview(view);
       set_listener();
        return  view;
    }





    private  void initview(View view){
        IP = view.findViewById(R.id.IP_Adrress);
        PORT = view.findViewById(R.id.IP_Port);
        showMessage = view.findViewById(R.id.text_show);
        link = view.findViewById(R.id.link);
    }

    private  void set_listener(){
        link.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if("".equals(IP.getText().toString().trim()) || "".equals(PORT.getText().toString().trim())){
                    showtip("请输入正确的格式！！！");
                }else {
                    mIp = IP.getText().toString();
                    mPort = Integer.parseInt(PORT.getText().toString());
                    sendthread = new SendThread(mIp, mPort, mHandler);
                    showtip("IP:" + mIp + "   PORT:" + mPort);
                    Thread1();
                }
            }
        });
    }

    private void showtip(String str){
        toast.setText(str);
        toast.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void Thread1() {
        try {
            new Thread(sendthread).start();//创建一个新线程
        }  catch (ExceptionInInitializerError error){
            Toast.makeText(getContext(), "连接失败", Toast.LENGTH_SHORT).show();
        }

    }

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 0x00) {

                Log.i("mr_收到的数据： ", msg.obj.toString());
                receive_Msg = msg.obj.toString();
                l = receive_Msg;
                //Receive.setText(l);
            }
        }
    };
}
