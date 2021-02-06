package conference;

import conference.session.Keynote;
import conference.session.Lab;
import conference.session.Lecture;
import conference.session.Session;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import java.util.List;
import java.util.logging.Logger;


public class AgendaService implements Service {

   private final AgendaRepository sessions;
   private static final Logger LOGGER = Logger.getLogger(AgendaService.class.getName());

   AgendaService() {
      sessions = new AgendaRepository();
   }

   @Override
   public void update(Routing.Rules rules) {
      rules.get("/", this::getAll);
   }

   private void getAll(final ServerRequest request, final ServerResponse response) {
     LOGGER.fine("getSessionsAll");

     List<Session> allSessions = this.sessions.getAll();
     response.send(allSessions);
   }
}