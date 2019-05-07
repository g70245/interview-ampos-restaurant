package restaurant.services;

import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FoodServiceRequest {
    public static class GetRequest {
        public String keyword;
        public Integer page;
        public Integer size;
        public GetRequest(Map<String, String[]> queryStrings) {
            this.keyword = Optional.ofNullable(queryStrings.get("keyword"))
                    .map(e -> e[0]).orElse(null);
            this.page = Optional.ofNullable(queryStrings.get("page"))
                    .map(e -> Integer.valueOf(e[0])).orElse(null);
            this.size = Optional.ofNullable(queryStrings.get("size"))
                    .map(e -> Integer.valueOf(e[0])).orElse(null);
        }
    }

    public static class AddRequest {
        @Constraints.Required
        @Constraints.MaxLength(64)
        public String name;

        @Constraints.Required
        @Constraints.MaxLength(255)
        public String description;

        @Constraints.Required
        @Constraints.MaxLength(255)
        @URL
        public String image;

        @Constraints.Required
        @Constraints.Min(0)
        @Constraints.Max(1000000)
        public BigDecimal price;

        @Constraints.Required
        public Set<String> types;
    }

    public static class UpdateRequest extends AddRequest {
        @Constraints.Required
        @Constraints.Min(1)
        public Long id;
    }

    public static class DeleteRequest {
        @Constraints.Required
        @Constraints.Min(1)
        public Long id;
    }
}
