package ai.service.ai_service.dto;

public class Doctor_Details_Dto {
    private String name;
    private String specialization;
    private int fees;
    private String availableDays;
    private double rating;
    private String reviews;
    private String location;
    private String search_text;

    public Doctor_Details_Dto(String name, String specialization, int fees, String availableDays, double rating, String reviews, String location, String search_text) {
        this.name = name;
        this.specialization = specialization;
        this.fees = fees;
        this.availableDays = availableDays;
        this.rating = rating;
        this.reviews = reviews;
        this.location = location;
        this.search_text = search_text;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getFees() {
        return fees;
    }

    public String getAvailableDays() {
        return availableDays;
    }

    public double getRating() {
        return rating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public void setAvailableDays(String availableDays) {
        this.availableDays = availableDays;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSearchText(String search_text) {
        this.search_text = search_text;
    }

    public String getLocation() {
        return location;
    }

    public String getSearchText() {
        return search_text;
    }

    public Doctor_Details_Dto() {
    }

    
}
