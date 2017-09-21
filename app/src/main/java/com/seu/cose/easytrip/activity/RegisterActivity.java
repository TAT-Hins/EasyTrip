package com.seu.cose.easytrip.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seu.cose.easytrip.Override.GsonPaserUtils;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppCompatActivity;
import com.seu.cose.xutils3.EasyTripApplication;
import com.seu.cose.xutils3.XUtilsTools;
import com.seu.cose.xutils3.pojo.UserInfo;
import com.seu.cose.xutils3.pojo.UserLogin;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseAppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String TAG = "LoginActivity";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    @ViewInject(R.id.account_register)
        private AutoCompleteTextView mAccountView;
    @ViewInject(R.id.password_register)
        private EditText mPasswordView;
    @ViewInject(R.id.register_progress)
        private View mProgressView;
    @ViewInject(R.id.register_form)
        private View mRegisterFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the login form.

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button mAccountSignInButton = (Button) findViewById(R.id.account_register_button);
        mAccountSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        mAccountView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userAccount = mAccountView.getText().toString();
        String userPswd = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(userPswd) && !isPasswordValid(userPswd)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(userAccount)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        } else if (!isAccountValid(userAccount)) {
            mAccountView.setError(getString(R.string.error_invalid_account));
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            registerUser(userAccount, userPswd);
        }
    }

    private boolean isAccountValid(String account) {
        //TODO: Replace this with your own logic
        return account.length() <= 20 ;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return (password.length() >= 6 && password.length() <= 20);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
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
    }

    private void registerUser(String account, String password){
        Map<String, String> params = new HashMap<>();
        params.put("id", "0");
        params.put("account", account);
        params.put("password", password);
        XUtilsTools.Post(getResources().getString(R.string.server_url) + "/user/register", params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, result);
                Gson gson = new Gson();
                Map<String, String> userBean = gson.fromJson(result, HashMap.class);
                if(userBean.get("id") != "0"){
                    //EasyTripApplication application = (EasyTripApplication)getApplication();
                    //application.setLoginUser(loginUsers);
                    Toast.makeText(getApplicationContext(), "注册成功！请退出重新登录！", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {   }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {   }

            @Override
            public void onFinished() {

            }
        });
    }

}

