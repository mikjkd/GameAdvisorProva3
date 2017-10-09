package com.miche.gameadvisorprova3.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.miche.gameadvisorprova3.MainActivity;
import com.miche.gameadvisorprova3.View.GiocoAdapter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Created by miche on 03/10/2017.
 */

public class DatabaseLink implements Serializable{
    private String DB_GIOCHI = "Giochi";
    private String DB_GENERE = "Genere";
    private String DB_AVVENTURA="Avventura";
    private String DB_AZIONE= "Azione";
    private String DB_CORSE = "Corse";
    private String DB_SPARATUTTO="Sparatutto";

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private ArrayList<DataGioco> giochi;
    private ArrayList<DataGenere> generi;
    private ValueEventListener listenerGiochi;
    private ValueEventListener listenerGenere;
    private Bitmap immagine;

    private Bitmap icona;


    public DatabaseLink(){
        giochi= new ArrayList<>();
        generi = new ArrayList<>();
    }


    public interface UpdateListener{
        void giochiAggiornati();
    }
    public interface BitmapListener{
        void BitmapPronta();
    }
    public interface UpdateGeneriListener{
        void generiAggiornati();
    }

    public void logInAnonimo(){
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        mauth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
            }
        });
    }
    public void osservaGiochi(final UpdateListener notifica){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child(DB_GIOCHI);

        listenerGiochi = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                giochi.clear();
                for(DataSnapshot e: dataSnapshot.getChildren()){
                    final DataGioco dg = e.getValue(DataGioco.class);
                    dg.setKey(e.getKey());
                    giochi.add(dg);
                }

               // notifica.giochiAggiornati();
                scaricaImmagine(new DatabaseLink.BitmapListener(){
                    @Override
                    public void BitmapPronta() {
                        notifica.giochiAggiornati();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addValueEventListener(listenerGiochi);
    }
    public void nonOsservaGiochi(){
        if(listenerGiochi!=null){
            FirebaseDatabase.getInstance().getReference("gameadvisorprova-ab95b").removeEventListener(listenerGiochi);
        }
    }

    public void osservaGenere(final UpdateGeneriListener notificaGenere){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child(DB_GENERE);

        listenerGenere = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                generi.clear();
                for (DataSnapshot e: dataSnapshot.getChildren()){
                    //Log.e("datasnap: ",e.getKey());
                    final DataGenere gen = e.getValue(DataGenere.class);
                    gen.setKeyGenere(e.getKey());
                    generi.add(gen);

                }
                scaricaIcona(new DatabaseLink.BitmapListener(){
                    @Override
                    public void BitmapPronta() {
                        notificaGenere.generiAggiornati();
                    }
                });
                notificaGenere.generiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        ref.addValueEventListener(listenerGenere);
    }

    public void scaricaImmagine(final BitmapListener immagineCaricata){
        final Bitmap[] bmp = new Bitmap[1];
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        //Log.e("Scarica immagine","dim: "+giochi.size());
        for (final DataGioco dg : giochi){
            final File localFile;
            //Log.e("Scarica","Immagine"+dg.getURLimg());
            try {
                localFile = File.createTempFile(dg.getURLimg(),".jpg");
                storageRef.child("Giochi/"+dg.getURLimg()+".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        dg.setUrlIconaLocale(localFile.getAbsolutePath());
                        Log.e("path: ",localFile.getAbsolutePath());

                       // Log.e("OnSuccess download","mannagg");
                        /*  BitmapFactory.Options op = new BitmapFactory.Options();
                        op.inSampleSize=1;
                        bmp[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath(),op);
                        dg.setImmagine(bmp[0]);*/

                        immagineCaricata.BitmapPronta();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Niente"," Fallito e che cazz");
                    }
                });
            } catch (IOException e) {
                Log.e("errore","errore try catch");
            }
        }
    }

    public void scaricaIcona(final BitmapListener immagineCaricata ){

        final Bitmap[] bmp = new Bitmap[1];
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        for (final DataGenere dge : generi){

            final File localFile;

            try{
                localFile = File.createTempFile(dge.getKeyGenere(),".jpg");
                storageRef.child("Iconegeneri/"+dge.getKeyGenere()+".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        dge.setPathlocale(localFile.getAbsolutePath());
                        Log.e("path: ",localFile.getAbsolutePath());

                        immagineCaricata.BitmapPronta();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Miss","Fallito");
                    }
                });
            } catch (IOException e) {
                Log.e("Errore","Try Catch");
            }

        }

    }

    public List<DataGioco> elencoGiochi(){
        return giochi;
    }
    public List<DataGenere> elencoGenere() { return generi; }

}
