package com.moaaz.modernhome.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchRequest {

    @JsonFormat(pattern = "MM/dd/yyyy") // Specify the date format
    private LocalDate fromDate;
    @JsonFormat(pattern = "MM/dd/yyyy") // Specify the date format
    private LocalDate toDate;
    private OrderStatus orderStatus;


    public SearchRequest getOptimizedSearchRequest() {
        return new SearchRequest(convertDateToLocalDate(this.fromDate), convertDateToLocalDate(this.toDate), this.orderStatus);
    }

    private LocalDate convertDateToLocalDate(LocalDate fromDate) {
        String string = fromDate.toString();
        String split[] = string.split("/");
        int year = fromDate.getYear();
        int month = fromDate.getMonthValue();
        int day = fromDate.getDayOfMonth();
        return LocalDate.of(year, month, day);
//        String newFormat = split[2] + "-" + split[0] + "-" + split[1];
//        return LocalDate.parse(newFormat);

    }


}
