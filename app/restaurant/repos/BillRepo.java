package restaurant.repos;

import restaurant.entities.EBill;

import java.util.List;
import java.util.Optional;

public interface BillRepo {
    List<EBill> getBills();
    Optional<EBill> getBill(Long id);
    EBill createBill(EBill eBill);
    EBill updateBill(EBill eBill);
}