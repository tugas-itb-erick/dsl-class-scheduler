package classscheduler.repositories;

import classscheduler.models.Timeslot;
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
public class TimeslotRepository {

    private List<Timeslot> timeslots;

    public TimeslotRepository() {
        this.timeslots = new ArrayList<>();
    }

    public TimeslotRepository(Timeslot[] timeslots) {
        this.timeslots = new ArrayList<>(Arrays.asList(timeslots));
    }

    public TimeslotRepository(Collection<Timeslot> timeslots) {
        this.timeslots = new ArrayList<>(timeslots);
    }

    public void addTimeslot(Timeslot timeslot) {
        this.timeslots.add(timeslot);
    }
}
