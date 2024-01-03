package privatemovie.be;

public class Category {

    private int id;
    private String Categories;

    public Category(int id, String categories) {
        this.id = id;
        Categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String categories) {
        Categories = categories;
    }
}