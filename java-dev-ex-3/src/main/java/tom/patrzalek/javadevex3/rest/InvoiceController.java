package tom.patrzalek.javadevex3.rest;

import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tom.patrzalek.javadevex3.model.CreateInvoiceDTO;
import tom.patrzalek.javadevex3.service.stripe.InvoiceService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{invoiceId}")
    public Invoice getInvoiceById(@PathVariable String invoiceId) throws StripeException {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @GetMapping("/list")
    public List<Invoice> getAllForCurrentCustomer() throws StripeException {
        return invoiceService.getAllInvoicesForCurrentCustomer();
    }

    @PostMapping
    public Invoice create(@RequestBody @Valid List<CreateInvoiceDTO> createInvoiceDTO) throws StripeException {
        return invoiceService.create(createInvoiceDTO);
    }

}
