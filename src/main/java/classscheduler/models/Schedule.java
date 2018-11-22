package classscheduler.models;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class Schedule {

    private Map<DayHour, List<Session>> sessions;

    public Schedule() {
        sessions = new HashMap<>();
    }

    public synchronized void addSession(DayHour dayHour, Session session) {
        List<Session> sessionList = sessions.get(dayHour);

        // if list does not exist create it
        if (sessionList == null) {
            sessionList = new LinkedList<>();
            sessionList.add(session);
            sessions.put(dayHour, sessionList);
        } else {
            // add if item is not already in list
            if(!sessionList.contains(session)) sessionList.add(session);
        }
    }

    public boolean isClassroomExist(DayHour dayHour, String classroomId) {
        List<Session> sessionList = sessions.get(dayHour);
        if (sessionList != null) {
            for (Session session: sessionList) {
                if (session.getClassroom().getId().equals(classroomId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteSession(DayHour dayHour, String classroomId) {
        List<Session> sessionList = sessions.get(dayHour);
        if (sessionList != null) {
            sessionList.removeIf(session -> session.getClassroom()
                    .getId().equals(classroomId));
        }
    }

    public void deleteSessions(DayHour dayHour) {
        sessions.remove(dayHour);
    }
}
