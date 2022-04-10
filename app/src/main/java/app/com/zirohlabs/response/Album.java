package app.com.zirohlabs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Album {

    private Integer ablbumId;

    public Album(int id){
        ablbumId = id;
    }

    public Integer getAblbumId() {
        return ablbumId ==null ? 0 : ablbumId;
    }

    public List<ImageResponse> images;

    public List<ImageResponse> getImages() {
        if(images == null)
            images = new ArrayList<>();
        return images;
    }

    public void setImages(List<ImageResponse> images) {
        this.images = images;
    }




}
