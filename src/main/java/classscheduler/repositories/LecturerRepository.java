package classscheduler.repositories;

import classscheduler.models.Lecturer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class LecturerRepository {

    private HashMap<String, Lecturer> lecturers;

    public LecturerRepository() {
        lecturers = new HashMap<>();
    }

    public LecturerRepository(Lecturer[] lecturers) {
        this.lecturers = new HashMap<>();
        Arrays.stream(lecturers).forEach(lecturer -> this.lecturers.put(lecturer.getId(), lecturer));
    }

    public LecturerRepository(Collection<Lecturer> lecturers) {
        this.lecturers = new HashMap<>();
        lecturers.forEach(lecturer -> this.lecturers.put(lecturer.getId(), lecturer));
    }

    public void addLecturer(Lecturer lecturer) {
        lecturers.put(lecturer.getId(), lecturer);
    }
}
