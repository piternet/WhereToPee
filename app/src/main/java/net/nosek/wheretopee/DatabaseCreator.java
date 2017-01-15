package net.nosek.wheretopee;

public class DatabaseCreator {

    private DatabaseAdapter dbAdapter;

    DatabaseCreator(DatabaseAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    public void create() {
        dbAdapter.insertUser("admin", "Android 3.1");
        dbAdapter.insertUser("piternet", "Android 4.1");
        dbAdapter.insertUser("anonim", "iOS xd");
        dbAdapter.insertCoordinates(52.211997, 20.982090); // MIMUW
        dbAdapter.insertCoordinates(52.188821, 21.002455); // Woronicza
        dbAdapter.insertCoordinates(52.212498, 20.987348); // EDS
        dbAdapter.insertCoordinates(52.212499, 20.983044); // FUW
        dbAdapter.insertToilet(dbAdapter.getCoordinates(1), dbAdapter.getUser(1), "Toalety MIMUW, zazwyczaj czyste", true, false, true, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(2), dbAdapter.getUser(3), "Toaleta przy Carrefourze", false, false, true, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(3), dbAdapter.getUser(1), "Toaleta w Egurrola Dance Studio", true, false, false, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(4), dbAdapter.getUser(1), "Toaleta FUWu", true, true, true, true);
    }
}
