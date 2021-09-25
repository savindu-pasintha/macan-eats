package spl.support.machaneatesmerchent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import spl.support.machaneatesmerchent.databasePkg.DB_Operation;
import spl.support.machaneatesmerchent.firebasePkg.PushNotific;
import spl.support.machaneatesmerchent.modelsPkg.ProfileModel;
import spl.support.machaneatesmerchent.restServicePkg.MySingleton;
import spl.support.machaneatesmerchent.restServicePkg.RESTService;

public class Login extends AppCompatActivity {

    private String username, password;
    TextInputLayout usernameTextInputLayout,passwordTextInputLayout;
    TextInputEditText usernameEditText, passwordEditText;
    Button loginBtn;
    DB_Operation db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //send push notification
     //   new PushNotific().send();

        usernameTextInputLayout =(TextInputLayout)findViewById(R.id.usernameTextinputLayout);
        passwordTextInputLayout= (TextInputLayout)findViewById(R.id.passwordTextinputLayout);
        usernameEditText=(TextInputEditText)findViewById(R.id.usernameEditText);
        passwordEditText=(TextInputEditText)findViewById(R.id.passwordEditText);
        loginBtn = (Button)findViewById(R.id.lbtn);
        loginBtn.setClickable(true);

     //   db=new DB_Operation(this);

    }

    public void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public boolean validate_username(){
        if(usernameEditText.getText().toString().trim().isEmpty()){
            usernameTextInputLayout.setError("Enter User Name !");
            requestFocus(usernameEditText);
            return false;
        }else{
            if(usernameEditText.getText().toString().trim().length()<3){
                usernameTextInputLayout.setError("Minimum 3 characters !");
                requestFocus(usernameEditText);
                return false;
        }else {
                usernameTextInputLayout.setErrorEnabled(false);
            }
    }
        return true;
    }

    public boolean validate_password(){
        if(passwordEditText.getText().toString().trim().isEmpty()){
            passwordTextInputLayout.setError("Enter Password !");
            requestFocus(usernameEditText);
            return false;
        }else{
            if(passwordEditText.getText().toString().trim().length()<6){
                passwordTextInputLayout.setError("Minimum 6 characters !");
                requestFocus(usernameEditText);
                return false;
            }else {
                passwordTextInputLayout.setErrorEnabled(false);
            }
        }
        return true;
    }

   //login button click event
    public void loginBtnClick(View view){
        check_user_authentication();
    }

    public void check_user_authentication(){
      // Instantiate the RequestQueue.
        //new RESTService().url;
        String urlHttp = "https://sub.nvision.lk/ecommerce_cloud/api/login_auth.php";
        //create json object
        JSONObject sendJsonData = new JSONObject();
        try {
            /*action => LOGIN_AUTH login_username => Login Username login_password => Login password */
            sendJsonData.put("action","LOGIN_AUTH");
            sendJsonData.put("login_username",username);
            sendJsonData.put("login_password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Type 2- json Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, urlHttp, sendJsonData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                       try {
                             /*
                        SUCCESS Response :{"error": "","status": "SUCCESS","result": {"Status": 1,"Description": "Login successful","shop_id": "100""device_key": "M0000100"}}
                        ERROR RESPONSE  : {"error": "Unknown action","status": "ERROR","result": {"Status": 0}}
                             */
                           JSONObject resultObj = response.getJSONObject("result");
                           String shopId = resultObj.getString("shop_id");
                           String deviceKey = resultObj.getString("device_key");
                           String status = resultObj.getString("status");
                          //execute
                           submitForm(username,password,status,Integer.parseInt(shopId),deviceKey);

                       } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
              MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void submitForm(String resName,String resPass,String resStatus,int shopId, String deviceKey){
        String status = "SUCCESS";

        if(!validate_password()){return;}
        if(!validate_username()){return;}

        username = usernameEditText.getText().toString().trim();
        password=passwordEditText.getText().toString().trim();
        if(username.equals(resName)&&password.equals(resPass)){
            alertMethod("Login","Success !");
           if(status.equals(resStatus)){
                profile_add(shopId,deviceKey);
            }else{
                alertMethod("Insert Failed", "");
            }
        }else{
            alertMethod("Login","Failed !");
        }

    }

    public void profile_add(int shopIdInt,String deviceKey){
        ProfileModel pObj =new ProfileModel();
        pObj.setShopId(shopIdInt);
        pObj.setDeviceKey(deviceKey);
        if (db.insert_profile(pObj) > 0) {
            alertMethod("Profile Data Insert Successfully.","");
        } else {
            alertMethod("Insert Failed", "");
        }

    }

    public void alertMethod(String title,String msg){
        try{
            //1-builder to a set alert values
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(msg);
             //2- execute alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }catch(Exception e){
            Log.e("error alertMethod()",e.getMessage().toString());
        }

    }



}