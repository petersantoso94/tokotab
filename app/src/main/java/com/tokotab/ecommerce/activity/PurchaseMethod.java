package com.tokotab.ecommerce.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.adapter.BankAdapter;
import com.tokotab.ecommerce.adapter.ProductAllAdapter;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.json.JsonParser;
import com.tokotab.ecommerce.model.Bank;
import com.tokotab.ecommerce.model.City;
import com.tokotab.ecommerce.model.Produk;
import com.tokotab.ecommerce.model.Province;
import com.tokotab.ecommerce.model.SpinnerColor;
import com.tokotab.ecommerce.model.SpinnerUom;
import com.tokotab.ecommerce.model.Transaksi;
import com.tokotab.ecommerce.model.User;
import com.tokotab.ecommerce.utils.CheckNetwork;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PurchaseMethod extends AppCompatActivity {
    Spinner purchase;
    Button btn_save_purc;
    private SharedPreferences sharedPreferences,pref;
    private RecyclerView recyclerView;
    private BankAdapter bankAdapter;
    private List<Bank> listBank = new ArrayList<>();
    SharedPreferences.Editor editor2;
    private String[] ProdukInternalID, UOMInternalID, ColorInternalID, Qty, ProdukName,
            ProdukPrice, ColorName, ColorHexa, UOMName, ProdukPicture;
    private String CustomerInternalID, CustomerEmail, Destination, CustomerName, HeaderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_method);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_purchase);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pembayaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //init
        purchase = (Spinner) findViewById(R.id.pembayaran_spinner);
        btn_save_purc = (Button) findViewById(R.id.btn_save_purchase);
        String[] jenis = {"transfer"};
        pref = getApplicationContext().getSharedPreferences("cart", 0); // 0 - for private mode
        sharedPreferences = getApplicationContext().getSharedPreferences("user", 0);
        editor2 = pref.edit();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_purchase_method);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Destination = pref.getString(Transaksi.ARG_ALAMAT,"none");

        //filling purchase spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,jenis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purchase.setAdapter(adapter);

        //json
        if(CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {
            prepareTask a = new prepareTask();
            a.execute();
        }else
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Anda tidak tersambung ke internet, coba lagi?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

        //action button
        btn_save_purc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String previousName = pref.getString(Produk.ARG_PRODUCT_NAME, "-,-");
                String previousUOMID = pref.getString(SpinnerUom.ARG_INTERNAL_ID, "-,-");
                String previousUOMName = pref.getString(SpinnerUom.ARG_UOM_ID, "-,-");
                String previousID = pref.getString(Produk.ARG_INTERNAL_ID, "-,-");
                String previousQty = pref.getString("qty", "-,-");
                String previousColorInternalID = pref.getString(SpinnerColor.ARG_INTERNAL_ID, "-,-");
                String previousColorHexa = pref.getString(SpinnerColor.ARG_COLOR_HEXA, "-,-");
                String previousColorName = pref.getString(SpinnerColor.ARG_COLOR_NAME, "-,-");
                String previousPrice = pref.getString(Produk.ARG_PRODUCT_PRICE, "-,-");
                String previousPict = pref.getString(Produk.ARG_PP_1, "-,-");

                ProdukInternalID = previousID.split("-,-");
                UOMInternalID = previousUOMID.split("-,-");
                ColorInternalID = previousColorInternalID.split("-,-");
                Qty = previousQty.split("-,-");
                CustomerInternalID = sharedPreferences.getString(User.INTERNAL_ID, "-1");
                CustomerEmail = sharedPreferences.getString(User.MEMBER_EMAIL, "-1");
                CustomerName = sharedPreferences.getString(User.MEMBER_NAME, "-1");
                ProdukName = previousName.split("-,-");
                ProdukPrice = previousPrice.split("-,-");
                ColorName = previousColorName.split("-,-");
                ColorHexa = previousColorHexa.split("-,-");
                UOMName = previousUOMName.split("-,-");
                ProdukPicture = previousPict.split("-,-");

                senddatatoserver();
            }
        });
    }

    private class prepareTask extends AsyncTask<String,Void,List<Bank>> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PurchaseMethod.this, "Please wait", "Receiving data..", true, false);
        }
        @Override
        protected List<Bank> doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            InputStream dataAll = httpClient.getData("http://home.stationeryone.com/getDataBank");
            JsonParser jsoNparser = new JsonParser();
            listBank = jsoNparser.getDataBank(dataAll);
            return  listBank;
        }
        @Override
        protected void onPostExecute(List<Bank> produks) {
            super.onPostExecute(produks);
            progressDialog.dismiss();
            bankAdapter = new BankAdapter(listBank);
            bankAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(bankAdapter);
        }
    }

    public void senddatatoserver() {
        //function in the activity that corresponds to the layout button
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        String objek = "";
                        for (int i = 0; i < ProdukInternalID.length; i++) {
                            if (i == 0)
                                objek = ProdukInternalID[i] + ";" + UOMInternalID[i] + ";" + ColorInternalID[i] + ";" + Qty[i];
                            else
                                objek += "--,--" + ProdukInternalID[i] + ";" + UOMInternalID[i] + ";" + ColorInternalID[i] + ";" + Qty[i];
                        }
                        objek += "--`--" + CustomerInternalID + "-;-" + Destination+"-;-"+pref.getString(Province.ARG_INTERNAL_ID,"")+"-;-"+pref.getString(City.ARG_INTERNAL_ID,"")+"-;-"+pref.getString(Transaksi.ARG_GRAND_TOTAL,"")+"-;-"+pref.getString(Transaksi.ARG_SHIPPING,"")+"-;-"+pref.getString(Transaksi.ARG_DISKON,"");
                        if (!objek.equals("")) {
                            if (CheckNetwork.isInternetAvailable(getApplicationContext())) //returns true if internet available
                            {
                                new SendDataToServer().execute(objek);
                            } else {
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //Yes button clicked
                                                finish();
                                                overridePendingTransition(0, 0);
                                                startActivity(getIntent());
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("Anda tidak tersambung ke internet, coba lagi?").setPositiveButton("ya", dialogClickListener)
                                        .setNegativeButton("tidak", dialogClickListener).show();
                            }
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah anda yakin untuk melanjutkan pembelian?").setPositiveButton("ya", dialogClickListener)
                .setNegativeButton("tidak", dialogClickListener).show();

    }

    public void afterSending() {
        Intent i = new Intent(PurchaseMethod.this,AfterPurchase.class);
        startActivity(i);
    }

    private class SendDataToServer extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new HttpClient();
            String response = httpClient.sendJSON(params[0], "http://home.stationeryone.com/saveCart");
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            editor2.putString(Transaksi.ARG_SALES_HEADER,response);
            editor2.commit();
            HeaderID = response;
            sendMail(CustomerEmail,"test",setMessageBody());
        }
    }
    private String setMessageBody(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String a="<html><head><style>table, td, th {\n" +
                "    border: 1px solid grey;\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    border-collapse: collapse;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "th {\n" +
                "    text-align: left;\n" +
                "}</style></head><body>" +
                "<h2>Detail Order Tabah Stationery</h2>\n" +
                "\n" +
                "            <p>Dear, "+CustomerName+"</p>\n" +
                "            <p>Berikut adalah detail pesanan anda:</p>\n" +
                "            <table>\n" +
                "                <tbody>\n" +
                "                    <tr>\n" +
                "                        <td style=\"text-align: right\">Sales Order :</td>\n" +
                "                        <td>"+HeaderID+"</td>\n" +
                "                        <td style=\"width: 10%\"></td>\n" +
                "                        <td>Stationeryone.com</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"text-align: right\">Tanggal :</td>\n" +
                "                        <td>"+timeStamp+"</td>\n" +
                "                        <td style=\"width: 10%\"></td>\n" +
                "                        <td>Jl. Ngagel Jaya Sel. No.45, Wonokromo, Kota SBY, Jawa Timur 60283</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"text-align: right\">Alamat Kirim :</td>\n" +
                "                        <td>"+Destination+"</td>\n" +
                "                        <td style=\"width: 10%\"></td>\n" +
                "                        <td>Tlp: (031) 5023645 || HP: - || Fax: -</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td style=\"text-align: right\"></td>\n" +
                "                        <td>"+pref.getString(Transaksi.ARG_KOTA,"none")+"</td>\n" +
                "                        <td style=\"width: 10%\"></td>\n" +
                "                        <td></td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>"+
                "<br><table><tr><th>Nama Barang</th><th>Jumlah</th><th>Harga</th><th>Subtotal</th></tr>";
        int grandTotal = 0;
        for(int i=0;i<ProdukInternalID.length;i++) {
            BigDecimal abc = new BigDecimal(Double.parseDouble(ProdukPrice[i])*Integer.parseInt(Qty[i]));
            String cob = abc.toPlainString();
            a = a + "<tr><td>" + ProdukName[i] + "</td>" + "<td>" + Qty[i] + "</td>" + "<td style=\"text-align:right;\">Rp." + String.format("%1$,.2f",Double.parseDouble(ProdukPrice[i])) + "</td>" + "<td style=\"text-align:right;\">Rp." + String.format("%1$,.2f",Double.parseDouble(cob)) + "</td>" + "</tr>";
            grandTotal += (Double.parseDouble(ProdukPrice[i])*Integer.parseInt(Qty[i]));
        }
        a=a+"<tr><td></td><td></td><td>Shipping</td><td style=\"text-align:right;\">Rp."+String.format("%1$,.2f",Double.parseDouble(pref.getString(Transaksi.ARG_SHIPPING,"0")))+"</td></tr>";
        a=a+"<tr><td></td><td></td><td>Diskon</td><td style=\"text-align:right;\">Rp."+String.format("%1$,.2f",Double.parseDouble(pref.getString(Transaksi.ARG_DISKON,"0")))+"</td></tr>";
        a=a+"<tr><td></td><td></td><td>Grand Total</td><td style=\"text-align:right;\">Rp."+String.format("%1$,.2f",Double.parseDouble(pref.getString(Transaksi.ARG_GRAND_TOTAL,"0")))+"</td></tr></table><p>Pesanan anda sedang di proses.</p>\n" +
                "            <p>Silahkan menunggu email konfirmasi pesanan anda dari Tabah Stationery.</p>\n" +
                "            <p>Terima Kasih, </p>\n" +
                "            <p>Tabah Stationery</p></body></html>";
        return a;
    }
    private void sendMail(String email, String subject, String messageBody) {
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("petercoba202@gmail.com", "Peter123!");
            }
        });
    }

    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("hai@tiemenschut.com", "COBA COBA DULU"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setContent(messageBody,"text/html");
        return message;
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PurchaseMethod.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            afterSending();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
