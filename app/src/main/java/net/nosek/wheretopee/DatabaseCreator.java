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
        dbAdapter.insertCoordinates(52.190230, 21.016761); // Metro Wierzbno
        dbAdapter.insertCoordinates(52.213397, 20.986022); // Wydział Biologii
        dbAdapter.insertCoordinates(52.215335, 20.982778); // WChUW
        dbAdapter.insertCoordinates(52.210885, 21.004316); // Pole Mokotowskie, toaleta publiczna
        dbAdapter.insertCoordinates(52.209585, 20.984265); // Szpital Banacha
        dbAdapter.insertToilet(dbAdapter.getCoordinates(1), dbAdapter.getUser(1), "Toaleta MIMUW", true, false, true, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(2), dbAdapter.getUser(3), "Toaleta przy Carrefourze", false, false, true, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(3), dbAdapter.getUser(1), "Toaleta w Egurrola Dance Studio", true, false, false, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(4), dbAdapter.getUser(1), "Toaleta FUWu", true, true, true, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(5), dbAdapter.getUser(2), "Toaleta na Metrze Wierzbno", true, false, false, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(6), dbAdapter.getUser(2), "Toaleta na Wydziale Biologii UW", true, false, false, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(7), dbAdapter.getUser(2), "Toaleta na Wydziale Chemii UW", true, false, false, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(8), dbAdapter.getUser(1), "Toaleta na Polu Mokotowskim", false, true, false, true);
        dbAdapter.insertToilet(dbAdapter.getCoordinates(9), dbAdapter.getUser(1), "Toaleta w szpitalu na Banacha", false, true, true, true);
    }
}
