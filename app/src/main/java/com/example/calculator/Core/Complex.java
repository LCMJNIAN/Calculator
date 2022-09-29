package com.example.calculator.Core;

public class Complex {
    private double real;
    private double imaginary;

    public Complex(){
        this.real = 0;
        this.imaginary = 0;
    }
    public Complex(double real,double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }
    public double getReal(){
        return this.real;
    }
    public double getImaginary(){
        return this.imaginary;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }

    public void setReal(double real) {
        this.real = real;
    }
    public String add(Complex a,Complex b){
        return String.valueOf(new Complex(a.real+b.real, a.imaginary+b.imaginary));
    }
    public String sub(Complex a, Complex b){
        return String.valueOf(new Complex(a.real-b.real,a.imaginary-b.imaginary));
    }
    public String multi(Complex a,Complex b){
        return String.valueOf(new Complex((a.real*b.real-a.imaginary*b.imaginary),(a.real*b.imaginary+a.imaginary*b.real)));
    }
    public String divide(Complex a,Complex b)
    {
        if(b.real==0 && b.imaginary==0)//排除除数不能为零的情况
        {
            return "ERROR";
        }
        return String.valueOf(new Complex(((a.real*b.real+a.imaginary*b.imaginary)/(b.real*b.real+b.imaginary*b.imaginary)),((a.imaginary*b.real-a.real*b.imaginary)/(b.real*b.real+b.imaginary*b.imaginary))));
    }
}
