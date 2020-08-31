package conference.session;

import java.util.Random;

public sealed abstract class Breakout extends Session
permits Lab, Lecture {

    private String speaker;
    private int virtualRoom;

    public Breakout(String id, String title, String speaker) {
        super(id, title);
        this.speaker = speaker;
        this.virtualRoom = new Random().nextInt(3) + 1; // session will be randomly assigned to room 1, 2 or 3
    }

    public String getSpeaker() {
        return speaker;
    }

    public int getVirtualRoom() {
        return virtualRoom;
    }

}
