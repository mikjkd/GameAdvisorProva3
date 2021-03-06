package com.miche.gameadvisorprova3.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miche on 11/10/2017.
 */

public class DataGiocoDettaglio implements Serializable {

    private String Genere;
    private String Link;
    private String Titolo;
    private String Descrizione;
    private String URLimg;
    private String Key;
    private String UrlIconaLocale;
    private String UrlImmagineLocale;
    private String Requisiti;
    private String Sviluppatore;
    private Float Votazione;
    private Integer NumeroVotanti;
    private List<String> Commenti;


    public DataGiocoDettaglio(){
        this.Commenti= new ArrayList<>();
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public DataGiocoDettaglio(String genere, String link, String titolo, String descrizione, String URLimg) {
        Genere = genere;
        Link = link;
        Titolo = titolo;
        Descrizione = descrizione;
        this.URLimg = URLimg;
    }

    public String getGenere() {
        return Genere;
    }

    public void setGenere(String genere) {
        Genere = genere;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public String getURLimg() {
        return URLimg;
    }

    public void setURLimg(String URLimg) {
        this.URLimg = URLimg;
    }

    public String getUrlIconaLocale() {
        return UrlIconaLocale;
    }

    public void setUrlIconaLocale(String urlIconaLocale) {
        UrlIconaLocale = urlIconaLocale;
    }

    public String getUrlImmagineLocale() {   return UrlImmagineLocale; }

    public void setUrlImmagineLocale(String urlImmagineLocale) { UrlImmagineLocale = urlImmagineLocale; }

    public String getSviluppatore() {
        return Sviluppatore;
    }

    public void setSviluppatore(String sviluppatore) {
        Sviluppatore = sviluppatore;
    }

    public String getRequisiti() {

        return Requisiti;
    }

    public void setRequisiti(String requisiti) {
        Requisiti = requisiti;
    }

    public Float getVotazione() {
        return Votazione;
    }

    public void setVotazione(Float votazione) {
        Votazione = votazione;
    }

    public Integer getNumeroVotanti() {
        return NumeroVotanti;
    }

    public void setNumeroVotanti(Integer numeroVotanti) {
        NumeroVotanti = numeroVotanti;
    }

    public List<String> getCommenti() {
        return Commenti;
    }

    public void setCommenti(List<String> commenti) {
        Commenti = commenti;
    }
    public void addCommenti(String c){
        this.Commenti.add(c);
    }
}
