package app.com.zirohlabs.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.chrono.MinguoChronology.INSTANCE;

@Database(entities = {EntityAlbum.class},version = 1)
public abstract class AppDatabse extends RoomDatabase {
    private static final String DB_NAME = "database.db";
    private static AppDatabse instance;
    public abstract DAOAlbum daoAlbum();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabse getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabse.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabse.class, DB_NAME).
                            addCallback(sRoomDatabaseCallback).
                    build();
                }
            }
        }
        return instance;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                DAOAlbum dao = instance.daoAlbum();
                EntityAlbum entityAlbum = new EntityAlbum();
                entityAlbum.setImageid(2);
                entityAlbum.setAlbumId(1);
                dao.insert(entityAlbum);
            });
        }
    };
}
