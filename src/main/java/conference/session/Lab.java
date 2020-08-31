package conference.session;

final public class Lab extends Breakout {

    String labUrl;

    public Lab(String id, String title, String speaker, String labUrl) {
        super(id, title, speaker);
        this.labUrl = labUrl;
    }

    public String getLabUrl() {
        return labUrl;
    }

}
