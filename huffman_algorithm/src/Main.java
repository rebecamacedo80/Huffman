// Huffman Implementation

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{


    // Lê o arquivo e retorna um array de inteiros contendo
    public static int[] read_file(String file_path) throws IOException, FileNotFoundException{
            File file = new File(file_path);
            FileInputStream arq =  new FileInputStream(file);
            DataInputStream data =  new DataInputStream(arq);

            int[] freq_vector = new int[256];

            for(int i = 0; i < file.length(); i++){
                freq_vector[data.read()]++;
            }

            arq.close();
            data.close();

        return freq_vector;
    }

    public static void createTree(FilaMin fila){
        while(fila.tam_heap > 1){
            
            
            No no1 = fila.extractMin();
            No no2 = fila.extractMin();
                        
            System.out.println("\n Freq de No1: " + no1.freq);
            System.out.println("\n Freq de No2: " + no2.freq);
            
            No parent = new No(no1.freq + no2.freq, '+');
            parent.esq = no1;
            parent.dir = no2;
            no1.pai = parent;
            no2.pai = parent;
            System.out.println("\nFreq de parent: " + parent.freq);
            fila.insert(parent);           
        }
    }
    static ArrayList<Integer> bits = new ArrayList<>();
    static ArrayList<Codigo> dicionario = new ArrayList<>();
    
    public static void codifica(No no){         
        if(no == null){
            return ;
        }        
        if(no.esq != null){
            
            bits.add(0);
            codifica(no.esq);
        }
        if(no.dir != null){
            
            bits.add(1);
            codifica(no.dir);
        }
        if(no.dir == null && no.esq == null){
            dicionario.add(new Codigo(bits, no.carac));
           
            //System.out.println("Informações de code:\nCaractere " + code.sym + "\tCodigo: " + code.bits + "\tSize: " + code.size);
            //System.out.println("\nfreq do no: " + no.freq + "\tcodigo: " + bits);
            bits.remove(bits.size() -1);
            
        }
        
    }
    
    public static void comprime() throws FileNotFoundException, IOException{
        File file = new File("/home/rebeca/Huffman/huffman_algorithm/generated.fib25");
        FileInputStream arq_leitura =  new FileInputStream(file);
        DataInputStream data =  new DataInputStream(arq_leitura);
        
        FileWriter arq = new FileWriter("/home/rebeca/Huffman/huffman_algorithm/aeo.txt");
        PrintWriter gravar = new PrintWriter(arq);
         
                
        for(int i = 0; i < file.length(); i++){
            int aux = data.read();
            for(int j = 0; j < dicionario.size(); j++){
                if(aux == dicionario.get(j).sym){
                    for(int k = 0; k < dicionario.get(j).bits.length; k++){
                        System.out.println("gravar: \t"+dicionario.get(j).bits[k]);
                        gravar.print(dicionario.get(j).bits[k]);
                    }
                }
            }
        }
        /*
        
        for(int i = 0; i < dicionario.size(); i++){
            
            if(data.read() == dicionario.get(i).sym){
                for(int j = 0; j < dicionario.get(i).bits.length; j++){
                gravar.print(dicionario.get(i).bits[j]);
                }
            }
        }*/
        arq.close();            
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException{

        // Cria um vetor de tamanho 256 com as frequências

        int vector_freq[] =  Main.read_file("/home/rebeca/Huffman/huffman_algorithm/generated.fib25");

        // Cria um arraylist que será preenchido apenas com os que tem frequência != 0
        ArrayList<No> ar = new ArrayList<>();

        for(int i = 0; i < vector_freq.length; i++){
            if(vector_freq[i] != 0){
                
                ar.add( new No(vector_freq[i], i));
            }
        }
        // Cria a fila mínima e a preenche apenas com o arraylist de Nós que contém as suas frequências.
        FilaMin fila = new FilaMin(ar.size());        
        fila.fill(ar);

        fila.buildMinHeap();
        fila.show(); // Printa a fila mínima
        createTree(fila);
        codifica(fila.vector_no[fila.tam_heap - 1]);
        
        comprime();
        /*        
        No[] filaminima = new No[fila.comp_heap];
        //Printa extraindo todos os elementos da fila 
        System.out.println();
        for(int i = 0; i < fila.comp_heap; i++){
            filaminima[i] = fila.extractMin();
            System.out.print(filaminima[i].freq + " ");
        }
        */
        
       
    }
}
