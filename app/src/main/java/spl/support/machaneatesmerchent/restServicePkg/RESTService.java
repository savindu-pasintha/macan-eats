package spl.support.machaneatesmerchent.restServicePkg;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RESTService {
   public String url ="https://sub.nvision.lk/ecommerce_cloud/api/login_auth.php";
   /* Instantiate the RequestQueue.
   RequestQueue queue = Volley.newRequestQueue(this);
  */

   //tutorial https://developer.android.com/training/volley/request
   //https://www.youtube.com/watch?v=xPi-z3nOcn8
   //https://www.youtube.com/watch?v=1Y_5ot6YLno
     /*
        //Type 1- string Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlHttp,
        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the  response string.
                        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"REST SERVICE NOT WORKING !", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
         */

/*
   //Type 2- json Request a string response from the provided URL.
   JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
           (Request.Method.POST, urlHttp, null, new Response.Listener<JSONObject>() {

              @Override
              public void onResponse(JSONObject response) {
                 Jso
                 Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
              }
           }, new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();

              }
           });
             queue.add(jsonObjectRequest);
   // Access the RequestQueue through your singleton class.
   //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
*/


   //Type 3 JsonArrayObject
}
