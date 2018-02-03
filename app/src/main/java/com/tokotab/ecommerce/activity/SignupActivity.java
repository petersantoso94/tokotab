package com.tokotab.ecommerce.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tokotab.ecommerce.R;
import com.tokotab.ecommerce.json.HttpClient;
import com.tokotab.ecommerce.utils.CheckNetwork;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignupActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    private RegisterTask mAuthTask = null;
    private View mProgressView;
    private View mRegisterFormView;
    private EditText mPasswordView, mUlangiPassword, mAddress, mPhone, mName, mEmailView, mLastName;
    private String hasil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        mEmailView = (EditText) findViewById(R.id.email_register);
        mPasswordView = (EditText) findViewById(R.id.password_register);
        mUlangiPassword = (EditText) findViewById(R.id.ulangi_password);
        mAddress = (EditText) findViewById(R.id.adrress);
        mPhone = (EditText) findViewById(R.id.phone);
        mName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastName);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        collapsingToolbarLayout.setTitle("Registrasi");

        Button registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mUlangiPassword.setError(null);
        mAddress.setError(null);
        mPhone.setError(null);
        mName.setError(null);
        mLastName.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String nama = mName.getText().toString();
        String lastName = mLastName.getText().toString();
        String alamat = mAddress.getText().toString();
        String telp = mPhone.getText().toString();
        String ulangi = mUlangiPassword.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //check name
        if (TextUtils.isEmpty(nama)) {
            mName.setError("Field Harus Diisi");
            focusView = mName;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            mLastName.setError("Field Harus Diisi");
            focusView = mLastName;
            cancel = true;
        }

        //check alamat
        if (TextUtils.isEmpty(alamat)) {
            mAddress.setError("Field Harus Diisi");
            focusView = mAddress;
            cancel = true;
        }

        //check telp
        if (TextUtils.isEmpty(telp)) {
            mPhone.setError("Field Harus Diisi");
            focusView = mPhone;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Field Harus Diisi");
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(ulangi)) {
            mUlangiPassword.setError("Field Harus Diisi");
            focusView = mUlangiPassword;
            cancel = true;
        }
        if (!ulangi.equals(password)) {
            mUlangiPassword.setError("Password yang diisi tidak sama");
            focusView = mUlangiPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if (CheckNetwork.isInternetAvailable(this)) //returns true if internet available
            {
                showProgress(true);
                mAuthTask = new RegisterTask(email, nama+" "+lastName, telp, alamat, password);
                mAuthTask.execute();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Anda tidak tersambung ke internet, coba lagi?").setPositiveButton("ya", dialogClickListener)
                        .setNegativeButton("tidak", dialogClickListener).show();
            }
        }
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public class RegisterTask extends AsyncTask<String, Void, Boolean> {

        private final String mEmail, mPassword, mNama, mTelp, mAlamat;

        public RegisterTask(String mEmail, String mNama, String mTelp, String mAlamat, String mPassword) {
            this.mEmail = mEmail;
            this.mPassword = mPassword;
            this.mNama = mNama;
            this.mTelp = mTelp;
            this.mAlamat = mAlamat;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            HttpClient httpClient = new HttpClient();
            hasil = httpClient.sendJSON(mEmail + "--,--" + mNama + "--,--" + mTelp + "--,--" + mPassword + "--,--" + mAlamat, "http://home.stationeryone.com/signUpAndroid");

            if (!hasil.isEmpty()) {
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d("hasil:", hasil);
            mAuthTask = null;
            showProgress(false);
            hasil = hasil.trim();
            if (hasil.equals("no")) {
                mEmailView.setError("Email sudah terpakai.");
                mEmailView.requestFocus();
            }else{
                sendMail(mEmail,"Registration",setMessageBody());
            }
        }
    }
    private String setMessageBody(){
        String a="<html><head><meta charset=\"utf-8\">" +
                "</head><body>" +
                "<div>\n" +
                "            <h2>Aktifasi akun anda</h2>\n" +
                "\n" +
                "            <p>Dear, "+mName.getText().toString()+"</p>\n" +
                "            <p>\n" +
                "                Anda selangkah lagi dalam mengaktifkan akun anda.\n" +
                "                Silahkan tekan link di bawah ini untuk melakukan verifikasi email anda.\n" +
                "                <button><a href=\""+hasil+"\">Verifikasi sekarang</a></button>\n" +
                "            </p>\n" +
                "            <p>Jika link di atas tidak dapat digunakan, silahkan memilih atau melakukan copy link di bawah ini dan melakukan paste pada browser anda:\n" +
                "            </p>\n" +
                "            <a href=\""+hasil+"\">"+hasil+"</a>\n" +
                "            <p>Terima Kasih,</p>\n" +
                "            <p>Tabah Stationery</p>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";
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
                return new PasswordAuthentication("service.tokotab@gmail.com", "Tabah123");
            }
        });
    }

    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("tokotab.com", "Registration Confirmation"));
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
            progressDialog = ProgressDialog.show(SignupActivity.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Sukses mendaftar. Aktifkan akun anda melalui email.", Toast.LENGTH_LONG).show();
            finish();
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
