package com.example.quang.appphonee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaypalDetails extends AppCompatActivity {

    TextView id,amount,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_details);

        id = findViewById(R.id.txtid);
        amount = findViewById(R.id.txtAmount);
        status = findViewById(R.id.txtStatus);

        Intent intent = getIntent();

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymenDetails"));
            showDetails(jsonObject.getJSONObject("reponse"),intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject reponse, String paymentAmount) throws JSONException {
        try{
            id.setText(reponse.getString("id"));
            status.setText(reponse.getString("state"));
            amount.setText("$%s"+paymentAmount);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
