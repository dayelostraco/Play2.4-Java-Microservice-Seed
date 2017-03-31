package entities;

import com.google.common.collect.ImmutableMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Logger;
import play.db.jpa.JPA;
import play.test.FakeApplication;
import play.test.Helpers;

import javax.persistence.metamodel.EntityType;

import java.util.List;
import java.util.Map;

/**
 * Created by Dayel Ostraco
 * 09/27/2015
 */
public abstract class TestDatabaseApplication {

    public static FakeApplication app;

    /**
     * Creates an instance of a Play Application using an H2 In-Memory
     * database with Evolutions turned on.
     */
    @BeforeClass
    public static void startApp() {

        Map<String, String> inMemoryDatabase = new ImmutableMap.Builder<String, String>()
                .put("db.default.driver", "org.h2.Driver")
                .put("db.default.url", "jdbc:h2:mem:play;MODE=MYSQL")
                .put("db.default.user", "")
                .put("db.default.password", "")
                .put("db.default.jndiName", "MicroserviceDS")
                .put("jpa.default", "MicroserviceDS")
                .put("applyEvolutions.default", "true")
                .put("applyDownEvolutions.default", "true")
                .build();

        app = Helpers.fakeApplication(inMemoryDatabase);
        Helpers.start(app);
    }

    /**
     * Stops the created Play Application
     */
    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

    /**
     * Deletes all non-Envers entities from the In-Memory Database after every
     * successful Test.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {

        JPA.withTransaction(() -> {
            JPA.em().createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

            JPA.em().getMetamodel().getEntities().stream().map(EntityType::getName).forEach(entity -> {

                // Filter our Envers Tables
                if(!entity.contains("Audit") && !entity.contains("DefaultRevisionEntity")) {
                    final List items = JPA.em().createQuery("from " + entity).getResultList();
                    Logger.warn("Will delete {} items from {}.", items.size(), entity);
                    items.stream().forEach(o -> JPA.em().remove(o));
                }
            });

            JPA.em().createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        });
    }

    @Test
    public abstract void testPersist() throws Exception;

    @Test
    public abstract void testUpdate() throws Exception;

    @Test
    public abstract void testDelete() throws Exception;

    @Test
    public abstract void findById() throws Exception;

    @Test
    public abstract void testAuditTrail() throws Exception;
}
