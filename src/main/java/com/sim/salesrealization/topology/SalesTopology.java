package com.sim.salesrealization.topology;



import com.sim.salesrealization.model.JsonSerde;
import com.sim.salesrealization.model.OneBMSInput;
import com.sim.salesrealization.model.SalesAggregatorResult;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

public class SalesTopology {

    //public static final String BANK_TRANSACTIONS = "bank-transactions";
    //public static final String BANK_BALANCES = "bank-balances";
    //public static final String REJECTED_TRANSACTIONS = "rejected-transactions";
   // public static final String BANK_BALANCES_STORE = "bank-balances-store";


    public static final String INPUT_TOPIC = "streaming.orders.input";
    public static final String SALES_AGGR_STORE = "aggregate-store";

   // public static final String SALES_AGGR_STORE = "time-windowed-aggregate-store";

    public static final String SALES = "sales-output";

    public static final String ANSI_PURPLE = "\u001B[35m";

    public static Topology buildTopology() {


        //final Serde<OneBMSInput> orderSerde = Serdes.serdeFrom(new ClassSerializer<>(),new ClassDeSerializer<>(OneBMSInput.class));
        final Serde<OneBMSInput> orderSerde = new JsonSerde<>(OneBMSInput.class);
       // final Serde<SalesAggregatorResult> aggregatorSerde = Serdes.serdeFrom(new ClassSerializer<>(),new ClassDeSerializer<>(SalesAggregatorResult.class));
        final Serde<SalesAggregatorResult> aggregatorSerde = new JsonSerde<>(SalesAggregatorResult.class);

        Initializer<SalesAggregatorResult> salesAggregatorInitializer= SalesAggregatorResult::new;

        Aggregator<String, OneBMSInput, SalesAggregatorResult> salesAggregator =  (key, value, aggregate)-> {
            aggregate.setSales(aggregate.getSales()+(value.getNoOfEngines() * value.getdBUNetPrice()));
            System.out.println(ANSI_PURPLE +"Sales Aggreg Result:  " + aggregate.getSales()+ANSI_PURPLE );
            return aggregate;
        };

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String,SalesAggregatorResult> salesSummary = streamsBuilder.stream(INPUT_TOPIC,
                        Consumed.with(Serdes.String(), orderSerde))
                //.filter()
                .groupBy( (key,value) -> value.getCustomerName(),Grouped.with(Serdes.String(),orderSerde))
                .aggregate(salesAggregatorInitializer,
                        salesAggregator,
                        Materialized.<String, SalesAggregatorResult,
                                        KeyValueStore<Bytes, byte[]>>as(
                                        SALES_AGGR_STORE)
                                .withValueSerde(aggregatorSerde)).toStream();

        //output to topic
     //   salesSummary.to(SALES, Produced.with(Serdes.String(), aggregatorSerde));

        final Topology topology = streamsBuilder.build();
        //System.out.println(topology.describe());

        salesSummary
                .foreach( (key, aggregation) ->
                        {
                            System.out.println("Received Summary :" +
                                    " Product =" + key +
                                    " Sales = " + aggregation.getSales());
                        }
                );
        return topology;


    }




}
