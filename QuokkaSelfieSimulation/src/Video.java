public class Video extends Selfie{
    public String video;
    public Video(String type) {
        super(type);
    }

    public Video(String type, String photo) {
        super(type);
        this.video = photo;
    }
}
