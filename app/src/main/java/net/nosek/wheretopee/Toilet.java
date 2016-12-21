package net.nosek.wheretopee;

import com.google.android.gms.maps.model.LatLng;

public class Toilet {
    private long id;
    private LatLng coordinates;
    private User userWhoAdded;
    private String description;
    private boolean isFree, hasChangingTable, disabledAccesible;
    private boolean acceptedByAdmin = false;

    public Toilet(long id, LatLng coordinates, User userWhoAdded, String description, boolean hasChangingTable, boolean disabledAccesible, boolean acceptedByAdmin, boolean isFree) {
        this.id = id;
        this.coordinates = coordinates;
        this.userWhoAdded = userWhoAdded;
        this.description = description;
        this.hasChangingTable = hasChangingTable;
        this.disabledAccesible = disabledAccesible;
        this.acceptedByAdmin = acceptedByAdmin;
        this.isFree = isFree;
    }

    /* Constructor without acceptedByAdmin fiels, false by default */
    public Toilet(LatLng coordinates, User userWhoAdded, String description, boolean isFree, boolean hasChangingTable, boolean disabledAccesible) {
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

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
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
        return "Toilet{" +
                "id=" + id +
                "coordinates=" + coordinates +
                ", userWhoAdded=" + userWhoAdded +
                ", description='" + description + '\'' +
                ", isFree=" + isFree +
                ", hasChangingTable=" + hasChangingTable +
                ", disabledAccesible=" + disabledAccesible +
                ", acceptedByAdmin=" + acceptedByAdmin +
                '}';
    }
}
