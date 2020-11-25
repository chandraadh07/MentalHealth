package com.example.mentalhealth

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.net.URL
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * @constructor : to create a ThumbnailLoader instance, use ThumbnailLoader.getInstance()
 */
class ThumbnailLoader {

    var retrofit = getRetrofitClient()
    val service = retrofit.create(ThumbnailService::class.java)

    fun getRetrofitClient(): Retrofit {
        Log.e("TAG","Making RetroFit")
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")//https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.e("TAG","Made RetroFit?")
        return retrofit
    }

    interface ThumbnailService {
        @GET("videos?")
        fun getThumbnail(
            @Query("id") id: String,
            @Query("key") key: String,
            @Query("part") part:String,
            @Query("fields") fields:String
        ): Call<ResponseBody>
    }

    fun fetchGetThumbnail(id: String, key: String="AIzaSyDmiBFPxBdwb6xfHDqUadBbV8FWhIC-Vvk", part: String="snippet",fields:String="items(id,snippet/thumbnails)",imageView: ImageView) {
        Log.e("JSON","videoId: $id")
        val call = service.getThumbnail(id,key,part,fields)
        call.enqueue(ThumbnailCallBack(imageView))
    }

    inner class ThumbnailCallBack (imageView: ImageView):
        Callback<ResponseBody> {

        val imageView = imageView

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("CALL","BAD CALL")
        }

        override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
        ) {
            if (response.isSuccessful) {
                Log.e("CALL","good?? CALL ${response.body()}")
                response.body()?.let {
                    decodeJson(it.string(),imageView)
                }
            }
        }
    }

    fun decodeJson(json: String, imageView: ImageView) {
        Log.e("JSON",json)
        try {
            val thumbnails: JSONObject =
                JSONObject(json).getJSONArray("items").getJSONObject(0).getJSONObject("snippet")
                    .getJSONObject("thumbnails")
            val thumb =
                try {
                    thumbnails.getJSONObject("maxres").getString("url")
                }
                catch (e:org.json.JSONException) {
                    try {
                        thumbnails.getJSONObject("standard").getString("url")
                    }
                    catch (e:org.json.JSONException) {
                        try {
                            thumbnails.getJSONObject("high").getString("url")
                        }
                        catch (e:org.json.JSONException) {
                            try {
                                thumbnails.getJSONObject("medium").getString("url")
                            }
                            catch (e:org.json.JSONException) {
                                thumbnails.getJSONObject("default").getString("url")
                            }
                        }
                    }
                }
            getInstance().loadURL(thumb, imageView)
        }catch(e:Exception){
            true
        }
    }

    companion object {
        private var loader: ThumbnailLoader? = null

        fun getInstance(): ThumbnailLoader {
            if (loader == null) {
                loader = ThumbnailLoader()
            }
            return loader!!
        }
    }

    val baseUrl = "https://www.googleapis.com/youtube/v3/videos?id="
    val endUrl = "&key=AIzaSyDmiBFPxBdwb6xfHDqUadBbV8FWhIC-Vvk&fields=items(id,snippet(thumbnails(standard)))&part=snippet" //just gets standard image

    /**
     * A function to load the poster image from the API
     * @param url: poster path
     * @param imgView: The ImageView to show the returned poster image
     */
    fun loadURL(thumb:String, imgView: ImageView) {
        println(thumb)
        LoaderAsync(imgView).execute(thumb)
    }

    private class LoaderAsync(internal var bmImage: ImageView) :
        AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urldisplay = urls[0]
            Log.e("THUMBNAIL",urldisplay)
            var mIcon11: Bitmap? = null
            try {
                val url = URL(urldisplay)
                mIcon11 = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: Exception) {
                println("error" + e.printStackTrace())
            }

            return mIcon11
        }

        override fun onPostExecute(result: Bitmap) {
            println("result")
            bmImage.setImageBitmap(result)
            println(bmImage)
        }
    }
}

