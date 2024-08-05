package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Calculator implements ActionListener {

    private JFrame frame;
    private JTextField input;
    private boolean pressedOperator;
    private boolean pressedEquals;
    private double num1;
    private double num2;
    private String operator = "";


    public Calculator() {
        setFrame();
        initTextInput();
        initButtons();
        frame.setVisible(true);
    }

    private void setFrame() {
        frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
    }

    private void initButtons() {
        String[] buttonList = {"AC", "+/-", "%", "/", "7", "8", "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", "0", ".", "^", "="};
        for (String buttonStr : buttonList) {
            JButton button = new JButton(buttonStr);
            button.setPreferredSize(new Dimension(95, 65));
            button.addActionListener(this);
            frame.add(button);
        }
    }

    private void initTextInput() {
        input = new JTextField();
        input.setEditable(false);
        input.setPreferredSize(new Dimension(450, 60));
        input.setFocusable(false);
        input.setVisible(true);
        input.setFont(new Font("Arial", Font.PLAIN, 20));
        input.setBackground(Color.WHITE);
        frame.add(input);
    }

    public static void main(String[] args) {
        new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String previous = input.getText();
        if(command.matches("[0-9]")){
            if(pressedOperator || pressedEquals){
                input.setText(command);
            } else {
                input.setText(previous + command);
            }
            pressedOperator = false;
            pressedEquals = false;
        } else if (command.equals(".")){
            if(!previous.contains(".")){
                input.setText(previous + command);
            }
        } else if(command.equals("+/-")){
            double inputText = Double.parseDouble(input.getText()) * -1;
            input.setText(String.valueOf(inputText));
        } else if(command.equals("AC")){
            num1 = 0;
            num2 = 0;
            input.setText("");
        } else if (command.equals("=")){
            num2 = Double.parseDouble(input.getText());
            if(!operator.isEmpty()){
                double result = operation(num1, num2, operator);
                input.setText(String.valueOf(result));
            }
            pressedEquals = true;
            pressedOperator = false;
        } else {
            if(!pressedOperator){
                num1 = Double.parseDouble(previous);
            }
            operator = command;
            pressedOperator = true;
            pressedEquals = false;
        }

    }

    private double operation(double a, double b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            case "^" -> Math.pow(a, b);
            case "%" -> a % b;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
