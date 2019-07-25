public class FilaMin extends Heap{

    public FilaMin(int tam) {
        super(tam);
    }

    public int heapMinimum(){
        return vector_no[0].freq;
    }

    public No extractMin(){
        if(tam_heap <= 0){
            System.out.println("Error EXTRACT-MIN FAILED");
        }

        No menor_No = vector_no[0];
        vector_no[0] = vector_no[tam_heap - 1];
        tam_heap -= 1;
        comp_heap = tam_heap;
        minHeapfy(0);

        return menor_No;
    }

    public void decreaseKey(int i, No no){        
        //if(no.freq > vector_no[i].freq){
            //System.out.println("Chave inserida é maior que a atual");
        //}else{
            vector_no[i] = no;

            while((i > 0) && (vector_no[pai(i)].freq > vector_no[i].freq)){
                swap(i, pai(i));
                i = pai(i);
            }
        //}
    }

    public void insert(No no){
        tam_heap += 1;
        comp_heap = tam_heap;
        //vector_no[tam_heap -1].freq = Integer.MAX_VALUE;
        decreaseKey((tam_heap -1), no);

    }

}
