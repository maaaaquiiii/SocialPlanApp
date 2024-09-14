package controllerTests;

import org.example.controller.UserController;
import org.example.model.Plan;
import org.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserControllerTests {
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void registerWithValidDataTest() throws Exception {
        userController.register("Macarena", 21, 634870912, "abc");
        Map<String, User> users = userController.getUserMap();

        Assertions.assertEquals(1, users.size());

        Assertions.assertEquals("Macarena", users.get("Macarena").getName());
        Assertions.assertEquals(21, users.get("Macarena").getAge());
        Assertions.assertEquals(634870912, users.get("Macarena").getPhone());
        Assertions.assertEquals("abc", users.get("Macarena").getPassword());
    }

    @Test
    void registerMoreThanOneUserTest() throws Exception {
        userController.register("Macarena", 21, 634870912, "abc");
        userController.register("Nicolas", 21, 689023045, "def");
        Map<String, User> users = userController.getUserMap();

        Assertions.assertEquals(2, users.size());

        Assertions.assertEquals("Macarena", users.get("Macarena").getName());
        Assertions.assertEquals(21, users.get("Macarena").getAge());
        Assertions.assertEquals(634870912, users.get("Macarena").getPhone());
        Assertions.assertEquals("abc", users.get("Macarena").getPassword());

        Assertions.assertEquals("Nicolas", users.get("Nicolas").getName());
        Assertions.assertEquals(21, users.get("Nicolas").getAge());
        Assertions.assertEquals(689023045, users.get("Nicolas").getPhone());
        Assertions.assertEquals("def", users.get("Nicolas").getPassword());
    }

    @Test
    void registerUserWithInvalidAgeTest() {
        Exception exceptionWithAgeUnderMin = Assertions.assertThrows(Exception.class,
                () -> userController.register("Macarena", 12, 634870912, "abc"));

        Exception exceptionWithAgeOverMax = Assertions.assertThrows(Exception.class,
                () -> userController.register("Macarena", 112, 634870912, "abc"));

        Assertions.assertEquals("The age must be between 14 and 100 years.", exceptionWithAgeUnderMin.getMessage());
        Assertions.assertEquals("The age must be between 14 and 100 years.", exceptionWithAgeOverMax.getMessage());
    }

    @Test
    void registerUserWithNameThatAlreadyExistsTest() throws Exception {
        userController.register("Macarena", 21, 634870912, "abc");

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> userController.register("Macarena", 22, 623895633, "abc"));

        Assertions.assertEquals("Existing user name.", exception.getMessage());
    }

    @Test
    void registerUserWithPhoneThatAlreadyExistsTest() throws Exception {
        userController.register("Macarena", 21, 634870912, "abc");

        Exception exception = Assertions.assertThrows(Exception.class,
                () -> userController.register("Nicolas", 21, 634870912, "abc"));

        Assertions.assertEquals("Existing user phone.", exception.getMessage());
    }

    @Test
    void registerUserWithInvalidPassword() {
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> userController.register("Macarena", 21, 634870912, "ab"));

        Assertions.assertEquals("Password must be longer than 3 characters.", exception.getMessage());
    }

    @Test
    void loginValidUserTest() throws Exception {
        // First we register a user
        userController.register("Macarena", 21, 634870912, "abc");

        User userThatWeJustRegistered = new User("Macarena", 21, 634870912, "abc");
        userController.login(userThatWeJustRegistered.getName(), userThatWeJustRegistered.getPassword());

        Assertions.assertEquals(userThatWeJustRegistered.getName(), UserController.getLoggedUser().getName());
        Assertions.assertEquals(userThatWeJustRegistered.getAge(), UserController.getLoggedUser().getAge());
        Assertions.assertEquals(userThatWeJustRegistered.getPhone(), UserController.getLoggedUser().getPhone());
        Assertions.assertEquals(userThatWeJustRegistered.getPassword(), UserController.getLoggedUser().getPassword());
    }

    @Test
    void loginUserWithIncorrectPasswordTest() throws Exception {
        // First we register a user
        userController.register("Macarena", 21, 634870912, "def");

        User userThatWeJustRegistered = new User("Macarena", 21, 634870912, "def");
        Exception exception = Assertions.assertThrows(Exception.class, () ->
                userController.login(userThatWeJustRegistered.getName(), "incorrectPassword"));

        Assertions.assertEquals("Incorrect password.", exception.getMessage());
    }

    @Test
    void loginUserWithUserThatHasNotBeingRegisteredTest() throws Exception {
        userController.register("Macarena", 21, 634870912, "abc");

        User userThatWeJustRegistered = new User("Macarena", 21, 634870912, "abc");
        Exception exception = Assertions.assertThrows(Exception.class, () ->
                userController.login("Nicolas", userThatWeJustRegistered.getPassword()));

        Assertions.assertEquals("User not found.", exception.getMessage());
    }

    @Test
    void joinPlanTest() throws Exception {
        registerAndLoginAUserForTests("Macarena", 634870912);
        LocalDateTime planDateTime = LocalDateTime.now();
        UserController.joinPlan(testPlan(1,"testPlan", planDateTime, "Some location", 10));
        List<Plan> userPlans = UserController.getLoggedUser().getPlans();

        Assertions.assertEquals(1, userPlans.size());

        Assertions.assertEquals(1, userPlans.get(0).getId());
        Assertions.assertEquals("Some location", userPlans.get(0).getLocation());
        Assertions.assertEquals(planDateTime, userPlans.get(0).getDateTime());
        Assertions.assertEquals(10, userPlans.get(0).getMaxCapacity());
        Assertions.assertEquals(0, userPlans.get(0).getParticipants().size());
        Assertions.assertEquals(0, userPlans.get(0).getActivities().size());
    }

    @Test
    void joinMultipleTests() throws Exception {
        registerAndLoginAUserForTests("Macarena", 62367378);
        LocalDateTime planDateTime = LocalDateTime.now();
        UserController.joinPlan(testPlan(1,"testPlan", planDateTime, "Some location", 10));
        UserController.joinPlan(testPlan(2, "testPlan2", planDateTime, "Some location", 10));
        List<Plan> userPlans = UserController.getLoggedUser().getPlans();

        Assertions.assertEquals(2, userPlans.size());

        Assertions.assertEquals(1, userPlans.get(0).getId());
        Assertions.assertEquals("Some location", userPlans.get(0).getLocation());
        Assertions.assertEquals(planDateTime, userPlans.get(0).getDateTime());
        Assertions.assertEquals(10, userPlans.get(0).getMaxCapacity());
        Assertions.assertEquals(0, userPlans.get(0).getParticipants().size());
        Assertions.assertEquals(0, userPlans.get(0).getActivities().size());

        Assertions.assertEquals(2, userPlans.get(1).getId());
        Assertions.assertEquals("Some location", userPlans.get(1).getLocation());
        Assertions.assertEquals(planDateTime, userPlans.get(1).getDateTime());
        Assertions.assertEquals(10, userPlans.get(1).getMaxCapacity());
        Assertions.assertEquals(0, userPlans.get(1).getParticipants().size());
        Assertions.assertEquals(0, userPlans.get(1).getActivities().size());
    }

    private void registerAndLoginAUserForTests(String name, int phoneNumber) throws Exception {
        // First we register a user
        userController.register(name, 21, phoneNumber , "ajdh");
        userController.login(name, "ajdh");
    }

    private Plan testPlan(int id, String name, LocalDateTime dateTime, String location, int maxCapacity) {
        return new Plan.Builder()
                .id(id)
                .name(name)
                .dateTime(dateTime)
                .location(location)
                .maxCapacity(maxCapacity)
                .activities(new ArrayList<>())
                .participants(new ArrayList<>())
                .build();
    }

}
