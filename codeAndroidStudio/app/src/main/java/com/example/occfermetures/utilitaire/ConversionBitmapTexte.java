package com.example.occfermetures.utilitaire;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ConversionBitmapTexte {

    //transformation d'un bitmap en texte
    public static String bitmapToString(Bitmap bitmap){
        //classe ByteArrayOutputStream permet de recuperer la version binaire de l img
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //compression de l img (donc reduite au max)
        //1er parametre : format, 2eme param : 50 est le taux de compression, 3eme param : où integrer l img
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        //creation d un tableau de bytes qui stocke notre stream que l on change en tableau de byte
        byte[] byteArray = stream.toByteArray();
        //encodage que l on retourne
        return Base64.encodeToString(byteArray, 0);
    }

    //transformation d'un texte en bitmap
    public static Bitmap stringToBitmap(String picture){
        //declaration d un bitmap qui sera retourne; si ca se passe mal il retournera null
        Bitmap bitmap = null;
        try {
            //on decode le picture que l on recoit et on le transfert dans un tableau de bytes
            byte[]  decodeString = Base64.decode(picture, Base64.DEFAULT);
            //transformation du tableau de byte en bitmap
            bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error", "texte non convertible");
        }
        return bitmap;
    }
}
