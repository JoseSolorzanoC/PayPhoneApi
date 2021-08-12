package com.uteq.appmoviles.payphoneapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uteq.appmoviles.payphoneapi.models.ConsultSaleResponse;
import com.uteq.appmoviles.payphoneapi.models.CreateSaleRequest;
import com.uteq.appmoviles.payphoneapi.models.CreateSaleResponse;
import com.uteq.appmoviles.payphoneapi.services.SaleService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://pay.payphonetodoesposible.com/api/";
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        EditText txtCountryCode = findViewById(R.id.txtCountryCode);
        EditText txtSubTotal = findViewById(R.id.txtSubTotal);
        CheckBox chkWithIva = findViewById(R.id.chkWithIva);
        Button btnGenerar = findViewById(R.id.btnGenerar);
        txtResult = findViewById(R.id.txtResult);

        btnGenerar.setOnClickListener(new View.OnClickListener() {
            double amount = 0;
            double amountWithoutTax = 0;
            double amountWithTax = 0;
            double tax = 0;
            double ivaValue = 12.0;

            @Override
            public void onClick(View view) {
                //Validación de IVA
                if (chkWithIva.isChecked()){
                    amountWithTax = Double.parseDouble(txtSubTotal.getText().toString()) * 100;
                    tax = amountWithTax * (ivaValue / 100);
                    amount =  tax + amountWithTax;
                } else {
                    amount = Double.parseDouble(txtSubTotal.getText().toString()) * 100;
                    amountWithoutTax = Double.parseDouble(txtSubTotal.getText().toString()) * 100;
                }

                //Declaración de modelo request
                CreateSaleRequest request = new CreateSaleRequest(
                        txtPhoneNumber.getText().toString(),
                        txtCountryCode.getText().toString(),
                        (int)amount,
                        (int)amountWithTax,
                        (int)amountWithoutTax,
                        (int)tax,
                        UUID.randomUUID().toString()
                );

                //Ejecución
                try {
                    servicioRetrofit(request);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void servicioRetrofit(CreateSaleRequest request) throws PackageManager.NameNotFoundException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getPayPhoneToken());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SaleService saleService = retrofit.create(SaleService.class);
        Call<CreateSaleResponse> saleResponseCall = saleService
                .createSale(headers, request);

        saleResponseCall.enqueue(new Callback<CreateSaleResponse>() {
            @Override
            public void onResponse(Call<CreateSaleResponse> call, Response<CreateSaleResponse> response) {
                if (response.isSuccessful()){
                    //Toast.makeText(MainActivity.this, response.body().getTransactionId(), Toast.LENGTH_SHORT);
                    Intent intent = new Intent("payPhone_Android.PayPhone_Android.Purchase");
                    Gson gson = new Gson();
                    intent.putExtra("otherApp", true);
                    //String parameters = gson.toJson("{ClientID, Amount}");
                    intent.putExtra("parameters", (Bundle) null);
                    intent.putExtra("parameterName", "parameters" );
                    String packageName = getApplicationContext().getPackageName();
                    intent.putExtra("package", packageName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    try {
                        getSaleStatus(Long.parseLong(response.body().getTransactionId()));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateSaleResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ocurrió un error al intentar realizar la orden de pago", Toast.LENGTH_SHORT);
            }
        });
    }

    private String getPayPhoneToken() throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
        return applicationInfo.metaData.getString("payPhoneToken");
    }

    private  void getSaleStatus(long transactionId) throws PackageManager.NameNotFoundException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + getPayPhoneToken());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SaleService saleService = retrofit.create(SaleService.class);
        Call<ConsultSaleResponse> saleResponseCall = saleService
                .getSale(headers, transactionId);

        saleResponseCall.enqueue(new Callback<ConsultSaleResponse>() {
            @Override
            public void onResponse(Call<ConsultSaleResponse> call, Response<ConsultSaleResponse> response) {
                if (response.isSuccessful()){
                    txtResult.setText("");
                    txtResult.setText(response.body().getAmount() + "\n" + response.body().getClientTransactionId()
                            + "\n" + response.body().getTransactionStatus() + "\n" + response.body().getTransactionId()
                            + "\n" + response.body().getDocument() + "\n" + response.body().getStoreName());
                }else
                {
                    Toast.makeText(MainActivity.this, "Ocurrió un error al intentar consultar la orden de pago", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ConsultSaleResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ocurrió un error al intentar consultar la orden de pago", Toast.LENGTH_SHORT);
            }
        });
    }
}