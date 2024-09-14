package controllerTests;

import org.example.controller.ActivityController;
import org.example.model.Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class ActivityControllerTests {
    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        activityController = new ActivityController();
    }

    @Test
    void createCinemaActivity() {
        activityController.createActivity("Harry Potter", "and the Philosopher's Stone", 12.5, 152, 60, "Cinema");
        List<Activity> activities = activityController.getAllActivities();

        Assertions.assertEquals(1, activities.size());

        Assertions.assertEquals("Harry Potter", activities.get(0).getName());
        Assertions.assertEquals("and the Philosopher's Stone", activities.get(0).getDescription());
        Assertions.assertEquals(12.5, activities.get(0).getCost());
        Assertions.assertEquals(152, activities.get(0).getDuration());
        Assertions.assertEquals(60, activities.get(0).getCapacity());
        Assertions.assertEquals("Cinema", activities.get(0).getType());
    }

    @Test
    void createCinemaActivityWithoutCapacity() {
        activityController.createActivity("Harry Potter", "and the Philosopher's Stone", 12.5, 152, 0, "Cinema");
        List<Activity> activities = activityController.getAllActivities();

        Assertions.assertEquals(1, activities.size());

        Assertions.assertEquals("Harry Potter", activities.get(0).getName());
        Assertions.assertEquals("and the Philosopher's Stone", activities.get(0).getDescription());
        Assertions.assertEquals(12.5, activities.get(0).getCost());
        Assertions.assertEquals(152, activities.get(0).getDuration());
        Assertions.assertEquals(0, activities.get(0).getCapacity());
        Assertions.assertEquals("Cinema", activities.get(0).getType());
    }

    @Test
    void createTheatreActivity() {
        activityController.createActivity("La vida es sueño", "Calderon de la Barca", 15.0, 110, 100, "Theatre");
        List<Activity> activities = activityController.getAllActivities();

        Assertions.assertEquals(1, activities.size());

        Assertions.assertEquals("La vida es sueño", activities.get(0).getName());
        Assertions.assertEquals("Calderon de la Barca", activities.get(0).getDescription());
        Assertions.assertEquals(15.0, activities.get(0).getCost());
        Assertions.assertEquals(110, activities.get(0).getDuration());
        Assertions.assertEquals(100, activities.get(0).getCapacity());
        Assertions.assertEquals("Theatre", activities.get(0).getType());
    }

    @Test
    void createGenericActivity() {
        activityController.createActivity("Cervezas", "Madrid", 10.0, 90, 0, "Generic");
        List<Activity> activities = activityController.getAllActivities();

        Assertions.assertEquals(1, activities.size());

        Assertions.assertEquals("Cervezas", activities.get(0).getName());
        Assertions.assertEquals("Madrid", activities.get(0).getDescription());
        Assertions.assertEquals(10.0, activities.get(0).getCost());
        Assertions.assertEquals(90, activities.get(0).getDuration());
        Assertions.assertEquals(0, activities.get(0).getCapacity());
        Assertions.assertEquals("Generic", activities.get(0).getType());
    }
}
