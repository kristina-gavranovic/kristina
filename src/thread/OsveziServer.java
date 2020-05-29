/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import com.sun.webkit.ThemeClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import modeli.ModelTabelePonuda;

/**
 *
 * @author Kristina
 */
public class OsveziServer extends Thread{
    ModelTabelePonuda mtp;

    public OsveziServer(ModelTabelePonuda mtp) {
        this.mtp = mtp;
    }
    

    @Override
    public void run() {
            while (true) {            
                try {
                    
                    
                    mtp.fireTableDataChanged();
                    System.out.println("Osvezene ponude");
                    sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(OsveziServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
        }

    }
    
}
