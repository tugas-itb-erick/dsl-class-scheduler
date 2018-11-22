package classscheduler.repositories;

import classscheduler.models.Lecturer;
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
public class LecturerRepository {

    private List<Lecturer> lecturers;

    public LecturerRepository() {
        lecturers = new ArrayList<>();
    }

    public LecturerRepository(Lecturer[] lecturers) {
        this.lecturers = new ArrayList<>(Arrays.asList(lecturers));
    }

    public LecturerRepository(Collection<Lecturer> lecturers) {
        this.lecturers = new ArrayList<>(lecturers);
    }

    public void addLecturer(Lecturer lecturer) {
        lecturers.add(lecturer);
    }
}
