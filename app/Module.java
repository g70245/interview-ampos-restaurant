import com.google.inject.AbstractModule;
import restaurant.repos.*;
import restaurant.repos.mysql.*;
import restaurant.services.*;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();

        // services
        bind(FoodService.class).to(FoodServiceImpl.class).asEagerSingleton();
        bind(BillService.class).to(BillServiceImpl.class).asEagerSingleton();

        // repos
        bind(FoodRepo.class).to(MySQLFoodRepo.class).asEagerSingleton();
        bind(BillRepo.class).to(MySQLBillRepo.class).asEagerSingleton();
    }
}
