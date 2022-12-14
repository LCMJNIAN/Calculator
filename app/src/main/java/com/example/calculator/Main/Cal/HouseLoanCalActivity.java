package com.example.calculator.Main.Cal;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.calculator.Core.Calculate;
import com.example.calculator.Main.Convert.ConvertLengthActivity;
import com.example.calculator.Main.MainActivity;
import com.example.calculator.R;
import com.example.calculator.utils.TextUtil;

import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import android.view.View.OnClickListener;
public class HouseLoanCalActivity extends Activity implements OnClickListener, OnMenuItemClickListener {
    //声明用到的所有控件
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText row1edit;
    private EditText row2edit;
    private Button total;
    private RadioGroup radioGroup;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private EditText row4edit;
    private EditText row5edit;
    private Button detail;
    private TextView totalcal;
    private TextView alldetail;
    private  Button settings; //设置
    private  Button cal_choose; //计算器选择
    private  Button convert;  //换算器

    //声明变量
    double intotal,backtotal,extra,pertime;//贷款总额，还款总额，利息，每月还款总额
    int month;//月份
    String buytotal;//购房总额
    String percent;//贷款百分比

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_loan);
        initView();
    }

    private void initView() {
        //初始化控件
        initSpinner();
        row1edit= (EditText) findViewById(R.id.row1edit);
        row2edit= (EditText) findViewById(R.id.row2edit);
        total= (Button) findViewById(R.id.totalcal);
        radioGroup= (RadioGroup) findViewById(R.id.radiogroup);
        checkBox1= (CheckBox) findViewById(R.id.check1);
        checkBox2= (CheckBox) findViewById(R.id.check2);
        totalcal= (TextView) findViewById(R.id.showtotal);
        detail= (Button) findViewById(R.id.detail);
        alldetail= (TextView) findViewById(R.id.alldetail);
        row4edit= (EditText) findViewById(R.id.row4label);
        row5edit= (EditText) findViewById(R.id.row5label);
        settings = findViewById(R.id.btn_settings);
        cal_choose = findViewById(R.id.btn_main_choose);
        convert = findViewById(R.id.btn_convert_choose);


        settings.setOnClickListener(this);
        convert.setOnClickListener(this);
        cal_choose.setOnClickListener(this);

        //给第一个计算按钮添加点击监听
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buytotal=row1edit.getText().toString();
                percent=row2edit.getText().toString();
                if(TextUtils.isEmpty(buytotal)||TextUtils.isEmpty(percent))//判断前两个输入框是否非空
                {
                    Toast.makeText(HouseLoanCalActivity.this,"购房总价和按揭部分信息填写完整",Toast.LENGTH_LONG).show();
                }else if(TextUtil.isNum(buytotal)==false||TextUtil.isNum(percent)==false){//判断输入的是否是数字
                    Toast.makeText(HouseLoanCalActivity.this,"包含不合法的输入信息",Toast.LENGTH_LONG).show();
                } else if(Double.parseDouble(percent)>100){//判断百分比部分输入是否大于100%
                    Toast.makeText(HouseLoanCalActivity.this,"按揭部分不能超过100%",Toast.LENGTH_LONG).show();
                } else if(TextUtil.isNum(buytotal)&&TextUtil.isNum(percent))
                {
                    intotal=(Double.parseDouble(buytotal)*Double.parseDouble(percent)*0.01);
                    totalcal.setText("您的贷款总额为"+String.format("%.2f",intotal)+"万元");
                }
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first,second为商贷和公积金贷所填数值
                String first=row4edit.getText().toString();
                String second=row5edit.getText().toString();
                //firstrate和secondrate为商贷和公积金的年利率
                double firstrate=Double.parseDouble(spinner2.getSelectedItem().toString().substring(20,24))*0.01;
                double secondrate=Double.parseDouble(spinner2.getSelectedItem().toString().substring(31,35))*0.01;
                //获取下拉列表的年份求得月份
                String year=spinner1.getSelectedItem().toString();
                month=Integer.parseInt(year.substring(0,year.length()-1))*12;
                //两种贷款的月利率
                double firstmonthrate=firstrate/12;
                double secondmonthrate=secondrate/12;
                if(totalcal.getText().toString().equals("其中贷款部分为：***万")){//判断是否计算过贷款总额
                    Toast.makeText(HouseLoanCalActivity.this,"请先计算贷款总额",Toast.LENGTH_LONG).show();
                }else if(row1edit.getText().toString().equals(buytotal)==false||row2edit.getText().toString().equals(percent)==false){//监听贷款总额和按揭部分数值是否发生变化
                    Toast.makeText(HouseLoanCalActivity.this,"检查到您的购房总价或按揭部分数据更改，请重新计算贷款总额",Toast.LENGTH_LONG).show();
                } else if(checkBox1.isChecked()==false&&checkBox2.isChecked()==false)//监听勾选的多选框
                {
                    Toast.makeText(HouseLoanCalActivity.this,"请勾选贷款种类",Toast.LENGTH_LONG).show();
                }else if(checkBox1.isChecked()&&checkBox2.isChecked()==false){
                    //等额本息贷款算法
                    if(radioGroup.getCheckedRadioButtonId()==R.id.btn1){
                        pertime=intotal*firstmonthrate*Math.pow((1+firstmonthrate),month)/(Math.pow(1+firstmonthrate,month)-1);
                        backtotal=pertime*month;
                        extra=backtotal-intotal;
                        alldetail.setText("您的贷款总额为"+String.format("%.2f",intotal)+"万元\n还款总额为"+String.format("%.2f",backtotal)+"万元\n其中利息总额为"+String.format("%.2f",extra)+"万元\n还款总时间为"+month+"月\n每月还款金额为"+String.format("%.2f",pertime*10000)+"元");
                    }else{//等额本金贷款算法
                        double[] array=new double[month];
                        double sum=0;
                        for(int i=0;i<month;i++)
                        {
                            array[i]=intotal/month+(intotal-sum)*firstmonthrate;
                            sum+=array[i];
                        }
                        String text="";
                        for(int i=0;i<month;i++){
                            text+="\n第"+(i+1)+"个月应还金额为："+String.format("%.2f",array[i]*10000);
                        }
                        backtotal=sum;
                        extra=backtotal-intotal;
                        alldetail.setText("您的贷款总额为"+String.format("%.2f",intotal)+"万元\n还款总额为"+String.format("%.2f",backtotal)+"万元\n其中利息总额为"+String.format("%.2f",extra)+"万元\n还款总时间为"+month+"月\n每月还款金额如下："+text);
                    }

                }else if(checkBox1.isChecked()==false&&checkBox2.isChecked()){
                    if(radioGroup.getCheckedRadioButtonId()==R.id.btn1){
                        pertime=intotal*secondmonthrate*Math.pow((1+secondmonthrate),month)/(Math.pow(1+secondmonthrate,month)-1);
                        backtotal=pertime*month;
                        extra = backtotal - intotal;
                        alldetail.setText("您的贷款总额为" + String.format("%.2f",intotal) + "万元\n还款总额为"+String.format("%.2f",backtotal)+"万元\n其中利息总额为"+String.format("%.2f",extra)+"万元\n还款总时间为"+month+"月\n每月还款金额为"+String.format("%.2f",pertime*10000)+"元");
                    }else{
                        double[] array=new double[month];
                        double sum=0;
                        for(int i=0;i<month;i++)
                        {
                            array[i]=intotal/month+(intotal-sum)*secondmonthrate;
                            sum+=array[i];
                        }
                        String text="";
                        for(int i=0;i<month;i++){
                            text+="\n第"+(i+1)+"个月应还金额为："+String.format("%.2f",array[i]*10000)+"元";
                        }
                        backtotal=sum;
                        extra=backtotal-intotal;
                        alldetail.setText("您的贷款总额为"+String.format("%.2f",intotal)+"万元\n还款总额为"+String.format("%.2f",backtotal)+"万元\n其中利息总额为"+String.format("%.2f",extra)+"万元\n还款总时间为"+month+"月\n每月还款金额如下："+text);
                    }
                }else if(checkBox1.isChecked()&&checkBox2.isChecked()){
                    if(radioGroup.getCheckedRadioButtonId()==R.id.btn1){
                        if(TextUtils.isEmpty(first)||TextUtils.isEmpty(second)){
                            Toast.makeText(HouseLoanCalActivity.this,"请将空信息填写完整",Toast.LENGTH_LONG).show();
                        }else if(TextUtil.isNum(first)==false||TextUtil.isNum(second)==false){
                            Toast.makeText(HouseLoanCalActivity.this,"包含不合法的输入信息",Toast.LENGTH_LONG).show();
                        }else if(Double.parseDouble(first)+Double.parseDouble(second)!=Double.parseDouble(String.format("%.2f",intotal))){
                            Toast.makeText(HouseLoanCalActivity.this,"填写的两项贷款总额不等于初始贷款额度，请重新填写",Toast.LENGTH_LONG).show();
                        }else{
                            double p1=Double.parseDouble(first);
                            double p2=Double.parseDouble(second);
                            double pertime1=p1*firstmonthrate*Math.pow((1+firstmonthrate),month)/(Math.pow(1+firstmonthrate,month)-1);
                            double pertime2=p2*secondmonthrate*Math.pow((1+secondmonthrate),month)/(Math.pow(1+secondmonthrate,month)-1);
                            pertime=pertime1+pertime2;
                            backtotal=pertime*month;
                            extra=backtotal-intotal;
                            alldetail.setText("您的贷款总额为" + String.format("%.2f",intotal) + "万元\n还款总额为"+String.format("%.2f",backtotal)+"万元\n其中利息总额为"+String.format("%.2f",extra)+"万元\n还款总时间为"+month+"月\n每月还款金额为"+String.format("%.2f",pertime*10000)+"元");
                        }
                    }else{
                        if(first.equals("请输入商业贷款总额（单位万）")||second.equals("请输入公积金贷款总额（单位万）")){
                            Toast.makeText(HouseLoanCalActivity.this,"请将空信息填写完整",Toast.LENGTH_LONG).show();
                        }else if(TextUtil.isNum(first)==false||TextUtil.isNum(second)==false){
                            Toast.makeText(HouseLoanCalActivity.this,"包含不合法的输入信息",Toast.LENGTH_LONG).show();
                        }else if(Double.parseDouble(first)+Double.parseDouble(second)!=Double.parseDouble(String.format("%.2f",intotal))){
                            Toast.makeText(HouseLoanCalActivity.this,"填写的两项贷款总额不等于初始贷款额度，请重新填写",Toast.LENGTH_LONG).show();
                        }else{
                            double p1=Double.parseDouble(first);
                            double p2=Double.parseDouble(second);
                            double[] array1=new double[month];
                            double[] array2=new double[month];
                            double sum1=0,sum2=0;
                            for(int i=0;i<month;i++)
                            {
                                array1[i]=p1/month+(p1-sum1)*firstmonthrate;
                                array2[i]=p2/month+(p2-sum2)*secondmonthrate;
                                Toast.makeText(HouseLoanCalActivity.this,array1[i]+" "+array2[i],Toast.LENGTH_LONG);
                                sum1+=array1[i];
                                sum2+=array2[i];
                            }
                            String text="";
                            for(int i=0;i<month;i++){
                                text+="\n第"+(i+1)+"个月应还金额为："+String.format("%.2f",(array1[i]+array2[i])*10000)+"元";
                            }
                            backtotal=sum1+sum2;
                            extra=backtotal-intotal;
                            alldetail.setText("您的贷款总额为"+String.format("%.2f",intotal)+"万元\n还款总额为"+String.format("%.2f",backtotal)+"万元\n其中利息总额为"+String.format("%.2f",extra)+"万元\n还款总时间为"+month+"月\n每月还款金额如下："+text);
                        }
                    }
                }
            }
        });
        row1edit.addTextChangedListener(new TextWatcher() {
            int oldlength=0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//强制用户不能输入非数字和小数点之外的字符
                int length = charSequence.length();
                if (length > oldlength) {
                    char newchar = charSequence.charAt(i);
                    if ((newchar < '0' && newchar > '9') && newchar != '.') {
                        if (i != length - 1)
                            row1edit.setText(charSequence.subSequence(0, i).toString() + charSequence.subSequence(i + 1, length).toString());
                        else
                            row1edit.setText(charSequence.subSequence(0, length - 1));
                    }
                }
                oldlength=length;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        row2edit.addTextChangedListener(new TextWatcher() {
            int oldlength=0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length=charSequence.length();
                if(length>oldlength) {
                    char newchar = charSequence.charAt(i);
                    if ((newchar < '0' && newchar > '9') && newchar != '.') {
                        if (i != length - 1)
                            row2edit.setText(charSequence.subSequence(0, i).toString() + charSequence.subSequence(i + 1, length).toString());
                        else
                            row2edit.setText(charSequence.subSequence(0, length - 1));
                    }
                }
                oldlength=length;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        row4edit.addTextChangedListener(new TextWatcher() {
            int oldlength=0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length=charSequence.length();
                if(length>oldlength) {
                    char newchar = charSequence.charAt(i);
                    if ((newchar < '0' && newchar > '9') && newchar != '.') {
                        if (i != length - 1)
                            row4edit.setText(charSequence.subSequence(0, i).toString() + charSequence.subSequence(i + 1, length).toString());
                        else
                            row4edit.setText(charSequence.subSequence(0, length - 1));
                    }
                }
                oldlength=length;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        row5edit.addTextChangedListener(new TextWatcher() {
            int oldlength=0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length=charSequence.length();
                if(length>oldlength) {
                    char newchar = charSequence.charAt(i);
                    if ((newchar < '0' && newchar > '9') && newchar != '.') {
                        if (i != length - 1)
                            row5edit.setText(charSequence.subSequence(0, i).toString() + charSequence.subSequence(i + 1, length).toString());
                        else
                            row5edit.setText(charSequence.subSequence(0, length - 1));
                    }
                }
                oldlength=length;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
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
                Intent intent=new Intent(HouseLoanCalActivity.this, ConvertLengthActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initSpinner() {
        //初始化控件
        spinner1= (Spinner) findViewById(R.id.sp1);
        spinner2= (Spinner) findViewById(R.id.sp2);
        //建立数据源
        String[] years=getResources().getStringArray(R.array.years);
        String[] baserates=getResources().getStringArray(R.array.baserate);
        //声明一个下拉列表的数组适配器并绑定数据源
        ArrayAdapter<String> yearAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,years);
        ArrayAdapter<String> baserateAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,baserates);
        //绑定Adapter到控件
        spinner1.setAdapter(yearAdapter);
        spinner2.setAdapter(baserateAdapter);
        //设置默认选择第一项
        spinner1.setSelection(0);
        spinner2.setSelection(0);
        //设置标题
        spinner1.setPrompt("请选择贷款年限");
        spinner2.setPrompt("请选择基准利率");
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
                Intent base_intent = new Intent(HouseLoanCalActivity.this,BaseCalActivity.class);
                startActivity(base_intent);
                break;
            case R.id.sci_cal:
                Intent intent=new Intent(HouseLoanCalActivity.this,SciCalActivity.class);
                startActivity(intent);
                break;
            case R.id.house_loan_cal:
                Intent house_loan_intent = new Intent(HouseLoanCalActivity.this,HouseLoanCalActivity.class);
                startActivity(house_loan_intent);
                break;
            case R.id.car_loan_cal:
                Intent car_loan_intent = new Intent(HouseLoanCalActivity.this, CarLoanCalActivity.class);
                startActivity(car_loan_intent);
                break;
            case R.id.basic_cal:
                Intent intent_two=new Intent(HouseLoanCalActivity.this, MainActivity.class);
                startActivity(intent_two);
                break;
            default:
                break;
        }
        return false;
    }
}
