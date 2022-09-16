package com.example.calculator.Main;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.calculator.Core.Calculate;
import com.example.calculator.R;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener,OnMenuItemClickListener {
    //结果
    private TextView result_front;
    private TextView result_end;
    //按钮0-9
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    //运算符
    private  Button plus;   // 加号+
    private  Button sub;    // 减号-
    private  Button multi;  // 乘号×
    private  Button divide; // 除号÷
    private  Button point;  // 小数点.
    private  Button equal;  // 等于=
    private  Button clean;  // 清空
    private  Button delete; // 删除
    private  Button settings; //设置
    private  Button cal_choose; //计算器选择
    private  Button convert;  //换算器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void initView() {
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

        plus = findViewById(R.id.plus);
        sub = findViewById(R.id.sub);
        multi = findViewById(R.id.multi);
        divide = findViewById(R.id.divide);
        point = findViewById(R.id.point);
        equal = findViewById(R.id.equal);
        clean = findViewById(R.id.clean);
        delete = findViewById(R.id.delete);

        result_front = findViewById(R.id.result_front);
        result_end = findViewById(R.id.result_end);

        settings = (Button)findViewById(R.id.btn_settings);
        cal_choose = findViewById(R.id.btn_main_choose);
        convert = findViewById(R.id.btn_convert_choose);

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

        plus.setOnClickListener(this);
        sub.setOnClickListener(this);
        multi.setOnClickListener(this);
        divide.setOnClickListener(this);
        equal.setOnClickListener(this);
        point.setOnClickListener(this);
        clean.setOnClickListener(this);
        delete.setOnClickListener(this);

        settings.setOnClickListener(this);
        cal_choose.setOnClickListener(this);
        convert.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        //获取文本内容
        String input = result_front.getText().toString();
        switch (view.getId()){//选择按钮id
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
                if(input.length()>0 && input.charAt(input.length()-1) == '=') {
                    result_front.setText(((Button) view).getText());
                    result_end.setText("");
                }
                else
                    result_front.setText(input + ((Button)view).getText());
                break;
            case R.id.point:
                if (input.isEmpty() || input.substring(input.length() - 1).equals(" "))
                    return;//如果最后是空格，无响应
                else
                    result_front.setText(input + '.');
                break;
            //加减乘除，运算符前后都是空格
            case R.id.plus:
            case R.id.sub:
            case R.id.multi:
            case R.id.divide:
                result_front.setText(input + " " + ((Button)view).getText() + " ");
                break;
            case R.id.clean://清除输入框
                result_front.setText("");
                result_end.setText("");
                break;
            case R.id.delete://从后往前删除字符
                if(!input.isEmpty())
                    result_front.setText(input.substring(0, input.length() - 1));
                    result_end.setText("");
                break;
            case R.id.equal://计算运算结果
                Calculate cal = new Calculate();
                result_end.setText(cal.evaluateExpression(result_front.getText().toString()));
                result_front.setText(input + "=");
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
            case R.id.btn_main_choose:
                //创建弹出式菜单对象（最低版本11）
                PopupMenu popup = new PopupMenu(this, view);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.menu_cal_array, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(this);
                //显示(这一行代码不要忘记了)
                popup.show();
                break;
            case R.id.btn_convert_choose:
                //跳转到换算器页面
                Intent intent=new Intent(MainActivity.this,ConvertLengthActivity.class);
                startActivity(intent);
                break;
        }
    }

    //弹出式菜单的单击事件处理
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.account:
                Toast.makeText(this, "账号", Toast.LENGTH_SHORT).show();
                break;
            case R.id.base_cal:
                Toast.makeText(this, "进制计算器", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sci_cal:
                Intent intent=new Intent(MainActivity.this,SciCalActivity.class);
                startActivity(intent);
                break;
            case R.id.loan_cal:
                Toast.makeText(this, "贷款计算器", Toast.LENGTH_SHORT).show();
                break;
            case R.id.basic_cal:
                Toast.makeText(this, "计算器", Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;
        }
        return false;
    }

}
