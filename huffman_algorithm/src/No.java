public class No {
    public int freq;
    public char carac;
    public No pai;
    public No esq;
    public No dir;

    public No(int freq, int carac){
        this.freq = freq;
        this.carac = (char)(carac);
        pai = null;
        esq = null;
        dir = null;
    }

}
