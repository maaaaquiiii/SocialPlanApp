package controllerTests;

import org.example.controller.ActivityController;
import org.example.controller.PlanController;
import org.example.controller.UserController;
import org.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class PlanControllerTests {
    private PlanController planController;
    private UserController userController;
    private ActivityController activityController;


    @BeforeEach
    void setUp() {
        userController = new UserController();
        planController = new PlanController();
        activityController = new ActivityController();
    }

    @Test
    void addActivityToPlanTest() throws Exception {
        activityController.createActivity("name", "desc", 12.1, 120, 50, "Generic");

        LocalDateTime dateTime = LocalDateTime.now();

        planController.createPlan("name", dateTime, "location", 10, UserController.getLoggedUser());
        planController.addActivity(1, 1);

        Assertions.assertEquals(1, planController.listPlans().get(0).getActivities().size());

        planController.listPlans().get(0).getActivities().forEach(activity -> {
            Assertions.assertEquals("name", activity.getName());
            Assertions.assertEquals("desc", activity.getDescription());
            Assertions.assertEquals(12.1, activity.getCost());
            Assertions.assertEquals(120, activity.getDuration());
            Assertions.assertEquals(50, activity.getCapacity());
            Assertions.assertEquals("Generic", activity.getType());
        });
    }

    @Test
    void joinPlanTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        userController.register("Macarena", 21, 663460420, "password");
        userController.login("Macarena", "password");
        activityController.createActivity("name", "desc", 13, 120, 50, "Theatre");
        planController.createPlan("name", dateTime, "location", 10, UserController.getLoggedUser());
        planController.addActivity(1, 1);
        userController.logout();

        userController.register("Nicolas", 21, 663460421, "password");
        userController.login("Nicolas", "password");

        Assertions.assertEquals(1, planController.listPlans().size());

        planController.joinPlan(1);

        Assertions.assertEquals("name", planController.listPlans().get(0).getName());
        Assertions.assertEquals(dateTime, planController.listPlans().get(0).getDateTime());
        Assertions.assertEquals("location", planController.listPlans().get(0).getLocation());
        Assertions.assertEquals(10, planController.listPlans().get(0).getMaxCapacity());

        Assertions.assertEquals(1, planController.listPlans().get(0).getActivities().size());
        Assertions.assertEquals(13, planController.listPlans().get(0).getActivities().get(0).getCost());

        Assertions.assertEquals("name", planController.listUserPlans().get(0).getName());
        Assertions.assertEquals(dateTime, planController.listUserPlans().get(0).getDateTime());
        Assertions.assertEquals("location", planController.listUserPlans().get(0).getLocation());
        Assertions.assertEquals(10, planController.listUserPlans().get(0).getMaxCapacity());

        Assertions.assertEquals(1, planController.listUserPlans().get(0).getActivities().size());
        Assertions.assertEquals(6.5, planController.listUserPlans().get(0).getActivities().get(0).getCost());
    }

    @Test
    void deleteActivityPlanTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        activityController.createActivity("name", "desc", 12.1, 120, 50, "Generic");
        planController.createPlan("name", dateTime, "location", 10, UserController.getLoggedUser());
        planController.addActivity(1, 1);

        Assertions.assertEquals(1, planController.listPlans().get(0).getActivities().size());

        planController.deleteActivity(1, 1);

        Assertions.assertEquals(0, planController.listPlans().get(0).getActivities().size());
    }

    @Test
    void leavePlanTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        userController.register("Macarena", 21, 663460420, "password");
        userController.login("Macarena", "password");
        activityController.createActivity("name", "desc", 13, 120, 50, "Theatre");
        planController.createPlan("name", dateTime, "location", 10, UserController.getLoggedUser());
        planController.addActivity(1, 1);
        userController.logout();

        userController.register("Nicolas", 21, 663460421, "password");
        userController.login("Nicolas", "password");

        Assertions.assertEquals(1, planController.listPlans().size());

        planController.joinPlan(1);
        planController.leavePlan(1);

        Assertions.assertEquals(0, UserController.getLoggedUser().getPlans().size());
    }

    @Test
    void ratePlanTest() throws Exception {
        userController.register("Macarena", 21, 663460420, "password");
        userController.login("Macarena", "password");
        LocalDateTime dateTime = LocalDateTime.now();
        planController.createPlan("The Lord of the Rings", dateTime, "Madrid", 10, UserController.getLoggedUser());
        userController.logout();

        userController.register("Nicolas", 21, 663460421, "password");
        userController.login("Nicolas", "password");
        planController.joinPlan(1);
        planController.ratePlan(1, 10);
        userController.logout();

        userController.register("Claudia", 23, 652340971, "pass");
        userController.login("Claudia", "pass");
        planController.joinPlan(1);
        planController.ratePlan(1, 7);
        userController.logout();

        userController.register("Maria", 23, 612397654, "123");
        userController.login("Maria", "123");
        planController.joinPlan(1);
        planController.ratePlan(1, 8);
        userController.logout();

        userController.register("Marcos", 23, 682109372, "ABC");
        userController.login("Marcos", "ABC");
        planController.joinPlan(1);
        planController.ratePlan(1, 6);
        userController.logout();

        Assertions.assertEquals(List.of(10, 7, 8, 6), planController.getAllPlans().get(0).getRating());
    }

    @Test
    void calculateAverageTest() throws Exception {
        userController.register("Macarena", 21, 663460420, "password");
        userController.login("Macarena", "password");
        LocalDateTime dateTime = LocalDateTime.now();
        planController.createPlan("The Lord of the Rings", dateTime, "Madrid", 10, UserController.getLoggedUser());

        userController.logout();

        userController.register("Nicolas", 21, 663460421, "password");
        userController.login("Nicolas", "password");
        planController.joinPlan(1);

        planController.ratePlan(1, 10);
        planController.ratePlan(1, 6);
        planController.ratePlan(1, 8);
        planController.ratePlan(1, 7);
        Assertions.assertEquals(7, planController.calculateAverage(1));
    }


    @Test
    void plansSortedByDateTest() throws Exception {
        userController.register("Macarena", 21, 663460420, "password");
        userController.login("Macarena", "password");

        activityController.createActivity("name", "desc", 13, 120, 50, "Theatre");

        planController.createPlan("name", LocalDateTime.now(), "location", 10, UserController.getLoggedUser());
        planController.addActivity(1, 1);

        planController.createPlan("name2", LocalDateTime.now(), "location", 10, UserController.getLoggedUser());
        planController.addActivity(2, 1);

        Assertions.assertEquals(2, planController.listPlansSortedDate().size());
    }

    @Test
    void planTotalCostTest() throws Exception {
        userController.register("Macarena", 21, 663460420, "password");
        userController.login("Macarena", "password");

        activityController.createActivity("name", "desc", 13, 120, 50, "Theatre");
        activityController.createActivity("name2", "desc", 150, 120, 50, "Cinema");

        planController.createPlan("name", LocalDateTime.now(), "location", 10, UserController.getLoggedUser());
        planController.addActivity(1, 1);

        Assertions.assertEquals(1, planController.listPlans().size());
        Assertions.assertEquals(13, planController.totalCost(1));
    }

}
