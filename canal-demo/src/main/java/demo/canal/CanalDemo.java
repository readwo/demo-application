package demo.canal;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CanalDemo implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    private CanalConnector canalConnector;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        demo();

        applicationReadyEvent.getApplicationContext().close();
    }

    private void demo() {


        System.out.println("连接开始。。。。。。");
        canalConnector.connect();
        canalConnector.subscribe("demo\\..*");
        Message withoutAck = canalConnector.getWithoutAck(1000);

        long id = withoutAck.getId();
        int size = withoutAck.getEntries().size();
        System.out.println("id=" + id);
        System.out.println("size=" + size);
        List<CanalEntry.Entry> entries = withoutAck.getEntries();
        for (CanalEntry.Entry entry : entries) {
            System.out.println(entry.getHeader().getTableName());
            System.out.println(entry.getHeader().getSchemaName());
            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                Map<String, String> collect = afterColumnsList.stream().collect(Collectors.toMap(CanalEntry.Column::getName, CanalEntry.Column::getValue));
                System.out.println(collect);
            }

        }
        canalConnector.ack(id);

    }


}