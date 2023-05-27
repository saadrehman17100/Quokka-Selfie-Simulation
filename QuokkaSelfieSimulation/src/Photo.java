public class Photo extends Selfie{
    public String photo;
    public Photo(String type) {
        super(type);
    }

    public Photo(String type, String photo) {
        super(type);
        this.photo = photo;
    }
}
