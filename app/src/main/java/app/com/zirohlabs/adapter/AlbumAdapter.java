package app.com.zirohlabs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import app.com.zirohlabs.MainActivity;
import app.com.zirohlabs.databinding.RowAlbumListBinding;
import app.com.zirohlabs.response.Album;
import app.com.zirohlabs.response.ImageResponse;
import app.com.zirohlabs.room.EntityAlbum;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private Album album;
    private MainActivity context;
    public AlbumAdapter(Album album, MainActivity context) {
        this.album = album;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowAlbumListBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }
    public void remove(int position) {
        album.getImages().remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageResponse response = album.getImages().get(position);
        holder.binding.allbumTitle.setText("Title: "+response.getTitle());
        Picasso.get().load(response.getThumbnailUrl()).into(holder.binding.allbumImg);
        holder.binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntityAlbum entityAlbum=new EntityAlbum();
                entityAlbum.setAlbumId(response.getAlbumId());
                entityAlbum.setImageid(response.getId());
                ((MainActivity)context).insertData(entityAlbum);
                album.getImages().remove(position);
                notifyItemRemoved(position);
            }
        });
       /* holder.binding.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                EntityAlbum entityAlbum=new EntityAlbum();
                entityAlbum.setAlbumId(response.getAlbumId());
                entityAlbum.setImageid(response.getId());
                ((MainActivity)context).insertData(entityAlbum);
                album.getImages().remove(position);
                notifyItemRemoved(position);
                return false;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return album.getImages().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowAlbumListBinding binding;

        public ViewHolder(RowAlbumListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}

