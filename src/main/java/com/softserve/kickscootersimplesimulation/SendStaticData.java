package com.softserve.kickscootersimplesimulation;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SendStaticData {

    private static final String RAW_DATA = "raw-data";

    private final KafkaTemplate<String, ScooterRawDataDto> template;

    //todo: put here uuid when scooter is registred
    private static UUID firstId = UUID.fromString("37ffd5e0-9d4f-4dae-b54b-ca47bc4bb02c");
    private static UUID secondId = UUID.fromString("d883b312-33ce-43c1-8a9c-3dd07531e9ad");
    private static UUID thirdId = UUID.fromString("525fcbc7-4b94-4c16-98ec-5111a9d1f1be");
    private static UUID fourthId = UUID.fromString("b06f639a-a285-443d-a2ba-5fa34a51dc4a");
    private static UUID fifthId = UUID.fromString("a07d6ac9-0055-4147-b0c7-cdbfe7cec6fe");

    @Scheduled(fixedRate = 1000L)
    public void sendStatusDataToTopic(){
        var scooter = new ScooterRawDataDto(firstId, 1.0, 2.0, (short) 100);
        log.info("Send data to topic '{}': {}", RAW_DATA, scooter);
        template.send(RAW_DATA, scooter);
    }

   /* @Scheduled(fixedRate = 1050L)
    public void sendStatusDataToTopic2(){
        var scooter = new ScooterRawDataDto(secondId, 16.0, 0.0, (short) 60);
        log.info("Send data to topic '{}': {}", RAW_DATA, scooter);
        template.send(RAW_DATA, scooter);
    }
    @Scheduled(fixedRate = 1100L)
    public void sendStatusDataToTopic3(){
        var scooter = new ScooterRawDataDto(thirdId, 0.0, 3.0, (short) 74);
        log.info("Send data to topic '{}': {}", RAW_DATA, scooter);
        template.send(RAW_DATA, scooter);
    }
    @Scheduled(fixedRate = 1150L)
    public void sendStatusDataToTopic4(){
        var scooter = new ScooterRawDataDto(fourthId, 5.0, 0.0, (short) 80);
        log.info("Send data to topic '{}': {}", RAW_DATA, scooter);
        template.send(RAW_DATA, scooter);
    }

    @Scheduled(fixedRate = 1200L)
    public void sendStatusDataToTopic5(){
        var scooter = new ScooterRawDataDto(fifthId, 25.0, 0.0, (short) 90);
        log.info("Send data to topic '{}': {}", RAW_DATA, scooter);
        template.send(RAW_DATA, scooter);
    }*/

}