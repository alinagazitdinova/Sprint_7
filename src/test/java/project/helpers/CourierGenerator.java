package project.helpers;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public Courier random(){
        return new Courier(RandomStringUtils.randomAlphanumeric(10), "1234512345", "blinchik");

    }

    public Courier generic() {
        return new Courier("alina", "1234512345", "blinchik");
    }
}
