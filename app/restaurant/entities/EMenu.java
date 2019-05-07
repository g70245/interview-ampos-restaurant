package restaurant.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EMenu {
    public String keyword;
    public Integer page;
    public Integer size;
    public Integer total;

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

    @JsonGetter
    public Integer getPage() {
        if (!doesNeedPaginate())
            page = null;
        return page;
    }

    @JsonGetter
    public Integer getSize() {
        if (!doesNeedPaginate())
            size = null;
        return size;
    }
}
