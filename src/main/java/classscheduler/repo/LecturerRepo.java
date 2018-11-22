package classscheduler.repo;

import classscheduler.model.Lecturer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
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
}
