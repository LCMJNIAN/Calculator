package com.example.calculator.Core;

import java.util.*;
public class Calculate {
    private final double PI = 3.1415926535;
    private final double E = 2.81828;
    public String insetBlanks(String s) {
        String result = "";
        char temp='s';
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == '+' || s.charAt(i) == '-'
                    || s.charAt(i) == '×' || s.charAt(i) == '÷' || s.charAt(i) == '&' || s.charAt(i) == '^' || s.charAt(i) == '√')
            {
                if(s.charAt(i)=='-'&& (temp=='s'||temp=='(')) {
                    result += s.charAt(i);
                    continue;
                }
                result += " " + s.charAt(i) + " ";
            } else {
                result += s.charAt(i);
            }
            temp = s.charAt(i);
        }
        return result;
    }

    public String evaluateExpression(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        String[] ans = expression.split(" ");
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
            //当前运算符是乘除的时候，因为优先级高于加减，因此要判断最上面的是否是乘除，如果是乘除就运算，否则的话直接入栈
            else if (token.charAt(0) == '×' || token.charAt(0) == '÷') {
                while (!operatorStack.isEmpty() && (operatorStack.peek() == '÷' || operatorStack.peek() == '×')) {
                    processAnOperator(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));   //将当前操作符入栈
            }
            else if(token.charAt(0) == '^' || token.charAt(0) == '√')
                operatorStack.push(token.charAt(0));
            else if("lg".equals(token) || "ln".equals(token) || "sin".equals(token)|| "cos".equals(token) || "tan".equals(token))
            {
                if("lg".equals(token))
                    operatorStack.push('o');
                else if("ln".equals(token))
                    operatorStack.push('n');
                else if("sin".equals(token))
                    operatorStack.push('s');
                else if("cos".equals(token))
                    operatorStack.push('c');
                else
                    operatorStack.push('t');
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
            else if (token.charAt(0) == 'π')
                operandStack.push(PI);
            else if (token.charAt(0) == 'e')
                operandStack.push(E);
            //这里如果是数字的话直接如数据的栈
            else {
                operandStack.push(Double.parseDouble(token));   //将数字字符串转换成数字然后压入栈中
            }
        }
        //最后当栈中不是空的时候继续运算，知道栈中为空即可
        while (!operatorStack.isEmpty()) {
            processAnOperator(operandStack, operatorStack);
        }

        String answer = operandStack.pop().toString();//此时数据栈中的数据就是运算的结果
        int index = answer.indexOf('.');
        for(int i=index+1;i<answer.length();i++){
            if(answer.charAt(i) != '0')
                return answer;
        }
        return answer.substring(0,index);
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
        if(op == '^'){
            Double op1 = operandStack.pop();
            Double op2 = operandStack.pop();
            operandStack.push(Math.pow(op2,op1));
        }
        if(op == '√'){
            Double number = operandStack.pop();
            operandStack.push(Math.sqrt(number));
        }
        if(op == 'o' || op == 'n' || op == 's' || op == 'c' || op == 't')
        {
            Double number = operandStack.pop();
            if (op == 'o')
                operandStack.push(Math.log10(number));
            else if(op == 'n')
                operandStack.push(Math.log(number));
            else if(op == 's')
                operandStack.push(Math.sin(number));
            else if(op == 'c')
                operandStack.push(Math.cos(number));
            else
                operandStack.push(Math.tan(number));
        }

    }
}
