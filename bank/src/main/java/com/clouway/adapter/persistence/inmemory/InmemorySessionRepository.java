package com.clouway.adapter.persistence.inmemory;

import com.clouway.core.Session;
import com.clouway.core.SessionsRepository;
import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class InmemorySessionRepository implements SessionsRepository {
    private static Map<String, Session> sessions = new HashMap<String, Session>();

    public void register(Session session) {
        sessions.put(session.ID, session);
    }

    public Optional<Session> getSession(String sessionID) {
        Session session = sessions.get(sessionID);
        return Optional.of(session);
    }

    public void remove(String sessionID) {
        sessions.remove(sessionID);
    }
}
