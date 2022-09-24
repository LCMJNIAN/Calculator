package com.example.calculator.Core;

import java.util.*;
public class BaseCalculate {

    public String evaluateExpression(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        String[] tokens = expression.split(" ");
        String temp = " ";
        for(String item: tokens){
            if(item.isEmpty())
                continue;
            if(item.length() <= 1) {
                if(item.charAt(0) >= 'A' && item.charAt(0) <= 'F') {
                    temp += " " + decimal(item) + " ";
                }
                else
                    temp += " " + item + " ";
            }
            else{
                if("<<".equals(item) || ">>".equals(item))
                    temp += " " + item + " ";
                else {
                    item = decimal(item);
                    temp += " " + item + " ";
                }
            }
            System.out.println(temp);
        }
        String[] ans = temp.split(" ");
        for (String token : ans) {

            if (token.length() == 0)   //如果是空格的话就继续循环，什么也不操作
                continue;
                //如果是加减的话，因为加减的优先级最低，因此这里的只要遇到加减号，无论操作符栈中的是什么运算符都要运算
            else if (token.charAt(0) == '+' || token.charAt(0) == '-') {
                //当栈不是空的，并且栈中最上面的一个元素是加减乘除的人任意一个
                if(token.length()>1)
                    if(token.charAt(1)>='0'&&token.charAt(1)<='9') {
                        operandStack.push(Double.parseDouble(token));
                        continue;
                    }
                while (!operatorStack.isEmpty() && (operatorStack.peek() == '-' || operatorStack.peek() == '+' || operatorStack.peek() == '÷' || operatorStack.peek() == '×')) {
                    processAnOperator(operandStack, operatorStack);   //开始运算
                }
                operatorStack.push(token.charAt(0));   //运算完之后将当前的运算符入栈
            }
            else if("<<".equals(token))
            {
                operatorStack.push('<');
            }
            else if(">>".equals(token))
            {
                operatorStack.push('>');
            }
            else if (token.charAt(0) == '&')
            {
                while(!operatorStack.isEmpty()&&operandStack.peek() == '&')
                {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            }
            else if (token.charAt(0) == '^')
            {
                while(!operatorStack.isEmpty()&&operandStack.peek() == '^')
                {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            }
            else if (token.charAt(0) == '|')
            {
                while(!operatorStack.isEmpty()&&operandStack.peek() == '|')
                {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            }
            //当前运算符是乘除的时候，因为优先级高于加减，因此要判断最上面的是否是乘除，如果是乘除就运算，否则的话直接入栈


            else if (token.charAt(0) == '×' || token.charAt(0) == '÷') {
                while (!operatorStack.isEmpty() && (operatorStack.peek() == '÷' || operatorStack.peek() == '×')) {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));   //将当前操作符入栈
            }
            //如果是左括号的话直接入栈，什么也不用操作,trim()函数是用来去除空格的，由于上面的分割操作可能会令操作符带有空格
            else if (token.trim().charAt(0) == '(') {
                operatorStack.push('(');
            }
            //如果是右括号的话，清除栈中的运算符直至左括号
            else if (token.trim().charAt(0) == ')') {
                while (operatorStack.peek() != '(') {
                    processAnOperator(operandStack, operatorStack);    //开始运算
                }
                operatorStack.pop();   //这里的是运算完之后清除左括号
            }
            //这里如果是数字的话直接如数据的栈
            else {
                operandStack.push(Double.parseDouble(token));   //将数字字符串转换成数字然后压入栈中
            }
        }
        //最后当栈中不是空的时候继续运算，知道栈中为空即可
        while (!operatorStack.isEmpty()) {
            processAnOperator(operandStack, operatorStack);
        }
        String answer = operandStack.pop().toString();
        int index = answer.indexOf('.');
        for(int i=index+1;i<answer.length();i++){
            if(answer.charAt(i) != '0')
                return hexadecimal(answer);
        }
        return hexadecimal(answer.substring(0,index));

    }

    //这个函数的作用就是处理栈中的两个数据，然后将栈中的两个数据运算之后将结果存储在栈中
    public void processAnOperator(Stack<Double> operandStack, Stack<Character> operatorStack) {
        char op = operatorStack.pop();  //弹出一个操作符
        if(op == '+' || op == '×' || op == '÷' || op=='-') {
            Double op1 = operandStack.pop();  //从存储数据的栈中弹出连个两个数用来和操作符op运算
            Double op2 = operandStack.pop();
            if (op == '+')  //如果操作符为+就执行加运算
                operandStack.push(op1 + op2);
            else if (op == '-')
            {

                operandStack.push(op2 - op1);   //因为这个是栈的结构，自然是上面的数字是后面的，因此用op2-op1
            }
            else if (op == '×')
                operandStack.push(op1 * op2);
            else operandStack.push(op2 / op1);
        }
        else if(op == '&'||op=='^'||op=='|')
        {
            Double op1 = operandStack.pop();
            Double op2 = operandStack.pop();
            int op1_int = op1.intValue();
            int op2_int = op2.intValue();
            if(op == '&')
                operandStack.push((double) (op1_int & op2_int));
            else if(op == '^')
                operandStack.push((double) (op1_int ^ op2_int));
            else operandStack.push((double) (op1_int | op2_int));
        }
        else if(op == '<' || op == '>')
        {
            Double op1 = operandStack.pop();
            Double op2 = operandStack.pop();
            int op1_int = op1.intValue();
            int op2_int = op2.intValue();
            if(op == '<')
                operandStack.push((double)(op2_int<<op1_int));//左移位
            else
                operandStack.push((double)(op2_int>>op1_int));//右移位
        }

    }

    public String hexadecimal(String s) {
        int i = 0;
        for(i=0;i<s.length();i++)
            if(s.charAt(i)=='.') {
                break;
            }
        String target_string = s.substring(0,i);
        String hex = Long.toHexString(Long.parseLong(target_string));
        return hex.toUpperCase();


    }
    public String decimal(String a)
    {
        long outcome = 0;
        for(int i = 0; i < a.length(); i++){
            char hexChar = a.charAt(i);
            outcome = outcome * 16 + charToDecimal(hexChar);
        }
        return String.valueOf(outcome);
    }
    public int charToDecimal(char c)
    {
        if(c >= 'A' && c <= 'F')
            return 10 + c - 'A';
        else
            return c - '0';
    }
    public String bin_to_dec(String s)
    {
        int outcome = 0;
        for(int i = 0; i < s.length(); i++){
            char binChar = s.charAt(i);
            if(binChar == '0')
                outcome = outcome * 2;
            else
                outcome = outcome * 2 + 1;
        }
        return String.valueOf(outcome);
    }
    //求原码
    public String true_form(String s)
    {
        int num = Integer.parseInt(s);
        if(num>0)
        {
            String binary_num = Integer.toBinaryString(num);
            int len = 32-binary_num.length();
            char [] a = new char[len];
            for(int i=0;i<len;i++)
                a[i] = '0';
            String temp = String.valueOf(a);
            return temp + binary_num;
        }
        else
        {
            num = -num;
            String binary_num = Integer.toBinaryString(num);
            int len = 32-binary_num.length();
            char [] a = new char[len];
            for(int i=0;i<len;i++)
                a[i] = '0';
            a[0] = '1';
            String temp = String.valueOf(a);
            return temp + binary_num;
        }

    }
    //求反码
    public String invert_code(String s)
    {
        int num = Integer.parseInt(s);
        String trueForm = true_form(s);
        if(num >= 0)
        {
            return trueForm;
        }
        else
        {
            char [] invertCode = new char[32];
            invertCode[0] = '1';
            for(int i=1;i<32;i++)
            {
                if(trueForm.charAt(i) == '1')
                    invertCode[i] = '0';
                else
                    invertCode[i] = '1';
            }
            return String.valueOf(invertCode);
        }
    }
    //求补码
    public String complement(String s)
    {
        int num = Integer.parseInt(s);
        if(num >= 0 )
            return true_form(s);
        else
            return Integer.toBinaryString(num);
    }
}