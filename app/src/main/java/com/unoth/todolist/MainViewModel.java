package com.unoth.todolist;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Callable;

public class MainViewModel extends AndroidViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NotesDao notesDao;
    private MutableLiveData<List<Note>> notes = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void refreshList() {
        Disposable disposable = getNotesRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notesFromDB) throws Throwable {
                        notes.setValue(notesFromDB);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void remove(Note note) {
        Disposable disposable = removeRx(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("MainViewModel", "Removed " + note.getId() + " OK");
                        refreshList();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private Single<List<Note>> getNotesRx() {
        return Single.fromCallable(new Callable<List<Note>>() {
            @Override
            public List<Note> call() throws Exception {
                return notesDao.getNotes();
            }
        });
    }

    private Completable removeRx(Note note) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                notesDao.remove(note.getId());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}

