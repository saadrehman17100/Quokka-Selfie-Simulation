public class Sketch extends Selfie{
    public String selfie;

    public Sketch(String type, String selfie) {
        super(type);
        this.selfie = selfie;
    }

    public Sketch(String type) {
        super(type);
    }
}
