// Huffman Implementation

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class Main{
    // Array de bits para preencher o dicionário
    static ArrayList<Integer> bits = new ArrayList<>();
    
    //bit mapping
   // static BitSet bitset = new BitSet();
    
    // Array de objetos tipo código que será nosso "Dicionário
    static ArrayList<Codigo> dictionary = new ArrayList<>();

    // Lê o arquivo e retorna um array de inteiros contendo
    public static int[] fileToArray(String file_path) throws IOException, FileNotFoundException{
        
            File file = new File(file_path);
            FileInputStream arq =  new FileInputStream(file);
            DataInputStream data =  new DataInputStream(arq);

            int[] freq_vector = new int[256];
            System.out.println("file length: " + file.length());
            
            for(int i = 0; i < file.length(); i++){
                freq_vector[data.read()]++;
            }
            
            arq.close();
            data.close();

        return freq_vector;
    }
    // Preenche um arraylist a partir de um array de frequências apenas com os
    // caracteres que tem frequência maior que zero.
    public static void fillArraylist(int[] freq, ArrayList<No> ar){
        
        for(int i = 0; i < freq.length; i++){
            if(freq[i] != 0){
                
                ar.add( new No(freq[i], i));
            }
        }
    }

    public static No createTree(FilaMin fila){
       
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
            System.out.println("altura n1 e n2: " + no1.h + " " + no2.h);
            fila.insert(parent);           
        }
        return fila.extractMin();
    }

    static int i = 0; 
    public static void codifica(No no){ 
               
        if(no == null){
            return;
        }        
        if(no.esq != null){
            //bitset.clear(i);
            
            bits.add(0);
            i++;
            codifica(no.esq);
            bits.remove(i - 1);
            i--;
        }
        if(no.dir != null){
            //bitset.set(i);
            
            bits.add(1);
            i++;
            codifica(no.dir);
            bits.remove(i -1);
            i--;
        }
        if(no.dir == null && no.esq == null){
            dictionary.add(new Codigo(bits, no.carac));  
            
            //System.out.println("Informações de code:\nCaractere " + code.sym + "\tCodigo: " + code.bits + "\tSize: " + code.size);
            System.out.println("\nchar do no: " + no.carac + "\tcodigo: " + bits + "\tFreq: " + no.freq);
            //System.out.println("bitset size: " + bitset.size());
            //System.out.println("bitset length: " + bitset.length());
            //bitset.clear(bitset.length()-1);
            System.out.println("bits size: " + bits.size());
            System.out.println("i value: " + i);
                   
            //bits.remove(i -1);
            
        }
        
    }
    
    public static void comprime(String file_orig, String file_dest) throws FileNotFoundException, IOException{
        
        // Leitura do Arquivo
        File file = new File(file_orig);
        FileInputStream arq1 =  new FileInputStream(file);
        DataInputStream data_read =  new DataInputStream(arq1);        
                       
        FileOutputStream fos = new FileOutputStream(file_dest);
        DataOutputStream dos = new DataOutputStream(fos);
        
        
        ArrayList<Integer> buffer_arq =  new ArrayList<>();        
        
        int c = 0;
        for(int i = 0; i <dictionary.size(); i++){
            for(int j = 0; j < dictionary.get(i).size; j++){
                buffer_arq.add(dictionary.get(i).bits[j]);
                c++;
            }
        }
        BitSet bitset_2 = new BitSet(50);        
        int count = 0;
        
        for(int i = 0; i < file.length(); i++){
            int aux = data_read.read();
            
            for(int j = 0; j < dictionary.size(); j++){                
                if(aux == dictionary.get(j).sym){      
                    
                    for(int k = 0; k < dictionary.get(j).size; k++){                        
                        int a = dictionary.get(j).bits[k];
                        if(a == 1){
                            bitset_2.set(count);
                            count++;
                        } else{                            
                            count++;
                        }
                        if(count == 8){                            
                            dos.write(bitset_2.toByteArray());
                            count = 0;
                        }
                        
                    }
                    
                }
            }
            
        }
                
        arq1.close();
        data_read.close();
    }
    
    public static void descomprime(){
        
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        // Caminho dos arquivos
        String file_orig = "/home/rebeca/Huffman/huffman_algorithm/generated.fib25";
        String file_dest = "/home/rebeca/Huffman/huffman_algorithm/output.out";

        // Cria um vetor de tamanho 256 com as frequências
        int vector_freq[] =  fileToArray(file_orig);
        
        System.out.println(vector_freq.length);
        for(int i = 0; i < vector_freq.length; i++){
           System.out.print(vector_freq[i] + " ");
        }

        // Cria um arraylist que será preenchido apenas com os que tem frequência != 0
       ArrayList<No> ar = new ArrayList<>();

        // Preenche o arraylist com a Lista de Frequencia (apenas os !=0)
        fillArraylist(vector_freq, ar);
        
        // Cria a fila mínima e a preenche apenas com o arraylist de Nós que contém as suas frequências.
        FilaMin fila = new FilaMin(ar.size());        
        fila.fill(ar);
        fila.buildMinHeap();
        
        // Cria a árvore a partir da fila mínima
        No tree_root = createTree(fila);
        
        // Codifica a arvore e salva no Array dicionário
        codifica(tree_root);
        
        // Comprime o arquivo e salva em um novo arquivo
        comprime(file_orig, file_dest); 
        
        
       
    }
}
