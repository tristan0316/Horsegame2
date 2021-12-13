import com.example.horsegame2.jaon.Currency
import retrofit2.Call
import retrofit2.http.GET

interface MyAPIService {
    //Assign the path to the Json file
    @GET("capi.php")
    fun getExchangeRate(): Call<Currency>
}