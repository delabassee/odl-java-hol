package conference.session;

import conference.Track;

import java.util.UUID;

sealed public abstract class Session
permits Keynote, Breakout {

    private String id;
    private String title;

    public Session(String id, String title) {
        this.title = title;
        //uid = UUID.randomUUID().toString();
        this.id= id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
