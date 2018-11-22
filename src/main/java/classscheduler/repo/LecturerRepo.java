package classscheduler.repo;

import classscheduler.model.Lecturer;
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
public class LecturerRepo {

    private List<Lecturer> lecturers;

    public LecturerRepo() {
        lecturers = new ArrayList<>();
    }

    public LecturerRepo(Lecturer[] lecturers) {
        this.lecturers = new ArrayList<>(Arrays.asList(lecturers));
    }

    public LecturerRepo(Collection<Lecturer> lecturers) {
        this.lecturers = new ArrayList<>(lecturers);
    }

    public void addLecturer(Lecturer lecturer) {
        lecturers.add(lecturer);
    }
}
