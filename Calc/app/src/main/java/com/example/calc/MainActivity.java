package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView helloTextView;
    private EditText nameEditText;

    private Button addButton;
    private Button subtractButton;
    private TextView resultTextView;
    private EditText firstNumber;
    private EditText secondNumber;

    private enum Operation {
        Additional,
        Subtraction
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        helloTextView = findViewById(R.id.tVHello);
        nameEditText  = findViewById(R.id.eTName);

        addButton = findViewById(R.id.addBtn);
        subtractButton = findViewById(R.id.subtractBtn);
        resultTextView = findViewById(R.id.tVCalcResult);

        firstNumber = findViewById(R.id.eTfirst);
        secondNumber = findViewById(R.id.eTsecond);
    }

    public void onAddButtonClick(View view) {
        printResult(calculate(Operation.Additional));
    }

    public void onSubtractButtonClick(View view) {
        printResult(calculate(Operation.Subtraction));
    }

    private int calculate(Operation operation) {
        final int a = Integer.parseInt(firstNumber.getText().toString());
        final int b = Integer.parseInt(secondNumber.getText().toString());

        switch (operation) {
            case Additional: return a + b;
            case Subtraction: return a - b;
            default: return 0;
        }
    }

    private void printResult(int result) {
        resultTextView.setText(String.format("Результат: %s", result));
    }

    public void onClick(View view) {
        Editable name = nameEditText.getText();
        String message;

        message = name.length() == 0
            ? "Привет, мир!"
            : "Привет, " + name;

        helloTextView.setText(message);
    }
}