package app.com.zirohlabs.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import app.com.zirohlabs.App;
import app.com.zirohlabs.response.Album;
import app.com.zirohlabs.response.ImageResponse;
import app.com.zirohlabs.retrofit.ApiRequest;
import app.com.zirohlabs.retrofit.RetrofitRequest;
import app.com.zirohlabs.room.AppDatabse;
import app.com.zirohlabs.room.DAOAlbum;
import app.com.zirohlabs.room.EntityAlbum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlbumsRepository {
    private ApiRequest apiRequest;
    private static AlbumsRepository albumsRepository;
    private DAOAlbum daoAlbum;
    private AppDatabse db;
    private LiveData<List<EntityAlbum>> list;


    public static AlbumsRepository getInstance(Context context){
        if (albumsRepository == null){
            albumsRepository = new AlbumsRepository(context);
        }
        return albumsRepository;
    }

    public AlbumsRepository(Context application){
        apiRequest = RetrofitRequest.createService(ApiRequest.class);
        db = AppDatabse.getInstance(application);
        daoAlbum = db.daoAlbum();

    }
    public LiveData<List<EntityAlbum>> getImageList(int albumId,int imageId){
        list = daoAlbum.getAll(albumId,imageId);
        return list;
    }

    public LiveData<List<EntityAlbum>> getImageList(){
        list = daoAlbum.getAll();
        return list;
    }
    public MutableLiveData<List<ImageResponse>> getAlbumslist(int id){
        MutableLiveData<List<ImageResponse>> data = new MutableLiveData<>();
        Call<List<ImageResponse>> request = apiRequest.getAlbums(id);
        request.enqueue(new Callback<List<ImageResponse>>() {
            @Override
            public void onResponse(Call<List<ImageResponse>> call,
                                   Response<List<ImageResponse>> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ImageResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public void insert(EntityAlbum entityAlbum) {
        AppDatabse.databaseWriteExecutor.execute(() -> {
            db.daoAlbum().insert(entityAlbum);
        });
        //db.daoAlbum().insert(entityAlbum);
    }
}

