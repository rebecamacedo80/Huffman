// Huffman Implementation

import java.io.*;
import java.util.ArrayList;

public class Main{
    // Array de bits para preencher o dicionário
    static ArrayList<Integer> bits = new ArrayList<>();
        
    // Array de objetos tipo código que será nosso "Dicionário
    static ArrayList<Codigo> dictionary = new ArrayList<>();

    // Lê o arquivo e retorna um array de inteiros contendo
    public static int[] fileToArray(String file_path) throws IOException, FileNotFoundException{
                        
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
    // Preenche um arraylist a partir de um array de frequências apenas com os
    // caracteres que tem frequência maior que zero.
    public static void fillArraylist(int[] freq, ArrayList<No> ar){
        
        for(int i = 0; i < freq.length; i++){
            if(freq[i] != 0){                
                ar.add( new No(freq[i], i));
                            
            }            
        }
    }
    
    public static No createTree(ArrayList ar){    
        // Cria a fila mínima e a preenche apenas com o arraylist de Nós que contém as suas frequências.
        FilaMin fila = new FilaMin(ar.size());        
        fila.fill(ar);
        fila.buildMinHeap();
       
        while(fila.tam_heap > 1){
            
            No no1 = fila.extractMin();
            No no2 = fila.extractMin();
                        
            //System.out.println("\n Freq de No1: " + no1.freq);
            //System.out.println("\n Freq de No2: " + no2.freq);
            
            No parent = new No(no1.freq + no2.freq, '+');
            parent.esq = no1;
            parent.dir = no2;            
            no1.pai = parent;
            no2.pai = parent;            
            //System.out.println("\nFreq de parent: " + parent.freq);            
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
            
            bits.add(0);
            i++;
            codifica(no.esq);
            bits.remove(i - 1);
            i--;
        }
        if(no.dir != null){
            
            bits.add(1);
            i++;
            codifica(no.dir);
            bits.remove(i -1);
            i--;
        }
        if(no.dir == null && no.esq == null){
            dictionary.add(new Codigo(bits, no.carac));
            
            //System.out.println("\nchar do no: " + no.carac + "\tcodigo: " + bits + "\tFreq: " + no.freq);
          
        }
        
    }
    
    public static void comprime(String file_orig, String file_dest, int[] vector_freq) throws FileNotFoundException, IOException{
        
        // Leitura do Arquivo
        File file = new File(file_orig);
        FileInputStream arq1 =  new FileInputStream(file);
        DataInputStream data_read =  new DataInputStream(arq1);        
                       
        FileOutputStream fos = new FileOutputStream(file_dest);
        DataOutputStream dos = new DataOutputStream(fos);
        
        ArrayList<Integer> buffer_arq =  new ArrayList<>(); 
            
        //escreve vetor de frequencias no arquivo comprimido.        
        for(int i = 0; i < vector_freq.length; i++){            
            dos.writeInt(vector_freq[i]);                
            
        }
        
        int c = 0;
        for(int i = 0; i <dictionary.size(); i++){
            for(int j = 0; j < dictionary.get(i).size; j++){
                buffer_arq.add(dictionary.get(i).bits[j]);
                c++;
            }
        }
           
        int count = 0;
        char deslocador = 0, buf_mask = 0, bit=0;
              
        
        for(int i = 0; i < file.length(); i++){
            int aux = data_read.read();
            //System.out.println("aux = " + aux);
            
            for(int j = 0; j < dictionary.size(); j++){
                //System.out.println("sym dictionary: " + dictionary.get(j).sym);
                
                if(aux == dictionary.get(j).sym){      
                    //System.out.println("entrou no if!!!");
                    
                    for(int k = 0; k <  dictionary.get(j).size; k++){                        
                        byte a = (byte) dictionary.get(j).bits[k];
                        
                        if(a == 1)
                            buf_mask = (char) (1 << deslocador);
                        
                        bit |= buf_mask;
                        buf_mask = 0;
                        count++;                        
                        deslocador++;
                        
                        if(count == 8){                           
                            dos.writeByte(bit);
                            bit = 0;
                            count = 0;
                            deslocador = 0;
                        }                        
                    }                    
                }
            }            
        }        
                
        arq1.close();
        data_read.close();
        fos.close();
        dos.close();
        
    }
    
    public static void descomprime(String file_dest, String file_descomp) throws FileNotFoundException, IOException{
        File file = new File(file_dest);
        FileInputStream arq1 =  new FileInputStream(file);
        DataInputStream data =  new DataInputStream(arq1);
        
        FileOutputStream fos = new FileOutputStream(file_descomp);
        DataOutputStream dos = new DataOutputStream(fos);        
        
        int[] vector_freq = new int[256];
        
        ArrayList<No> freq = new ArrayList<>();
        int integ;
        
        for(int i = 0; i < vector_freq.length; i++){
            integ = data.readInt();
            
            vector_freq[i] = integ;
        }
        
        fillArraylist(vector_freq, freq);
        
        No raiz = createTree(freq);        
        No no = raiz;
        
        byte caracter;
        char mask = 1, aux;
                 
        while(true){
            int byte_arq = data.read();
            
            if(byte_arq == -1)
                break;
            else
                caracter = (byte)byte_arq;  
                                                
            for(int i = 0; i < 8; i++){
                
                aux = (char)(caracter >> i);
                
                if((mask & aux) == 1){
                    no = no.dir;
                    //System.out.println("bit = 1: " + Integer.toString(no.carac));
                }else{
                        
                    no = no.esq;
                    //System.out.println("bit = 0: "+Integer.toString(no.carac));
                }
                
                if(no.dir == null && no.esq == null){
                    //System.out.println("folha: "+Integer.toString(no.carac));
                    dos.write(no.carac);
                    no = raiz;
                }
            }
            
        }
        
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        // Caminho dos arquivos
        String file_orig = "/home/rebeca/Huffman/huffman_algorithm/generated.fib25";
        String file_dest = "/home/rebeca/Huffman/huffman_algorithm/output.out";
        String file_descomp = "/home/rebeca/Huffman/huffman_algorithm/decoded.out";
        
        // Cria um vetor de tamanho 256 com as frequências
        int vector_freq[] =  fileToArray(file_orig);
        
        /*
        for(int i = 0; i < vector_freq.length; i++){
           System.out.print(vector_freq[i] + " ");
        }*/

        // Cria um arraylist que será preenchido apenas com os que tem frequência != 0
       ArrayList<No> ar = new ArrayList<>();

        // Preenche o arraylist com a Lista de Frequencia (apenas os !=0)
        fillArraylist(vector_freq, ar);
        
        // Codifica a arvore e salva no Array dicionário
        codifica(createTree(ar));
                
        // Comprime o arquivo e salva em um novo arquivo
        comprime(file_orig, file_dest, vector_freq);
        
        descomprime(file_dest, file_descomp);
        
    }
}
