package com.example.occfermetures.utilitaire;


import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    public static String sendCetOkHttpRequest(String url) throws Exception {
        //Toujours effectuer un contrôle d’url en l’affichant en console
        System.out.println("Url : " + url);
        OkHttpClient client = new OkHttpClient();
        //Création de la requête
        Request request = new Request.Builder().url(url).build();
        //Exécution de la requête
        Response response = client.newCall(request).execute();
        //Analyse du code retour
        //si le code retour est 500 il y a de forte chance qu il retourne un json
        if (response.code() == 500) {
            //on recupere le contenu de la reponse
            String json = response.body().string();
            //si elle contient une accolade
            if (json.contains("{")) {
                //on parse ce json en objet Error
                Error error = new Gson().fromJson(json, Error.class);
                //un objet Error contient comme attribut message
                //on controle qu il y en ait bien un dans le json parse
                if (error.getMessage() != null && error.getMessage().trim().length() > 0) {
                    //le message sera alors retourne en cas de code erreur 500
                    throw new Exception(error.getMessage());
                }
            }
        }
        if (response.code() < 200 || response.code() > 299) {
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        } else {
            //Résultat de la requête
            return response.body().string();
        }
    }


    public static String sendPostOkHttpRequest(String url, String paramJson) throws Exception {
        System.out.println("Url : " + url);
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        //Corps de la requête
        RequestBody body = RequestBody.create(paramJson, JSON);
        //Création de la requête
        Request request = new Request.Builder().url(url).post(body).build();
        //Exécution de la requête
        Response response = client.newCall(request).execute();
        //Analyse du code retour
        //si le code retour est 500 il y a de forte chance qu il retourne un json
        if (response.code() == 500) {
            //on recupere le contenu de la reponse
            String json = response.body().string();
            //si elle contient une accolade
            if (json.contains("{")) {
                //on parse ce json en objet Error
                Error error = new Gson().fromJson(json, Error.class);
                //un objet Error contient comme attribut message
                //on controle qu il y en ait bien un dans le json parse
                if (error.getMessage() != null && error.getMessage().trim().length() > 0) {
                    //le message sera alors retourne en cas de code erreur 500
                    throw new Exception(error.getMessage());
                }
            }
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        }
        if (response.code() < 200 || response.code() > 299) {
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        } else {
            //Résultat de la requête.
            return response.body().string();
        }
    }
}
