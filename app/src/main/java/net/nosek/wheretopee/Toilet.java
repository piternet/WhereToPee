package net.nosek.wheretopee;

import com.google.android.gms.maps.model.LatLng;

public class Toilet {
    private long id;
    private Coordinates coordinates;
    private User userWhoAdded;
    private String description;
    private boolean isFree, hasChangingTable, disabledAccesible;
    private boolean acceptedByAdmin = false;

    public Toilet(long id, Coordinates coordinates, User userWhoAdded, String description, boolean isFree, boolean hasChangingTable, boolean disabledAccesible, boolean acceptedByAdmin) {
        this.id = id;
        this.coordinates = coordinates;
        this.userWhoAdded = userWhoAdded;
        this.description = description;
        this.isFree = isFree;
        this.hasChangingTable = hasChangingTable;
        this.disabledAccesible = disabledAccesible;
        this.acceptedByAdmin = acceptedByAdmin;
    }
    /* without acceptedByAdmin */
    public Toilet(long id, Coordinates coordinates, User userWhoAdded, String description, boolean isFree, boolean hasChangingTable, boolean disabledAccesible) {
        this.id = id;
        this.coordinates = coordinates;
        this.userWhoAdded = userWhoAdded;
        this.description = description;
        this.isFree = isFree;
        this.hasChangingTable = hasChangingTable;
        this.disabledAccesible = disabledAccesible;
    }

    public long getId() {
        return id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public User getUserWhoAdded() {
        return userWhoAdded;
    }

    public void setUserWhoAdded(User userWhoAdded) {
        this.userWhoAdded = userWhoAdded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isHasChangingTable() {
        return hasChangingTable;
    }

    public void setHasChangingTable(boolean hasChangingTable) {
        this.hasChangingTable = hasChangingTable;
    }

    public boolean isDisabledAccesible() {
        return disabledAccesible;
    }

    public void setDisabledAccesible(boolean disabledAccesible) {
        this.disabledAccesible = disabledAccesible;
    }

    public boolean isAcceptedByAdmin() {
        return acceptedByAdmin;
    }

    public void setAcceptedByAdmin(boolean acceptedByAdmin) {
        this.acceptedByAdmin = acceptedByAdmin;
    }

    @Override
    public String toString() {

        String isFreeString = isFree ? "bezpłatna" : "płatna";
        String hasChangingTableString = hasChangingTable ? "posiada przewijak" : "nie posiada przewijaka";
        String disabledAccesibleString = disabledAccesible ? "jest przystosowana" : "nie jest przystosowana";

        return "Toaleta ta jest " + isFreeString + ", " + hasChangingTableString + " dla niemowląt, " + disabledAccesibleString +
                " do potrzeb osób niepełnosprawnych." + "\n" + "Została dodana przez użytkownika " + userWhoAdded.getNickname() + ".";
    }
}
