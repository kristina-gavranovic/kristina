/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domain.PonudaKompanije;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Kristina
 */
public class ModelTabelePonuda extends  AbstractTableModel{
    
    ArrayList<PonudaKompanije> listaPonuda;

    public ModelTabelePonuda() {
        listaPonuda=new ArrayList<>();
    }

    public ModelTabelePonuda(ArrayList<PonudaKompanije> listaPonuda) {
        this.listaPonuda=listaPonuda;
    }
    

    @Override
    public int getRowCount() {
            return    listaPonuda.size();

    }

    public ArrayList<PonudaKompanije> getListaPonuda() {
        return listaPonuda;
    }

    public void setListaPonuda(ArrayList<PonudaKompanije> listaPonuda) {
        this.listaPonuda = listaPonuda;
    }
    
    @Override
    public int getColumnCount() {
       return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PonudaKompanije pk=listaPonuda.get(rowIndex);
        switch (columnIndex) {
            case 0: return pk.getRbPartije();
            
            case 1: return pk.getNazivKompanije();
            case 2: return pk.getNazivPredmeta();
            case 3: return pk.getIznos();
            case 4: return pk.getRokIsporuke();
                
            default:
                        return "Greska";
        }
        
    }

    @Override
    public String getColumnName(int column) {
         switch (column) {
            case 0: return "RB partije";         
            case 1: return "kompanija";
            case 2: return "predmet";
            case 3: return "iznos";
            case 4: return "rok isporuke";
                
            default:
                        return "Greska";
        }
    }
    
}
