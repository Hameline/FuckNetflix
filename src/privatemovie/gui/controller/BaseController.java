package privatemovie.gui.controller;

import privatemovie.gui.model.CategoryModel;
import privatemovie.gui.model.MovieModel;

public abstract class BaseController {
    private MovieModel model;
    private CategoryModel categoriesModel;

    public MovieModel getModel() {
        return model;
    }

    public void setModel(MovieModel model) {
        this.model = model;
    }

    public CategoryModel getCategoriesModel() {
        return categoriesModel;
    }

    public void setCategoriesModel(CategoryModel categoriesModel) {
        this.categoriesModel = categoriesModel;
    }

    public abstract void setup() throws Exception;
}