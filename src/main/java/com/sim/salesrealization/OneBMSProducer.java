package com.sim.salesrealization;

import com.sim.salesrealization.config.MariaDBManager;
import com.sim.salesrealization.model.OneBMSInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Map;

public class OneBMSProducer {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {
        KafkaProducer<Long, String> producer =
                new KafkaProducer<>(Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
                ));


        //Get all records from DB
        MariaDBManager sqldbm = new MariaDBManager();
        sqldbm.setUp();
        List<OneBMSInput> oneBMSRecords = sqldbm.getAllrecords();

        oneBMSRecords.stream()
                .map(oneBMS -> new ProducerRecord<>("streaming.orders.input", oneBMS.getId(), toJson(oneBMS)))
                .forEach(record -> {
                    send(producer, record);
                    System.out.println(ANSI_PURPLE +"Kafka oneBMS Stream Generator : Sending Event : "+ String.join(",", record.toString())  + ANSI_RESET);
                });


        //send(bankTransactionProducer, new ProducerRecord<>("bank-transactions", bankTransaction.getBalanceId(), toJson(bankTransaction)));

    }

    @SneakyThrows
    private static void send(KafkaProducer<Long, String> bmsProducer, ProducerRecord<Long, String> record) {
        bmsProducer.send(record).get();
    }

    @SneakyThrows
    private static String toJson(OneBMSInput oneBMSInput) {
        return OBJECT_MAPPER.writeValueAsString(oneBMSInput);
    }
}
