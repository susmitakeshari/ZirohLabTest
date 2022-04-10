package app.com.zirohlabs.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import app.com.zirohlabs.App;
import app.com.zirohlabs.repository.AlbumsRepository;
import app.com.zirohlabs.response.ImageResponse;
import app.com.zirohlabs.room.EntityAlbum;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<ImageResponse>> albumResponseLiveData;
    private AlbumsRepository albumsRepository;


    public void init(){
        if (albumResponseLiveData != null){
            return;
        }
       Context context=App.context;
        albumsRepository = AlbumsRepository.getInstance(context);
    }

    public LiveData<List<ImageResponse>> getAlbumsResponseLiveData(int id) {
        return albumsRepository.getAlbumslist(id);
    }
    public LiveData<List<EntityAlbum>> getImageList(int albumId,int imageId) {
        return albumsRepository.getImageList(albumId,imageId);
    }
    public LiveData<List<EntityAlbum>> getImageList() {
        return albumsRepository.getImageList();
    }
    public void insert(EntityAlbum entityAlbum) {
        albumsRepository.insert(entityAlbum);
    }



}