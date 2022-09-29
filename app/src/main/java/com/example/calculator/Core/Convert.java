package com.example.calculator.Core;

public class Convert {
    public String CalLength(String sour_name,String value,String tar_name){
        Double temp_ans = Double.parseDouble(value);
        if(sour_name.equals(tar_name))
            return value;
        if("皮米".equals(sour_name))
            temp_ans *= Math.pow(10,-12);
        else if("纳米".equals(sour_name))
            temp_ans *= Math.pow(10,-9);
        else if("微米".equals(sour_name))
            temp_ans *= Math.pow(10,-6);
        else if("毫米".equals(sour_name))
            temp_ans *= 0.001;
        else if("厘米".equals(sour_name))
            temp_ans *= 0.01;
        else if("分米".equals(sour_name))
            temp_ans *= 0.1;
        else if("米".equals(sour_name))
            temp_ans *= 1;
        else if("千米".equals(sour_name))
            temp_ans *= 1000;
        else if("英里".equals(sour_name))
            temp_ans *= 1609.269391696;


        if("皮米".equals(tar_name))
            temp_ans /= Math.pow(10,-12);
        else if("纳米".equals(tar_name))
            temp_ans /= Math.pow(10,-9);
        else if("微米".equals(tar_name))
            temp_ans /= Math.pow(10,-6);
        else if("毫米".equals(tar_name))
            temp_ans /= 0.001;
        else if("厘米".equals(tar_name))
            temp_ans /= 0.01;
        else if("分米".equals(tar_name))
            temp_ans /= 0.1;
        else if("米".equals(tar_name))
            temp_ans /= 1;
        else if("千米".equals(tar_name))
            temp_ans /= 1000;
        else if("英里".equals(tar_name))
            temp_ans /= 1609.269391696;

        return String.valueOf(temp_ans);
    }
    public String CalBase(String sour_name, String value,String tar_name){
        if(sour_name.equals(tar_name))
            return value;
        BaseCalculate BC = new BaseCalculate();
        String temp_ans = value;
        if(sour_name == "十进制")
            temp_ans = temp_ans;
        else if(sour_name.equals("八进制"))
            temp_ans = BC.O_to_dec(value);
        else if(sour_name.equals("二进制"))
            temp_ans = BC.bin_to_dec(value);
        else
            temp_ans = BC.decimal(value);

        if(tar_name.equals("十进制"))
            return temp_ans;
        else if(tar_name.equals("八进制"))
            return BC.dec_to_O(temp_ans);
        else if(tar_name.equals("二进制"))
            return BC.dec_bin(temp_ans);
        else
            return BC.hexadecimal(value);
    }
}
