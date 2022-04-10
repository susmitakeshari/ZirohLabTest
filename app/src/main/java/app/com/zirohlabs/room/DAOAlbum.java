package app.com.zirohlabs.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import app.com.zirohlabs.response.ImageResponse;

@Dao
public interface DAOAlbum {
    @Query("SELECT * FROM EntityAlbum WHERE albumId = :albumId AND imageid = :imageId")
    LiveData<List<EntityAlbum>> getAll(int albumId,int imageId);
    @Query("SELECT * FROM EntityAlbum")
    LiveData<List<EntityAlbum>> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntityAlbum album);
    @Delete
    void delete(EntityAlbum album);
}
