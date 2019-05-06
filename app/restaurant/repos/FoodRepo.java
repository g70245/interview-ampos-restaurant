package restaurant.repos;

import restaurant.entities.EFood;
import restaurant.entities.EMenu;

import java.util.List;

public interface FoodRepo {
    EMenu getMenu(EMenu eMenu);
    EFood addFood(EFood eFood);
    EFood updateFood(EFood eFood);
    void deleteFood(EFood eFood);
}
