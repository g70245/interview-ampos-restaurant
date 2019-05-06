package restaurant.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EMenu {
    public String keyword;
    public Integer page;
    public Integer size;
    public Integer total;

    @JsonInclude
    public List<EFood> foods;

    public EMenu(Integer page, Integer size, String keyword) {
        this.page = page;
        this.size = size;
        this.keyword = keyword;

        this.foods = new ArrayList<>();
    }

    public boolean doesNeedPaginate() {
        return page != null && size != null && page > 0 && size > 0;
    }

    public boolean doesNeedSearch() {
        return keyword != null;
    }

    public void setFoods(List<EFood> foods) {
        this.foods.addAll(foods);
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Integer getPage() {
        if (page != null && page < 1)
            page = null;
        return page;
    }

    public Integer getSize() {
        if (size != null && size < 1)
            page = null;
        return page;
    }
}
