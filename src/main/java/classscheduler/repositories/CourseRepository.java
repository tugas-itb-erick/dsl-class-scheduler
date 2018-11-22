package classscheduler.repositories;

import classscheduler.models.Course;
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
public class CourseRepository {

    private List<Course> courses;

    public CourseRepository() {
        this.courses = new ArrayList<>();
    }

    public CourseRepository(Course[] courses) {
        this.courses = new ArrayList<>(Arrays.asList(courses));
    }

    public CourseRepository(Collection<Course> courses) {
        this.courses = new ArrayList<>(courses);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
