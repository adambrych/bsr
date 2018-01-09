package bsr.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestPayload {
    String source_account;
    long amount;
    String title;
    String name;
}
