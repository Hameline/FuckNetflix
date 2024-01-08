package privatemovie.be;

public class Category {

    private int id;
    private String category;

    public Category(int id, String categories) {
        this.id = id;
        category = categories;
    }

    public Category() {

    }

    public Category(String categories) {
        this.category = categories;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategories() {
        return category;
    }

    public void setCategories(String categories) {
        category = categories;
    }

    public String toString() {
        return category;
    }

    public String getCategory() {
        return category;
    }
}