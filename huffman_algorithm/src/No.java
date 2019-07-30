public class No {
    public int freq;
    public int carac;
    public No pai;
    public No esq;
    public No dir;
    public int h;

    public No(int freq, int carac){
        this.freq = freq;
        this.carac = carac;
        pai = null;
        esq = null;
        dir = null;
        
    }

}
