package com.example.calculator.Main;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
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
public class CarLoanCalActivity extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_loan);
    }
}
