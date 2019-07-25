
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rebeca
 */
public class Codigo {
    int[] bits;
    int sym;
    int size;
    
    public Codigo(ArrayList<Integer> b, int ch){
        size = b.size();
        bits = new int[size];
        for(int i = 0; i < b.size(); i++){
            bits[i] = b.get(i);
        }
        sym = ch;        
    }
}
