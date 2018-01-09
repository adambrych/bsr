package rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExternalTransferController {

    @RequestMapping(value = "/accounts/{accountNumber}/history", method = RequestMethod.POST)
    public ResponseEntity externalTransfer(@PathVariable String accountNumber, @RequestBody RequestPayload payload) {

        return new ResponseEntity(HttpStatus.OK);
    }
}
