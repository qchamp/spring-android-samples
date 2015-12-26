package org.springframework.android.showcase.to;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Type X: Input postValue class
 * Type Y: Output postValue class
 *
 * @author Yasser Ibrahim
 * Created 26.12.2015.
 */
public abstract class AbstractJsonPostTask<X,Y> extends AsyncTask<Object, Void, X> {

    protected static final String TAG = AbstractJsonPostTask.class.getSimpleName();

    protected String hostUri;
    protected Class<X> responseType ;
    protected Y postValue;

    public AbstractJsonPostTask(Class<X> responseType, String hostUri) {
        this.responseType = responseType;
        this.hostUri = hostUri;
    }

    @Override
    protected X doInBackground(Object... params) {
        Log.i(TAG,"->doInBackground: " + hostUri + " postValue: " + postValue);
        try {
            // The URL for making the POST request
            final String url = hostUri + "/sendmessage";

            HttpHeaders requestHeaders = new HttpHeaders();

            // Sending a JSON or XML object i.e. "application/json" or "application/xml"
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            // Populate the Object object to serialize and headers in an
            // HttpEntity object to use for the request
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(postValue, requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // Make the network request, posting the postValue, expecting a String in response from the server
            ResponseEntity<X> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);

            // Return the response body to display to the user
            final X body = response.getBody();
            Log.i(TAG,"<- doInBackground: body " + body );

            return body;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return null;
    }


}
