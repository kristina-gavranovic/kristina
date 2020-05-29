/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.connection.ConnectionFactory;
import domain.JavnaNabavka;
import domain.Kompanija;
import domain.Partija;
import domain.PonudaKompanije;
import domain.Predmet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Kristina
 */
public class Controller {

    private static Controller instance;
    private int sifraNabavke;
    ArrayList<PonudaKompanije> listaPonuda;

    private Controller() {
        listaPonuda = new ArrayList<>();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public ArrayList<PonudaKompanije> getListaPonuda() {
        return listaPonuda;
    }

    public void setListaPonuda(ArrayList<PonudaKompanije> listaPonuda) {
        this.listaPonuda = listaPonuda;
    }

    public Kompanija login(Kompanija k) {

        ArrayList<Kompanija> listakompanija = new ArrayList();

        try {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            String upit = "Select * from kompanija";
            Statement s = connection.createStatement();

            ResultSet rs = s.executeQuery(upit);
            while (rs.next()) {
                Kompanija kompanija = new Kompanija();
                kompanija.setId(rs.getInt("KompanijaID"));
                kompanija.setNaziv(rs.getString("naziv"));
                kompanija.setKorisnickoIme(rs.getString("KorisnickoIme"));
                kompanija.setLozinka(rs.getString("Lozinka"));
                kompanija.setPib(rs.getString("pib"));
                kompanija.setAdresa(rs.getString("adresa"));
                listakompanija.add(kompanija);
            }
            
            for (Kompanija kompanija : listakompanija) {
                if(kompanija.equals(k)) return kompanija;
            }
            
            

        } catch (Exception ex) {

            System.err.println(ex.getMessage());

        }

       return null;

    }

    public Object vratiNabavku() {
        
        try{
         Connection connection = ConnectionFactory.getInstance().getConnection();
            String upit = "Select * from javnanabavka where nabavkaid="+sifraNabavke;
            Statement s = connection.createStatement();

            ResultSet rs = s.executeQuery(upit);
            JavnaNabavka jn=null;
            while(rs.next()){
           jn=new JavnaNabavka(rs.getInt("NabavkaID"),rs.getString("Naziv"), rs.getString("Opis"),
                    new Date(rs.getDate("datumod").getTime()),  new Date(rs.getDate("datumdo").getTime()), rs.getBoolean("aktivna"));
            
            }
            upit = "SELECT * FROM partija pa JOIN predmet pr ON pa.predmetid=pr.predmetid WHERE nabavkaid="+sifraNabavke;
            s = connection.createStatement();

            rs = s.executeQuery(upit);
            
            while (rs.next()) {
                Partija p=new Partija();
                p.setRb(rs.getInt("RBpartije"));
                p.setKlicina(rs.getInt("Kolicina"));
                p.setPredmet(new Predmet(rs.getInt("predmetid"), rs.getString("pr.naziv"), rs.getString("pr.opis")));
                jn.getListaPartija().add(p);
            }
           
            
            return jn;
            
        }catch(Exception ex){
                    System.err.println(ex.getMessage());

        }
        return null;

    }

    public void posaljiSifruNabavke(int sifraNabavke) {
        this.sifraNabavke=sifraNabavke;

    }

    public Object dodajPonudu(PonudaKompanije ponuda) {
        listaPonuda.add(ponuda);

            return true;    
    }

}
