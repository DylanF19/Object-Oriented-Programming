
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

    // Test group 1 ============================================

    @Test
    void initialise_setsGridSize_andResetsTick() {
        int[] sz = cr.getGridSize();
        assertArrayEquals(new int[]{5,5}, sz);
        assertTrue(cr.getStatus().contains("TICK=0"));
    }

    @Test
    void addStation_assignsIdStartingAt1() throws Exception {
        int id1 = cr.addStation("Central", 1, 1);
        int id2 = cr.addStation("North", 1, 2);
        assertEquals(1, id1);
        assertEquals(2, id2);
    }

    @Test
    void addObstacle_outOfBounds_throws_andStateUnchanged() throws Exception {
        String before = cr.getStatus();
        assertThrows(InvalidLocationException.class, () -> cr.addObstacle(-1, 0));
        assertThrows(InvalidLocationException.class, () -> cr.addObstacle(4, -3));
        assertEquals(before, cr.getStatus());
    }

    // Test group 2 ============================================

    @Test
    void dispatch_assignsClosestEligibleUnit_thenLowestUnitId() throws Exception {
        int s = cr.addStation("A", 0, 0);
        int u1 = cr.addUnit(s, UnitType.POLICE_CAR);
        int u2 = cr.addUnit(s, UnitType.POLICE_CAR);

        int i1 = cr.reportIncident(IncidentType.CRIME, 2, 2, 2);

        cr.dispatch();

        String inc = cr.viewIncident(i1);
        assertTrue(inc.contains("UNIT=" + u1));
        assertFalse(inc.contains("UNIT=" + u2));
    }

    // Test group 3 ============================================

    @Test
    void getStatus_containsRequiredHeadings() throws Exception {
        String s = cr.getStatus();
        assertTrue(s.contains("TICK="));
        assertTrue(s.contains("INCIDENTS"));
        assertTrue(s.contains("UNITS"));
    }

    @Test
    void viewUnit_and_viewIncident_haveStablePrefixes() throws Exception {
        int st = cr.addStation("A", 0, 0);
        int u = cr.addUnit(st, UnitType.FIRE_ENGINE);
        int i = cr.reportIncident(IncidentType.FIRE, 3, 4, 4);

        // Output prefix must match the coursework specification examples
        // (e.g., "U#2 ..." and "I#1 ...").
        assertTrue(cr.viewUnit(u).startsWith("U#"));
        assertTrue(cr.viewIncident(i).startsWith("I#"));
    }

    // Test group 4 ============================================

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

    @Test
    void tiebreaking_fromDispatch() throws Exception {
        int s1 = cr.addStation("A", 0, 0);
        int s2 = cr.addStation("B", 4, 4);

        cr.setStationCapacity(s1, 5);
        cr.setStationCapacity(s2, 5);

        int u1 = cr.addUnit(s1, UnitType.AMBULANCE);
        int u2 = cr.addUnit(s2, UnitType.AMBULANCE);

        cr.reportIncident(IncidentType.MEDICAL, 1, 2, 2);

        cr.dispatch(); // i1 should now belong to u1

        assertTrue(cr.viewUnit(u1).contains("INCIDENT=0"), cr.fullDetailTraceback());
        assertTrue(cr.viewUnit(u2).contains("INCIDENT=-"), cr.fullDetailTraceback());
    }

    @Test
    void unitDoesNotMoveWhenBlocked() throws Exception {
        int s = cr.addStation("A", 2, 2);

        int u = cr.addUnit(s, UnitType.FIRE_ENGINE);

        cr.reportIncident(IncidentType.FIRE, 2, 4, 4);

        cr.addObstacle(1, 2);
        cr.addObstacle(2, 1);
        cr.addObstacle(3, 2);
        cr.addObstacle(2, 3);

        cr.addUnit(s, UnitType.FIRE_ENGINE);
        cr.addUnit(s, UnitType.FIRE_ENGINE);

        cr.dispatch();
        
        String out = cr.fullDetailTraceback(); System.out.println(out);
        cr.visualiseMap();

        assertTrue(cr.viewUnit(u).contains("LOC=(2,2)"), cr.fullDetailTraceback());

        cr.tick();

        assertTrue(cr.viewUnit(u).contains("LOC=(2,2)"), cr.fullDetailTraceback());
    }
}
