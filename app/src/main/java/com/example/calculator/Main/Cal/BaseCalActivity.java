package com.example.calculator.Main.Cal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.calculator.Core.BaseCalculate;
import com.example.calculator.Main.Convert.ConvertLengthActivity;
import com.example.calculator.Main.MainActivity;
import com.example.calculator.R;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import android.view.View.OnClickListener;
public class BaseCalActivity extends Activity implements OnClickListener,OnMenuItemClickListener {
    //进制选择标志符
    private int flag = 16;
    //结果
    private TextView result_front;
    private TextView result_end;
    //按钮0-9
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    //按钮A-F
    private Button btn_A,btn_B,btn_C,btn_D,btn_E,btn_F;
    //运算符
    private  Button plus;   // 加号+
    private  Button sub;    // 减号-
    private  Button multi;  // 乘号×
    private  Button divide; // 除号÷
    private  Button equal;  // 等于=
    private  Button clean;  // 清空
    private  Button delete; // 删除
    private  Button settings; //设置
    private  Button cal_choose; //计算器选择
    private  Button convert;  //换算器
    private  Button and;    //与
    private  Button or;     //或
    private  Button xor;    //异或
    private  Button left_move;    //左移
    private  Button right_move;    //右移
    private  Button left;   //左括号
    private  Button right;  //右括号
    private RadioGroup rg;  //单选按钮组
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_cal);
        initView();
    }
    private void initView() {
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
        btn_A = findViewById(R.id.btn_A);
        btn_B = findViewById(R.id.btn_B);
        btn_C = findViewById(R.id.btn_C);
        btn_D = findViewById(R.id.btn_D);
        btn_E = findViewById(R.id.btn_E);
        btn_F = findViewById(R.id.btn_F);

        plus = findViewById(R.id.plus);
        sub = findViewById(R.id.sub);
        multi = findViewById(R.id.multi);
        divide = findViewById(R.id.divide);
        equal = findViewById(R.id.equal);
        clean = findViewById(R.id.all_clean);
        delete = findViewById(R.id.delete);
        left = findViewById(R.id.btn_left);
        right = findViewById(R.id.btn_right);
        and = findViewById(R.id.btn_and);
        or = findViewById(R.id.btn_or);
        xor = findViewById(R.id.btn_xor);
        left_move = findViewById(R.id.btn_left_move);
        right_move = findViewById(R.id.btn_right_move);

        result_front = findViewById(R.id.result_front);
        result_end = findViewById(R.id.result_end);

        settings = (Button)findViewById(R.id.btn_settings);
        cal_choose = findViewById(R.id.btn_main_choose);
        convert = findViewById(R.id.btn_convert_choose);

        rg = (RadioGroup) findViewById(R.id.RadioGroup1);
        // 为按钮组绑定事件
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton r = (RadioButton) findViewById(checkedId);
                String base_text = r.getText().toString();
                if("十六进制".equals(base_text)){
                    flag = 16;
                    enabledButton(flag);
                }
                else if("八进制".equals(base_text)){
                    flag = 8;
                    enabledButton(8);
                }
                else{
                    flag = 2;
                    enabledButton(2);
                }
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
        btn_A.setOnClickListener(this);
        btn_B.setOnClickListener(this);
        btn_C.setOnClickListener(this);
        btn_D.setOnClickListener(this);
        btn_E.setOnClickListener(this);
        btn_F.setOnClickListener(this);

        plus.setOnClickListener(this);
        sub.setOnClickListener(this);
        multi.setOnClickListener(this);
        divide.setOnClickListener(this);
        equal.setOnClickListener(this);
        clean.setOnClickListener(this);
        delete.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        and.setOnClickListener(this);
        or.setOnClickListener(this);
        xor.setOnClickListener(this);
        left_move.setOnClickListener(this);
        right_move.setOnClickListener(this);

        settings.setOnClickListener(this);
        cal_choose.setOnClickListener(this);
        convert.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
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
            case R.id.btn_A:
            case R.id.btn_B:
            case R.id.btn_C:
            case R.id.btn_D:
            case R.id.btn_E:
            case R.id.btn_F:
                if(input.length()>0 && input.charAt(input.length()-1) == '=') {
                    result_front.setText(((Button) view).getText());
                    result_end.setText("");
                }
                else
                    result_front.setText(input + ((Button)view).getText());
                System.out.println(result_front.getText().toString());
                break;
            //加减乘除，运算符前后都是空格

            case R.id.sub:
                if(input.isEmpty())
                    result_front.setText("-");
                else{
                    int last_index = input.length()-1;
                    if (Character.isDigit(input.charAt(last_index)))
                        result_front.setText(input + " " +((Button)view).getText() + " " );
                    else
                        result_front.setText(input + "-");
                }
                System.out.println(result_front.getText().toString());
                break;
            case R.id.plus:
            case R.id.multi:
            case R.id.divide:
            case R.id.btn_left_move:
            case R.id.btn_right_move:
            case R.id.btn_left:
            case R.id.btn_right:
                result_front.setText(input + " " + ((Button)view).getText() + " ");
                System.out.println(result_front.getText().toString());
                break;
            case R.id.btn_and:
                result_front.setText(input + " " + "&" + " ");
                break;
            case R.id.btn_or:
                result_front.setText(input + " " + "|" + " ");
                break;
            case R.id.btn_xor:
                result_front.setText(input + " "+ "^"+" ");
                break;
            case R.id.all_clean://清除输入框
                result_front.setText("");
                result_end.setText("");
                break;
            case R.id.delete://从后往前删除字符
                if(!input.isEmpty())
                    result_front.setText(input.substring(0, input.length() - 1));
                result_end.setText("");
                break;
            case R.id.equal://计算运算结果
                BaseCalculate cal = new BaseCalculate();
                result_end.setText(cal.evaluateExpression(result_front.getText().toString(),flag));
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
                Intent intent=new Intent(BaseCalActivity.this, ConvertLengthActivity.class);
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
            case R.id.base_cal:
                Intent base_intent = new Intent(BaseCalActivity.this,BaseCalActivity.class);
                startActivity(base_intent);
                break;
            case R.id.sci_cal:
                Intent intent=new Intent(BaseCalActivity.this,SciCalActivity.class);
                startActivity(intent);
                break;
            case R.id.house_loan_cal:
                Intent house_loan_intent = new Intent(BaseCalActivity.this,HouseLoanCalActivity.class);
                startActivity(house_loan_intent);
                break;
            case R.id.car_loan_cal:
                Intent car_loan_intent = new Intent(BaseCalActivity.this, CarLoanCalActivity.class);
                startActivity(car_loan_intent);
                break;
            case R.id.basic_cal:
                Intent intent_two=new Intent(BaseCalActivity.this, MainActivity.class);
                startActivity(intent_two);
                break;
            default:
                break;
        }
        return false;
    }
    private void enabledButton(int flag){
        if(flag == 16){
            btn2.setEnabled(true);
            btn3.setEnabled(true);
            btn4.setEnabled(true);
            btn5.setEnabled(true);
            btn6.setEnabled(true);
            btn7.setEnabled(true);
            btn8.setEnabled(true);
            btn9.setEnabled(true);
            btn_A.setEnabled(true);
            btn_B.setEnabled(true);
            btn_C.setEnabled(true);
            btn_D.setEnabled(true);
            btn_E.setEnabled(true);
            btn_F.setEnabled(true);
        }
        else if(flag == 8){
            btn2.setEnabled(true);
            btn3.setEnabled(true);
            btn4.setEnabled(true);
            btn5.setEnabled(true);
            btn6.setEnabled(true);
            btn7.setEnabled(true);
            btn8.setEnabled(false);
            btn9.setEnabled(false);
            btn_A.setEnabled(false);
            btn_B.setEnabled(false);
            btn_C.setEnabled(false);
            btn_D.setEnabled(false);
            btn_E.setEnabled(false);
            btn_F.setEnabled(false);
        }
        else{
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);
            btn5.setEnabled(false);
            btn6.setEnabled(false);
            btn7.setEnabled(false);
            btn8.setEnabled(false);
            btn9.setEnabled(false);
            btn_A.setEnabled(false);
            btn_B.setEnabled(false);
            btn_C.setEnabled(false);
            btn_D.setEnabled(false);
            btn_E.setEnabled(false);
            btn_F.setEnabled(false);
        }
    }
}
