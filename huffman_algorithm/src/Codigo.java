
import java.util.ArrayList;
import java.util.BitSet;

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
    //BitSet bits;
    int[] bits = new int[256];
    int size;
    int sym;
    
    public Codigo(ArrayList<Integer> bits, int ch){
        size = bits.size();
        for(int i = 0; i < bits.size(); i++){
            this.bits[i] = bits.get(i);
        }
        sym = ch;
    }
}
