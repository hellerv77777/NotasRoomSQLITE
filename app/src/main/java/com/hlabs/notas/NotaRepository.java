package com.hlabs.notas;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hlabs.notas.db.NotaRoomDataBase;
import com.hlabs.notas.db.dao.NotaDao;
import com.hlabs.notas.db.entity.NotaEntity;

import java.util.List;

public class NotaRepository {

    private NotaDao notaDao;
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> allNotasFavoritas;


    public NotaRepository(Application application) {

        NotaRoomDataBase db = NotaRoomDataBase.getDataBase(application);
        notaDao = db.notaDao();
        allNotas = notaDao.getAll();
        allNotasFavoritas=notaDao.getAllFavorites();
    }

    public LiveData<List<NotaEntity>> getAll(){
        return allNotas;
    }

    public LiveData<List<NotaEntity>> getAllNotasFavs(){
        return allNotasFavoritas;
    }

    public void insert(NotaEntity notaEntity){

        new insertAyncTask(notaDao).execute(notaEntity);

    }

    private static class insertAyncTask extends AsyncTask<NotaEntity,Void,Void>{

        private NotaDao notaDaoAsyncTask;

        insertAyncTask(NotaDao notaDao){

            notaDaoAsyncTask=notaDao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {

            notaDaoAsyncTask.insert(notaEntities[0]);

            return null;
        }
    }

}
