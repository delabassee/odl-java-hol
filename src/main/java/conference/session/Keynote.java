package conference.session;

final public class Keynote extends Session {

    public String getKeynoteSpeaker() {
        return keynoteSpeaker;
    }

    String keynoteSpeaker;

    public Keynote(String id, String keynoteSpeaker, String title) {
        super(id, title);
        this.keynoteSpeaker = keynoteSpeaker;
    }
}
