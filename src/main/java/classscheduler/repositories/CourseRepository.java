package classscheduler.repositories;

import classscheduler.models.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseRepository {

    private List<Course> courses;

    public CourseRepository(List<Course> courses) {
        this.courses = courses;
    }
}
