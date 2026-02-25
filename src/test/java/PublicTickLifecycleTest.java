
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class PublicTickLifecycleTest {
    private CityRescue cr;

    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(5, 5);
    }

    @Test
    void tick_movesUnitTowardIncident_andEventuallyResolves() throws Exception {
        int s = cr.addStation("A", 0, 0);
        int u = cr.addUnit(s, UnitType.AMBULANCE);

        int i = cr.reportIncident(IncidentType.MEDICAL, 1, 0, 1);
        cr.dispatch();

        cr.tick(); // should arrive at (0,1) in one tick
        assertTrue(cr.viewUnit(u).contains("LOC=(0,1)"), cr.fullDetailTraceback());

        cr.tick();
        cr.tick();

        assertTrue(cr.viewIncident(i).contains("STATUS=RESOLVED"), cr.fullDetailTraceback());
        assertTrue(cr.viewUnit(u).contains("STATUS=IDLE"),  cr.fullDetailTraceback());
    }

    @Test
    void tick_movesUnitTowardIncident_andEventuallyResolves_2() throws Exception {
        int s = cr.addStation("A", 0, 0);
        cr.setStationCapacity(s, 5);
        int u1 = cr.addUnit(s, UnitType.AMBULANCE);
        int u2 = cr.addUnit(s, UnitType.POLICE_CAR);
        int u3 = cr.addUnit(s, UnitType.FIRE_ENGINE);

        int i1 = cr.reportIncident(IncidentType.MEDICAL, 1, 3, 1); // should arrive in 4 ticks | takes 2 ticks to resolve
        int i2 = cr.reportIncident(IncidentType.CRIME, 1, 0, 3); // should arrive in 3 ticks | takes 3 ticks to resolve
        int i3 = cr.reportIncident(IncidentType.FIRE, 1, 2, 0); // should arrive in 2 ticks | takes 4 ticks to resolve

        /* i1 | 4 ticks to IN_PROGRESS | 6 ticks to RESOLVED
         * i2 | 3 ticks to IN_PROGRESS | 6 ticks to RESOLVED
         * i3 | 2 ticks to IN_PROGRESS | 6 ticks to RESOLVED
        */

        cr.dispatch();

        cr.tick(); 

        assertTrue(cr.viewUnit(u2).contains("LOC=(0,1)"), cr.fullDetailTraceback());
        assertTrue(cr.viewUnit(u1).contains("LOC=(0,1)"), cr.fullDetailTraceback());

        cr.tick(); 

        assertTrue(cr.viewUnit(u3).contains("LOC=(2,0)"), cr.fullDetailTraceback());
        assertTrue(cr.viewIncident(i3).contains("STATUS=IN_PROGRESS"), cr.fullDetailTraceback());

        cr.tick();

        assertTrue(cr.viewUnit(u2).contains("LOC=(0,3)"), cr.fullDetailTraceback());
        assertTrue(cr.viewIncident(i2).contains("STATUS=IN_PROGRESS"), cr.fullDetailTraceback());

        cr.tick();

        assertTrue(cr.viewUnit(u1).contains("LOC=(3,1)"), cr.fullDetailTraceback());
        assertTrue(cr.viewIncident(i1).contains("STATUS=IN_PROGRESS"), cr.fullDetailTraceback());

        cr.tick();
        cr.tick();

        assertTrue(cr.viewIncident(i1).contains("STATUS=RESOLVED"), cr.fullDetailTraceback());
        assertTrue(cr.viewIncident(i2).contains("STATUS=RESOLVED"), cr.fullDetailTraceback());
        assertTrue(cr.viewIncident(i3).contains("STATUS=RESOLVED"), cr.fullDetailTraceback());

        cr.tick();
    }
}
