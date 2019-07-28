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
            fila.insert(parent);           
        }
        return fila.extractMin();
    }

    
    public static void codifica(No no){         
        if(no == null){
            return;
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
            dictionary.add(new Codigo(bits, no.carac));  
            //System.out.println("Informações de code:\nCaractere " + code.sym + "\tCodigo: " + code.bits + "\tSize: " + code.size);
            //System.out.println("\nfreq do no: " + no.freq + "\tcodigo: " + bits);
            bits.remove(bits.size() -1);
            
        }
        
    }
    
    public static void comprime(String file_orig, String file_dest) throws FileNotFoundException, IOException{
        
        // Leitura do Arquivo
        File file = new File(file_orig);
        FileInputStream arq1 =  new FileInputStream(file);
        DataInputStream data_read =  new DataInputStream(arq1);
        ArrayList<Integer> buffer_arq =  new ArrayList<>();
        
        
        //FileWriter arq = new FileWriter(file_dest);
        //PrintWriter gravar = new PrintWriter(arq);
         
                
        for(int i = 0; i < file.length(); i++){
            int aux = data_read.read();
            for(int j = 0; j < dictionary.size(); j++){
                if(aux == dictionary.get(j).sym){
                    for(int k = 0; k < dictionary.get(j).bits.length; k++){
                        //buffer_arq.add(dictionary.get(j).bits[k]);
                        System.out.println(dictionary.get(j).bits[k]);
                        //System.out.println("gravar: \t"+dictionary.get(j).bits[k]);
                        //gravar.print(dictionary.get(j).bits[k] + " ");
                    }
                }
            }
        }
        
        //byte[] ar_byte = buffer_arq.toString().getBytes();
        
        //String teste = buffer_arq.toString();
        
        //System.out.println(teste);
        
        //FileOutputStream fileOutputStream = new FileOutputStream(new File("/home/lucas/teste.aee"));
	//fileOutputStream.write(ar_byte);
	//fileOutputStream.close();
        
        arq1.close();
        data_read.close();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        // Caminho dos arquivos
        String file_orig = "/home/lucas/GitHub/Huffman/huffman_algorithm/generated.fib25";
        String file_dest = "/home/lucas/GitHub/Huffman/huffman_algorithm/output.out";

        // Cria um vetor de tamanho 256 com as frequências
        //int vector_freq[] =  fileToArray(file_orig);
        
        //for(int i = 0; i < vector_freq.length; i++){
        //    System.out.print(vector_freq[i] + " ");
        //}

        // Cria um arraylist que será preenchido apenas com os que tem frequência != 0
       //ArrayList<No> ar = new ArrayList<>();

        // Preenche o arraylist com a Lista de Frequencia (apenas os !=0)
        //fillArraylist(vector_freq, ar);
        
        // Cria a fila mínima e a preenche apenas com o arraylist de Nós que contém as suas frequências.
        //FilaMin fila = new FilaMin(ar.size());        
        //fila.fill(ar);
        //fila.buildMinHeap();
        
        // Cria a árvore a partir da fila mínima
        //No tree_root = createTree(fila);
        
        // Codifica a arvore e salva no Array dicionário
        //codifica(tree_root);
        
        // Comprime o arquivo e salva em um novo arquivo
        //comprime(file_orig, file_dest);
 
       
    }
}
