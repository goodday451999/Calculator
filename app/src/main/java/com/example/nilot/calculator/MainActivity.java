package com.example.nilot.calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    final int row = 6;
    final int col = 4;
    final String[] singleBoard = new String[row * col];
    String str = "";
    Stack myStack = new Stack();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        createBoard();
        gridview();
    }

    public void createBoard(){
        char[][] board = new char[row][col];
        board[0][0] = 'C';
        board[0][1] = 'S';
        board[0][2] = '^';
        board[0][3] = 'I';
        board[1][0] = '(';
        board[1][1] = ')';
        board[1][2] = '%';
        board[1][3] = '/';
        board[2][0] = '7';
        board[2][1] = '8';
        board[2][2] = '9';
        board[2][3] = '*';
        board[3][0] = '4';
        board[3][1] = '5';
        board[3][2] = '6';
        board[3][3] = '-';
        board[4][0] = '1';
        board[4][1] = '2';
        board[4][2] = '3';
        board[4][3] = '+';
        board[5][0] = '~';
        board[5][1] = '0';
        board[5][2] = '.';
        board[5][3] = '=';
        for(int i = 0; i < row;i++){
            for(int j = 0;j < col;j++){
                singleBoard[(i * col) + j] = "" +  board[i][j];
            }
        }
    }

    protected void gridview() {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        String[] arr = singleBoard;
        final List<String> list = new ArrayList<String>(Arrays.asList(arr));
        final CalculatorArrayAdapter <String> gridAdapter = new CalculatorArrayAdapter<String>(this, R.layout.calculator_grid_layout, list,this);

        //data bind GridView with ArrayAdapter
        gridview.setAdapter(gridAdapter);
        setClickListener(list,gridAdapter);
    }

    public static double evaluate(String expression){
        char[] tokens = expression.toCharArray();
        Stack<Double> values = new Stack<Double>();
        Stack<Character> ops = new Stack<Character>();
        for (int i = 0; i < tokens.length; i++){
            /*if (tokens[i] == ' '){
                continue;
            }*/
            if (tokens[i] >= '0' && tokens[i] <= '9'){
                StringBuffer sbuf = new StringBuffer();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Double.parseDouble(sbuf.toString()));
                i--;
            }
            else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            }
            else if (tokens[i] == ')'){
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            }
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '%' || tokens[i] == 'S' || tokens[i] == 'I' || tokens[i] == '~' || tokens[i] == '^' || tokens[i] == '.'){
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(tokens[i]);
            }
        }
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    public static boolean hasPrecedence(char op1, char op2){
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-') && (op2 == '^') && (op2 == '%') && (op2 == '~') && (op2== 'S')) {
            return false;
        }
        else {
            return true;
        }
    }

    public static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return (a + b);
            case '-':
                return (a - b);
            case '*':
                return (a * b);
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("MATH ERROR : Cannot divide by zero");
                return (a / b);
            case '%':
                return (a % b);
            case '^':
                return Math.pow(a, b);
            case 'I':
                return (1 / Math.pow(a, b));
            case 'S':
                return Math.sqrt(a);
            case '~':
                return ((-1) * a);
            case '.':
                return ((0.1) * a);
        }
            //default:
                return 0;

    }

    protected void setClickListener(final List<String> list,final CalculatorArrayAdapter<String> gridAdapter) {
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                for (int i = 0; i < gridview.getChildCount(); i++)
                {
                    v = gridview.getChildAt(i);
                    v.setBackgroundColor(Color.TRANSPARENT);

                }

                                                if (singleBoard[position].equals("C")) try {
                                                    str = str.substring(0 , str.length()-1);
                                                    TextView textView = (TextView) findViewById(R.id.ans);
                                                    textView.setText(str);
                                                } catch (Exception e){
                                                    //wait for inputs
                                                }
                                                else if (singleBoard[position].equals("=")) {

                                                    //call a func to evaluate
                                                    double result= MainActivity.evaluate(str.replace(" ",""));
                                                    TextView textView = (TextView) findViewById(R.id.ans);
                                                    textView.setText("" + result);
                                                }
                                                else { //if (!singleBoard[position].equals("="))
                                                    str += ("" + singleBoard[position]);
                                                    TextView textView = (TextView) findViewById(R.id.ans);
                                                    textView.setText(str);
                                                    myStack.push(singleBoard[position]);
                                                }

                                            }
                                        }
        );
    }

    /*public void onClickEqual(View v)
    {
        TextView textView = (TextView) findViewById(R.id.clearbutton);
        textView.clearComposingText();
    }*/

}