package classscheduler.repositories;


import classscheduler.models.Classroom;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class ClassroomRepository {

    private HashMap<String, Classroom> classrooms;

    public ClassroomRepository() {
        this.classrooms = new HashMap<>();
    }

    public ClassroomRepository(Classroom[] classrooms) {
        this.classrooms = new HashMap<>();
        Arrays.stream(classrooms).forEach(classroom -> this.classrooms.put(classroom.getId(), classroom));
    }

    public ClassroomRepository(Collection<Classroom> classrooms) {
        this.classrooms = new HashMap<>();
        classrooms.forEach(classroom -> this.classrooms.put(classroom.getId(), classroom));
    }

    public void addClassroom(Classroom classroom) {
        this.classrooms.put(classroom.getId(), classroom);
    }
}
