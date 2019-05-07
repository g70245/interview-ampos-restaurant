package restaurant.services;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import restaurant.entities.EBill;
import restaurant.entities.EBillOrder;
import restaurant.repos.BillRepo;
import restaurant.repos.FoodRepo;
import restaurant.services.BillServiceRequest.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class BillServiceImpl implements BillService{

    private FoodRepo foodRepo;
    private BillRepo billRepo;

    @Inject
    public BillServiceImpl(FoodRepo foodRepo, BillRepo billRepo) {
        this.foodRepo = foodRepo;
        this.billRepo = billRepo;
    }

    @Override
    public JsonNode getBill(Long id) {
        Optional<EBill> eBillHolder = billRepo.getBill(id);
        if (!eBillHolder.isPresent()) {
            // TODO: 2019-05-08 Handle situation that bill does not exist
            throw new RuntimeException();
        }

        return Json.toJson(eBillHolder.get());
    }

    @Override
    public JsonNode getBills() {
        return Json.toJson(billRepo.getBills());
    }

    @Override
    public JsonNode createBill(CreateRequest request) {
        EBill eBill = new EBill(createEBillOrderListFromNewOrders(request.newOrders));

        return Json.toJson(billRepo.createBill(eBill));
    }

    @Override
    public void updateBill(UpdateRequest request) {
        List<EBillOrder> eBillOrders = new ArrayList<>();

        if (!Objects.isNull(request.newOrders))
            eBillOrders = createEBillOrderListFromNewOrders(request.newOrders);

        if (!Objects.isNull(request.orders))
            eBillOrders.addAll(request.orders.parallelStream()
                .map(updateOrder -> new EBillOrder(updateOrder.foodName, updateOrder.quantity)).collect(Collectors.toList()));

        if (!billRepo.updateBill(new EBill(request.id, eBillOrders)).isUpdated) {
            // TODO: 2019-05-06 Handle use-case exception here
            throw new RuntimeException();
        }
    }

    private List<EBillOrder> createEBillOrderListFromNewOrders(List<NewOrder> newOrders) {
        List<EBillOrder> billOrders = newOrders.parallelStream()
                .map(order -> new EBillOrder(order.id, order.quantity)).collect(Collectors.toList());
        return foodRepo.getFoodDetailsForOrder(billOrders);
    }
}
