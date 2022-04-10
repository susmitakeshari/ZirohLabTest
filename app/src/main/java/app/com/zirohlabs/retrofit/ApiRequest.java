package app.com.zirohlabs.retrofit;


import java.util.List;
import app.com.zirohlabs.response.Album;
import app.com.zirohlabs.response.ImageResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiRequest {
    @GET("albums/{id}/photos")
    Call<List<ImageResponse>> getAlbums(@Path(value="id") int id);
    /*@GET("albums/{id}/photos")
    Call<List<AlbumsResponse>> getAlbums(@Path("albumId") int id);*/


}
