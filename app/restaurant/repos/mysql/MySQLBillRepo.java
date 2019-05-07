package restaurant.repos.mysql;

import models.Bill;
import models.BillOrder;
import restaurant.entities.EBill;
import restaurant.entities.EBillOrder;
import restaurant.repos.BillRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MySQLBillRepo implements BillRepo {
    @Override
    public List<EBill> getBills() {
        return Bill.find.all().parallelStream()
                .map(bill -> new EBill(bill.id, bill.createdAt, createEBillOrderListFromBillOrders(bill.billOrders)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EBill> getBill(Long id) {
        return Optional.ofNullable(Bill.find.byId(id))
                .map(bill -> new EBill(bill.id, bill.createdAt, createEBillOrderListFromBillOrders(bill.billOrders)));
    }

    @Override
    public EBill createBill(EBill eBill) {
        Bill bill = new Bill();
        bill.billOrders = eBill.orders.parallelStream()
                .map(order -> {
                    BillOrder newBillOrder = new BillOrder();
                    newBillOrder.foodName = order.foodName;
                    newBillOrder.unitPrice = order.unitPrice;
                    newBillOrder.quantity = order.quantity;
                    return newBillOrder;
                }).collect(Collectors.toList());
        bill.save();

        eBill.submit(bill.id, bill.createdAt, createEBillOrderListFromBillOrders(bill.billOrders));

        return eBill;
    }

    @Override
    public EBill updateBill(EBill eBill) {
        Optional<Bill> billHolder = Optional.ofNullable(Bill.find.byId(eBill.id));

        billHolder.ifPresent(bill -> {
            List<BillOrder> newBillOrders = new ArrayList<>();

            Map<String, BillOrder> oldBillOrderMap = bill.billOrders.parallelStream()
                    .collect(Collectors.toMap(billOrder -> billOrder.foodName, billOrder -> billOrder));
            eBill.orders.parallelStream().forEach(order -> {
                Optional<BillOrder> sameNameOrder = Optional.ofNullable(oldBillOrderMap.get(order.foodName));

                if (order.isNewOrder() && !sameNameOrder.isPresent()) {
                    newBillOrders.add(createNewBillOrderForm(order));
                    return;
                }

                if (!order.isNewOrder() && sameNameOrder.isPresent()) {
                    newBillOrders.add(sameNameOrder.get().updateQuantity(order.quantity));
                }
            });

            bill.billOrders = newBillOrders;
            bill.update();

            eBill.isUpdated = true;
        });

        return eBill;
    }

    private List<EBillOrder> createEBillOrderListFromBillOrders(List<BillOrder> billOrders) {
        return  billOrders.parallelStream()
                .map(order -> new EBillOrder(order.foodName, order.unitPrice, order.quantity))
                .collect(Collectors.toList());
    }

    private BillOrder createNewBillOrderForm(EBillOrder eBillOrder) {
        BillOrder billOrder = new BillOrder();
        billOrder.foodName = eBillOrder.foodName;
        billOrder.unitPrice = eBillOrder.unitPrice;
        billOrder.quantity = eBillOrder.quantity;

        return billOrder;
    }
}
