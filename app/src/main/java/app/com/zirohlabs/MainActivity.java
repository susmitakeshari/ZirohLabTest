package app.com.zirohlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.com.zirohlabs.adapter.AlbumAdapter;
import app.com.zirohlabs.databinding.ActivityMainBinding;
import app.com.zirohlabs.response.Album;
import app.com.zirohlabs.room.AppDatabse;
import app.com.zirohlabs.room.DatabaseClient;
import app.com.zirohlabs.room.EntityAlbum;
import app.com.zirohlabs.util.LiveDataUtil;
import app.com.zirohlabs.viewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    MainActivityViewModel viewModel;
    ActivityMainBinding binding;
    int page = 1, limit = 10;
    private AlbumAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    int lastPosition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        layoutManager=new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        viewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.init();
        getDataFromAPI(page,limit);
        getSelectData();

        binding.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (page<10 && scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    binding.idPBLoading.setVisibility(View.VISIBLE);
                    page++;
                    getDataFromAPI(page, limit);
                }
                else if(page>=2 && scrollY ==0){
                    binding.idPBLoading.setVisibility(View.VISIBLE);
                    page--;
                    getDataFromAPI(page, limit);
                }
            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


    }
    private void getDataFromAPI(int page, int limit) {
        binding.albumId.setText("AlbumId:");
        viewModel.getAlbumsResponseLiveData(page).observe(this, images -> {
            if(images!=null){
                Album album = new Album(page);
                album.setImages(images);
                adapter = new AlbumAdapter(album, MainActivity.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setAdapter(adapter);
                binding.idPBLoading.setVisibility(View.GONE);
                binding.idNestedSV.scrollTo(50,100);
                binding.albumId.setText("AlbumId: "+String.valueOf(page));

            }
        });
    }
    public void getSelectData(){
        List<EntityAlbum> removedImages;
        try {
             removedImages= LiveDataUtil.getData(viewModel.getImageList(1,1));
             Log.i("gsgsfvvv",String.valueOf(removedImages!=null?removedImages.size():0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*viewModel.getImageList(1,1).observe(this,entityAlbums -> {
            if(entityAlbums!=null){

            }
        });*/
    }
   public void insertData(EntityAlbum entityAlbum){
        viewModel.insert(entityAlbum);
   }
}