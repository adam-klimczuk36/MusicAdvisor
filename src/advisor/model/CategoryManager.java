package advisor.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

    public List<Category> getCategories(UserDataManager userDataManager, UserData userData) throws IOException {
        String query = userDataManager.requestHandler("/v1/browse/categories", userData);
        return parseQueryCategories(query);
    }

    private List<Category> parseQueryCategories(String query) {
        List<Category> categories = new ArrayList<>();
        JsonObject jo = JsonParser.parseString(query).getAsJsonObject();
        JsonObject joCategories = jo.getAsJsonObject("categories");
        for (JsonElement category : joCategories.getAsJsonArray("items")) {
            String categoryName = category.getAsJsonObject().get("name").getAsString();
            String categoryId = category.getAsJsonObject().get("id").getAsString();
            categories.add(new Category(categoryName, categoryId));
        }
        return categories;
    }

    public String getCategoryId(List<Category> categories, String name) {
        for (Category category : categories) {
            if (category.getCategoryName().equals(name)) {
                return category.getCategoryId();
            }
        }
        return null;
    }
}
