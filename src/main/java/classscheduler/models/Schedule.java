package classscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
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
        if (sessionList == null) {
            sessionList = new LinkedList<>();
            sessionList.add(session);
            sessions.put(dayHour, sessionList);
        } else {
            if (!sessionList.contains(session)) {
                sessionList.add(session);
            }
        }
    }

    public List<Classroom> getAllClassroomsInDayHour(DayHour dayHour) {
        List<Session> sessionList = sessions.get(dayHour);
        if (sessionList != null) {
            return sessionList.stream().map(Session::getClassroom).collect(Collectors.toList());
        }
        return new ArrayList<>();
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

    public boolean isLecturerExist(DayHour dayHour, String lecturerId) {
        List<Session> sessionList = sessions.get(dayHour);
        if (sessionList != null) {
            for (Session session: sessionList) {
                if (session.getClazz().getLecturerId().equals(lecturerId)) {
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
            sessionList = sessions.get(dayHour);
            if (sessionList.isEmpty()) {
                sessions.remove(dayHour);
            }
        }

    }

    public void deleteSessions(DayHour dayHour) {
        sessions.remove(dayHour);
    }
}
