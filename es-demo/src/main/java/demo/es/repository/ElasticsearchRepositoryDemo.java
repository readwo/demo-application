package demo.es.repository;

import demo.es.vo.EsDataStrip;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface ElasticsearchRepositoryDemo{


    List<EsDataStrip>  getDistinctByVehicleNo();
    //getDistinctByVehicleNo();



}
