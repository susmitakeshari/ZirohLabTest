package app.com.zirohlabs.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = @Index(unique = true,value = {"albumId","imageid"},name = "idx1_albumid_image_id"))
public class EntityAlbum implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "albumId",index = true)
    private int albumId;
    @ColumnInfo(name = "imageid",index = true)
    private int imageid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }
}
