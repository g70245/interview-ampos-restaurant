package restaurant.repos.mysql;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import models.Food;
import models.Type;
import restaurant.entities.EFood;
import restaurant.entities.EMenu;
import restaurant.repos.FoodRepo;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MySQLFoodRepo implements FoodRepo {

    @Override
    public EMenu getMenu(EMenu eMenu) {
        ExpressionList<Food> expressions = Ebean.find(Food.class).fetch("types").where();

        if (eMenu.doesNeedSearch()) {
             expressions.disjunction()
                        .icontains("name", eMenu.keyword)
                        .icontains("description", eMenu.keyword)
                        .icontains("types.name", eMenu.keyword)
                    .endJunction();
        }

        expressions.order().asc("id");

        if (eMenu.doesNeedPaginate()) {
            PagedList<Food> pagedFoods = expressions
                    .order("id")
                    .setFirstRow((eMenu.page - 1) * eMenu.size)
                    .setMaxRows(eMenu.size)
                    .findPagedList();

            pagedFoods.loadCount();

            eMenu.setFoods(createEFoodListFrom(pagedFoods.getList()));
            eMenu.setTotal(pagedFoods.getTotalCount());
        } else {
            eMenu.setFoods(createEFoodListFrom(expressions.order("id").findList()));
            eMenu.setTotal(eMenu.foods.size());
        }

        return eMenu;
    }

    @Override
    public EFood addFood(EFood eFood) {
        Food food = new Food();

        food.name = eFood.name;
        food.description = eFood.description;
        food.image = eFood.image;
        food.price = eFood.price;
        food.types = getFoodTypes(eFood.types);
        food.save();

        eFood.id = food.id;
        return eFood;
    }

    @Override
    public EFood updateFood(EFood eFood) {
        Optional<Food> foodHolder = Optional.ofNullable(Food.find.byId(eFood.id));

        foodHolder.ifPresent(food -> {
            food.name = eFood.name;
            food.description = eFood.description;
            food.image = eFood.image;
            food.price = eFood.price;
            food.types = getFoodTypes(eFood.types);
            food.update();

            eFood.isUpdated = true;
        });

        return eFood;
    }

    @Override
    public void deleteFood(EFood eFood) {
        Optional.ofNullable(Food.find.byId(eFood.id)).ifPresent(f -> f.delete());
    }

    private List<Type> getFoodTypes(Set<String> typeStrs) {
        List<Type> newTypes = Ebean.find(Type.class).where().in("name", typeStrs).findList();

        Set<String> newTypeSet = newTypes.parallelStream().map(type -> type.name).collect(Collectors.toSet());
        newTypes.addAll(typeStrs.parallelStream()
                .filter(ts -> !newTypeSet.contains(ts)).map(ts -> new Type(ts))
                .collect(Collectors.toList()));

        System.out.println(typeStrs);
        System.out.println(newTypes);

        return newTypes;
    }

    private List<EFood> createEFoodListFrom(List<Food> foods) {
        return foods.parallelStream()
                .map(food -> new EFood(food.id, food.name, food.description, food.image, food.price, createTypeStringSetForm(food.types)))
                .collect(Collectors.toList());
    }

    private Set<String> createTypeStringSetForm(List<Type> types) {
        return types.parallelStream().map(type -> type.name).collect(Collectors.toSet());
    }
}
