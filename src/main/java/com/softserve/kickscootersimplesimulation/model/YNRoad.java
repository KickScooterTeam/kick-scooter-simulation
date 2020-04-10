package com.softserve.kickscootersimplesimulation.model;

import lombok.Data;


@Data
public class YNRoad {

    /* Coordinates from YourNavigation API
    * [0] is longitude(x), [1] is latitude(y)
     */
    double[][] coordinates;
}
