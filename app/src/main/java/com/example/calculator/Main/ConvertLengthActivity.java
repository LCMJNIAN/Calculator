package com.example.calculator.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.calculator.Core.Convert;
import com.example.calculator.R;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ConvertLengthActivity extends Activity implements OnClickListener,OnMenuItemClickListener{
    //标识
    private int flag = 1;
    private int choose_length = 1;
    //结果
    private TextView result_first;
    private TextView result_second;
    //按钮0-9
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    private Button length_choose_one;   //单位选择
    private Button length_choose_two;   //单位选择
    private  Button clean;  // 清空
    private  Button delete; // 删除
    private  Button settings; //设置
    private  Button cal_choose; //计算器选择
    private  Button convert_choose;  //换算器选择
    private  Button double_zero;    //00
    private  Button point;      //小数点
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_length);
        initView();
    }

    private void initView() {
        result_first = findViewById(R.id.edit_text_one);
        result_second = findViewById(R.id.edit_text_two);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        point = findViewById(R.id.point);
        double_zero = findViewById(R.id.double_zero);

        length_choose_one = findViewById(R.id.length_meter);
        length_choose_two = findViewById(R.id.length_kilo);

        settings = findViewById(R.id.btn_settings);
        cal_choose = findViewById(R.id.btn_main_choose);
        convert_choose = findViewById(R.id.btn_convert_choose);
        clean = findViewById(R.id.clean);
        delete = findViewById(R.id.delete);

        result_first.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                result_first.setTextColor(0xff007bff);
                result_second.setTextColor(0xff000000);
                flag = 1;
            }
        });
        result_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                result_first.setTextColor(0xff000000);
                result_second.setTextColor(0xff007bff);
                flag = -1;
            }
        });


        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        point.setOnClickListener(this);

        double_zero.setOnClickListener(this);

        length_choose_two.setOnClickListener(this);
        length_choose_one.setOnClickListener(this);

        clean.setOnClickListener(this);
        delete.setOnClickListener(this);

        settings.setOnClickListener(this);
        cal_choose.setOnClickListener(this);
        convert_choose.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String input = "";
        if (flag == 1)
            input = result_first.getText().toString();
        else
            input = result_second.getText().toString();
        switch (view.getId()) {
            //选择按钮id
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
            case R.id.double_zero:
                String content = input + ((Button) view).getText();
                if("0".equals(input))
                    content = ((Button) view).getText() + "";
                if (flag == 1) {
                    result_first.setText(content);
                    String sour_name = length_choose_one.getText().toString();
                    String tar_name = length_choose_two.getText().toString();
                    String value = result_first.getText().toString();
                    Convert con = new Convert();
                    result_second.setText(con.CalLength(sour_name,value,tar_name));
                } else {
                    result_second.setText(content);
                    String sour_name = length_choose_two.getText().toString();
                    String tar_name = length_choose_one.getText().toString();
                    String value = result_second.getText().toString();
                    Convert con = new Convert();
                    result_first.setText(con.CalLength(sour_name,value,tar_name));
                }
                break;
            case R.id.clean://清除输入框
                result_first.setText("0");
                result_second.setText("0");
                break;
            case R.id.point:
                if (input.isEmpty() || input.substring(input.length() - 1).equals(" "))
                    return;//如果最后是空格，无响应
                else {
                    if(flag == 1){
                        result_first.setText(input + '.');
                        String sour_name = length_choose_one.getText().toString();
                        String tar_name = length_choose_two.getText().toString();
                        String value = result_first.getText().toString();
                        Convert con = new Convert();
                        result_second.setText(con.CalLength(sour_name,value,tar_name));
                    }else{
                        result_second.setText(input + '.');
                        String sour_name = length_choose_two.getText().toString();
                        String tar_name = length_choose_one.getText().toString();
                        String value = result_second.getText().toString();
                        Convert con = new Convert();
                        result_first.setText(con.CalLength(sour_name,value,tar_name));
                    }
                }
                break;
            case R.id.length_meter:
                choose_length = 1;
                setDialog();
                break;
            case R.id.length_kilo:
                choose_length = 2;
                setDialog();
                break;

            case R.id.delete://从后往前删除字符
                if (!input.isEmpty()) {
                    if (input.substring(0, input.length() - 1).isEmpty()) {
                            result_first.setText("0");
                            result_second.setText("0");
                    }
                    else {
                        if(flag == 1) {
                            result_first.setText(input.substring(0, input.length() - 1));
                            String sour_name = length_choose_one.getText().toString();
                            String tar_name = length_choose_two.getText().toString();
                            String value = result_first.getText().toString();
                            Convert con = new Convert();
                            result_second.setText(con.CalLength(sour_name,value,tar_name));
                        }
                        else {
                            result_second.setText(input.substring(0, input.length() - 1));
                            String sour_name = length_choose_two.getText().toString();
                            String tar_name = length_choose_one.getText().toString();
                            String value = result_second.getText().toString();
                            Convert con = new Convert();
                            result_first.setText(con.CalLength(sour_name,value,tar_name));
                        }
                    }
                }
                break;
            case R.id.btn_settings:
                //创建弹出式菜单对象（最低版本11）
                PopupMenu popup_settings = new PopupMenu(this, view);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater_settings = popup_settings.getMenuInflater();
                //填充菜单
                inflater_settings.inflate(R.menu.menu_settings, popup_settings.getMenu());
                //绑定菜单项的点击事件
                popup_settings.setOnMenuItemClickListener(this);
                //显示(这一行代码不要忘记了)
                popup_settings.show();
                break;
            case R.id.btn_convert_choose:
                //创建弹出式菜单对象（最低版本11）
                PopupMenu popup = new PopupMenu(this, view);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.menu_convert_array, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(this);
                //显示(这一行代码不要忘记了)
                popup.show();
                break;
            case R.id.btn_main_choose:
                //跳转到计算器页面
                Intent intent = new Intent(ConvertLengthActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }
    //弹出式菜单的单击事件处理
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                this.finish();
                break;
            case R.id.set:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.account:
                Toast.makeText(this, "账号", Toast.LENGTH_SHORT).show();
                break;
            case R.id.convert_exchange_rate:
                Intent intent_ex_ra = new Intent(ConvertLengthActivity.this, ConvertExchangeRateActivity.class);
                startActivity(intent_ex_ra);
                break;
            case R.id.convert_base:
                Intent intent_base = new Intent(ConvertLengthActivity.this, ConvertBaseActivity.class);
                startActivity(intent_base);
                break;
            case R.id.convert_length:
                convert_choose.setText("长度换算");
                break;
            case R.id.convert_area:
                Intent intent_area = new Intent(ConvertLengthActivity.this, ConvertAreaActivity.class);
                startActivity(intent_area);
                break;
            case R.id.convert_speed:
                Intent intent_speed = new Intent(ConvertLengthActivity.this, ConvertSpeedActivity.class);
                startActivity(intent_speed);
                break;
            case R.id.convert_temperature:
                Intent intent_tem = new Intent(ConvertLengthActivity.this, ConvertTemperatureActivity.class);
                startActivity(intent_tem);
                break;
            case R.id.convert_weight:
                Intent intent_wei = new Intent(ConvertLengthActivity.this, ConvertWeightActivity.class);
                startActivity(intent_wei);
                break;
            case R.id.convert_power:
                Intent intent_power= new Intent(ConvertLengthActivity.this, ConvertPowerActivity.class);
                startActivity(intent_power);
                break;
            case R.id.convert_pressure:
                Intent intent_pressure = new Intent(ConvertLengthActivity.this, ConvertPressureActivity.class);
                startActivity(intent_pressure);
                break;
            case R.id.convert_volume:
                Intent intent_volume = new Intent(ConvertLengthActivity.this, ConvertVolumeActivity.class);
                startActivity(intent_volume);
                break;
        }
        return false;
    }
    private void setDialog() {
        Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_dialog, null);

        //初始化视图
        root.findViewById(R.id.btn_pm).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String button_text = ((Button) v).getText() + "";
                if(choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        root.findViewById(R.id.btn_nm).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String button_text = ((Button) v).getText() + "";
                if (choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        root.findViewById(R.id.btn_um).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String button_text = ((Button) v).getText() + "";
                if (choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        root.findViewById(R.id.btn_mm).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String button_text = ((Button) v).getText() + "";
                if (choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        root.findViewById(R.id.btn_cm).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String button_text = ((Button) v).getText() + "";
                if (choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        root.findViewById(R.id.btn_dm).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String button_text = ((Button) v).getText() + "";
                if (choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        root.findViewById(R.id.btn_m).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String button_text = ((Button) v).getText() + "";
                if (choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        root.findViewById(R.id.btn_km).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String button_text = ((Button) v).getText() + "";
                if (choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        root.findViewById(R.id.btn_mi).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String button_text = ((Button) v).getText() + "";
                if (choose_length == 1)
                    length_choose_one.setText(button_text);
                else
                    length_choose_two.setText(button_text);
                mCameraDialog.dismiss();
                result_first.setText("0");
                result_second.setText("0");
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

}
