package classscheduler.repositories;


import classscheduler.models.Classroom;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
public class ClassroomRepository {

    private List<Classroom> classrooms;

    public ClassroomRepository() {
        this.classrooms = new ArrayList<>();
    }

    public ClassroomRepository(Classroom[] classrooms) {
        this.classrooms = new ArrayList<>(Arrays.asList(classrooms));
    }

    public ClassroomRepository(Collection<Classroom> classrooms) {
        this.classrooms = new ArrayList<>(classrooms);
    }

    public void addClassroom(Classroom classrooms) {
        this.classrooms.add(classrooms);
    }
}
