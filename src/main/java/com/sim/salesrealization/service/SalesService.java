package com.sim.salesrealization.service;

import com.sim.salesrealization.SalesRealizationConstanant;
import com.sim.salesrealization.config.MariaDBManager;
import com.sim.salesrealization.model.DropDownEnum;
import com.sim.salesrealization.model.DropDownRequest;
import com.sim.salesrealization.model.SalesAggregatorResult;
import com.sim.salesrealization.topology.SalesTopology;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesService {

  //  private final KafkaStreams kafkaStreams;
 //   private final HostInfo hostInfo;

    public static final String ANSI_PURPLE = "\u001B[35m";

    /*@Autowired
    public SalesService(KafkaStreams kafkaStreams, HostInfo hostInfo) {
        this.kafkaStreams = kafkaStreams;
        this.hostInfo = hostInfo;
    }
*/

    public List<SalesAggregatorResult> getResult() {
        List<SalesAggregatorResult> result = new ArrayList<>();
         var res=getStore().all();
         //getStore().get()
        while (res.hasNext()) {
            KeyValue<Long, SalesAggregatorResult> next = res.next();
            System.out.println(ANSI_PURPLE +"count for " + next.key + ": " + next.value+ANSI_PURPLE );

        }
        res.close();
        return result;
    }

    private ReadOnlyKeyValueStore<Long, SalesAggregatorResult> getStore() {
        return null;
       /* return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        SalesTopology.SALES_AGGR_STORE,
                        QueryableStoreTypes.keyValueStore()));*/
    }

    public Map<DropDownEnum, List<?>> getDropdown(DropDownRequest reqObject) {
        Map<DropDownEnum, List<?>> response = new HashMap<>();
        if(null!=reqObject.getCurrent_dropdown()){
            StringBuilder query = new StringBuilder(SalesRealizationConstanant.DROP_DOWN_QUERY_PREFIX+reqObject.getCurrent_dropdown()+SalesRealizationConstanant.DROP_DOWN_FROM_QUERY+SalesRealizationConstanant.WHERE );

            query = addFilterParams(reqObject,query);

          //  query.append(SalesRealizationConstanant.SEMI_COLUMN);

            System.out.println(ANSI_PURPLE +query+ANSI_PURPLE );
           MariaDBManager sqldbm = new MariaDBManager();
            sqldbm.setUp();
            List<String> values= sqldbm.getDropDownValues(query.toString(),reqObject.getCurrent_dropdown());
            response.put(reqObject.getCurrent_dropdown(),values);
        }

        return response;
    }

    private StringBuilder addFilterParams(DropDownRequest reqObject, StringBuilder query) {
        boolean filterPresent = false;
        switch(reqObject.getCurrent_dropdown()) {
            case base_period:
                if(!isNullOrEmptyOrAll(reqObject.getSelection_type())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.selection_type + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getSelection_type()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }

                break;
            case current_period:
                if(!isNullOrEmptyOrAll(reqObject.getSelection_type())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.selection_type + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getSelection_type()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getBase_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.base_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getBase_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                break;
            case region:
                if(!isNullOrEmptyOrAll(reqObject.getSelection_type())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.selection_type + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getSelection_type()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getBase_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.base_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getBase_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCurrent_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.current_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCurrent_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                break;

            case engine:
                if(!isNullOrEmptyOrAll(reqObject.getSelection_type())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.selection_type + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getSelection_type()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getBase_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.base_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getBase_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCurrent_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.current_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCurrent_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getRegion())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.region + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getRegion()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                break;

            case certification:
                if(!isNullOrEmptyOrAll(reqObject.getSelection_type())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.selection_type + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getSelection_type()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getBase_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.base_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getBase_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCurrent_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.current_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCurrent_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getRegion())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.region + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getRegion()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getEngine())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.engine + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getEngine()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                break;
            case application:
                if(!isNullOrEmptyOrAll(reqObject.getSelection_type())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.selection_type + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getSelection_type()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getBase_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.base_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getBase_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCurrent_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.current_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCurrent_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getRegion())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.region + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getRegion()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getEngine())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.engine + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getEngine()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCertification())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.certification + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCertification()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                break;
            case channel:
                if(!isNullOrEmptyOrAll(reqObject.getSelection_type())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.selection_type + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getSelection_type()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getBase_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.base_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getBase_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCurrent_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.current_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCurrent_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getRegion())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.region + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getRegion()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getEngine())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.engine + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getEngine()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCertification())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.certification + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCertification()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getApplication())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.application + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getApplication()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                break;
            case customer:
                if(!isNullOrEmptyOrAll(reqObject.getSelection_type())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.selection_type + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getSelection_type()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getBase_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.base_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getBase_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCurrent_period())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.current_period + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCurrent_period()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getRegion())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.region + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getRegion()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getEngine())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.engine + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getEngine()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getCertification())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.certification + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getCertification()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getApplication())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.application + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getApplication()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                if(!isNullOrEmptyOrAll(reqObject.getChannel())){
                    query.append((filterPresent?SalesRealizationConstanant.AND:"")+DropDownEnum.channel + SalesRealizationConstanant.EQUALS+SalesRealizationConstanant.QUOTES+reqObject.getChannel()+SalesRealizationConstanant.QUOTES);
                    filterPresent =true;
                }
                break;
        }
        if(!filterPresent){
            query=query.delete(query.length()-SalesRealizationConstanant.WHERE.length(),query.length()-1);
        }
        return query;
    }

    static boolean isNullOrEmptyOrAll(String s) {
        return s == null || s.isBlank() || SalesRealizationConstanant.ALL.equalsIgnoreCase(s);
    }
}
