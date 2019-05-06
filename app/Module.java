import com.google.inject.AbstractModule;
import restaurant.repos.FoodRepo;
import restaurant.repos.mysql.MySQLFoodRepo;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();

        // repos
        bind(FoodRepo.class).to(MySQLFoodRepo.class);
    }
}
