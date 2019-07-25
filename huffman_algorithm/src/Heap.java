import java.util.ArrayList;

public class Heap {
    public No vector_no[];
    public int tam_heap;
    public int comp_heap;

    public Heap(int tam){
        tam_heap = tam;
        comp_heap = tam;
        vector_no = new No[comp_heap];
        
    }

    public int pai(int i){
        return (i - 1)/2;
    }

    public int dir(int i){
        return (2 * i) + 2;
    }

    public int esq(int i){
        return (2 * i) + 1;
    }

    public void fill(ArrayList<No> ar){
        for(int i = 0; i < vector_no.length; i++){
            vector_no[i] = ar.get(i);
        }
    }

    public void show(){
        for(int i = 0; i < vector_no.length; i++){
            System.out.print(vector_no[i].freq + " ");
        }
    }

    public void swap(int a, int b){
        No aux = vector_no[a];
        vector_no[a] = vector_no[b];
        vector_no[b] = aux;
    }

    public void minHeapfy(int i){
        int l = this.esq(i);
        int r = this.dir(i);
        int menor;

        if((l < tam_heap) && (vector_no[l].freq < vector_no[i].freq)){
            menor = l;
        }else{
            menor = i;
        }
        if((r < tam_heap) && (vector_no[r].freq < vector_no[menor].freq)){
            menor = r;
        }

        if(menor != i){
            swap(i, menor);
            minHeapfy(menor);
        }
    }

    public void buildMinHeap(){
        comp_heap = tam_heap;

        for(int i = (comp_heap/2); i >= 0; i--){
            minHeapfy(i);
        }
    }

}
