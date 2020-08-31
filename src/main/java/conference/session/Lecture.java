package conference.session;

final public class Lecture extends Breakout {

    String slidesUrl;

    public Lecture(String id, String title, String speaker, String slidesUrl) {
        super(id, title, speaker);
        this.slidesUrl = slidesUrl;
    }

    public String getslidesUrl() {
        return slidesUrl;
    }

}
