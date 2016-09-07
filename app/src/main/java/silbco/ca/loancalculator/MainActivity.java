package silbco.ca.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void calculate(View view) {

        try {
            double loanAmount = Double.parseDouble(((EditText) findViewById(R.id.loanAmount)).getText().toString());
            int termOfLoan = Integer.parseInt(((EditText) findViewById(R.id.termOfLoan)).getText().toString());
            double yearlyInterestRate = Double.parseDouble(((EditText) findViewById(R.id.yearlyInterestRate)).getText().toString());

            Toast.makeText(MainActivity.this, "Please enter values", Toast.LENGTH_SHORT).show();


            double monthlyPayment = getMonthlyPayment(termOfLoan, yearlyInterestRate, loanAmount);
            ((TextView) findViewById(R.id.monthlyPayment)).setText(monthlyPayment + "");

            double totalLoan = getTotalPayment(termOfLoan, monthlyPayment);
            ((TextView) findViewById(R.id.totalPayment)).setText(round(totalLoan, 2) + "");

            double totalInterest = getTotalInterest(loanAmount, totalLoan);
            ((TextView) findViewById(R.id.totalInterest)).setText(round(totalInterest, 2) + "");
        }
        catch(Exception NPE)
        {
            Toast.makeText(MainActivity.this, "Please enter values", Toast.LENGTH_SHORT).show();
            clear(null);
        }

    }
    public double getTotalInterest(double loanAmount, double totalLoan){

        return totalLoan - loanAmount;
    }
    public double getTotalPayment(int termOfLoan, double monthlyPayment){


        return  monthlyPayment * termOfLoan * 12;


    }

    public double getMonthlyPayment(int termOfLoan, double yearlyInterestRate, double loanAmount){

        double monthlyPayment;
        double monthlyInterestRate;
        int numberOfPayments;
        if (termOfLoan != 0 && yearlyInterestRate != 0)
        {
            //calculate the monthly payment
            monthlyInterestRate = yearlyInterestRate / 1200;
            numberOfPayments = termOfLoan * 12;

            monthlyPayment =
                    (loanAmount * monthlyInterestRate) /
                            (1 - (1 / Math.pow ((1 + monthlyInterestRate), numberOfPayments)));

            monthlyPayment = Math.round (monthlyPayment * 100) / 100.0;
        }
        else
            monthlyPayment = 0;
        return monthlyPayment;


    }
    public void clear(View view){

        EditText temp = (EditText)findViewById(R.id.loanAmount);
        temp.setText("");
        temp = (EditText)findViewById(R.id.termOfLoan);
        temp.setText("");
        temp = (EditText)findViewById(R.id.yearlyInterestRate);
        temp.setText("");

        TextView temp2 = (TextView)findViewById(R.id.monthlyPayment);
        temp2.setText("");
        temp2 = (TextView)findViewById(R.id.totalPayment);
        temp2.setText("");
        temp2 = (TextView)findViewById(R.id.totalInterest);
        temp2.setText("");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
