package animalhusbandry.android.com.animalhusbandry.Activities.RetroFit;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by grewalshavneet on 5/10/2017.
 */

public class RetroUtils {
    private Context context;
    public String strSessionId,strDeviceToken;

    public RetroUtils(Context context) {
        this.context = context;
    }

    public ApiEndpointInterface getApiClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final SharedPreferences sharedPreferences = context.getSharedPreferences("Options", MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                strSessionId= sharedPreferences.getString("strSessionId", "");
                strDeviceToken = sharedPreferences.getString("strDeviceToken", "");
                Request original = chain.request();
                Request request;
                if (!(strSessionId.equals(""))) {
                    request = original.newBuilder()
                            .header("Content-Type", " application/json")
                            .header("Application-Type", "ANDROID")
                            .header("sessionId", strSessionId)
                            .header("Device-Token", strDeviceToken)
                            .header("Application-Token", "ADMN1999F5HJ321MSELSYG5FS5R2IJ8K6GEFJ1BK9NZW10ZMBQLT7CU9TFQH80A8N")
                            .method(original.method(), original.body())
                            .build();
                }
                else {
                    request = original.newBuilder()
                            .header("Content-Type", " application/json")
                            .header("Application-Type", "ANDROID")
                            .header("Device-Token", "xxxxxxxxxxxxx")
                            .header("Application-Token", "ADMN1999F5HJ321MSELSYG5FS5R2IJ8K6GEFJ1BK9NZW10ZMBQLT7CU9TFQH80A8N")
                            .method(original.method(), original.body())
                            .build();
                }
                return chain.proceed(request);
            }
        }).addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiEndpointInterface.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiEndpointInterface.class);
    }

    public static Retrofit create() {
        return new Retrofit.Builder()
                .baseUrl(ApiEndpointInterface.URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
