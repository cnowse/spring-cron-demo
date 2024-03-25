package cn.cnowse.csv;

import static cn.cnowse.web.ResultCodeEnum.LOAD_CSV_ERROR;

import java.io.FileReader;
import java.io.Reader;
import java.util.function.Consumer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import cn.cnowse.web.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeong Geol
 */
@Slf4j // Lombok 的注解，加上该注解，相当于直接在代码中写 private static final Logger log = LoggerFactory.getLogger(CsvLoaderService.class);
@Lazy // 该 bean 懒加载，如果项目中所有的 bean 都加上该注解，可以在一定程度上加快项目启动速度，但是第一次请求出来，会很慢
@Service
@RequiredArgsConstructor // Lombok 的注解，会为 final 修饰的字段生成构造函数
public class CsvLoaderService {

    /** 使用该工具，可以直接从 resource 路径下读取文件 */
    private final ResourceLoader resourceLoader;

    public void example() {
        /*
        // 判断库中有无数据，没有数据再从 csv 文件中加载数据
        if (authorizeIdCommentRepository.findAll().isEmpty()) {
        // 文件名直接写名称，无需加后缀，这里使用 Consumer 函数式接口，去处理具体的业务逻辑
            csvHandler("apiAndGroup", csvRecord -> {
                Integer type = Integer.valueOf(csvRecord.get(0));
                Integer apiOrGroupId = Integer.valueOf(csvRecord.get(1));
                String name = csvRecord.get(2);
                String comment = csvRecord.get(3);
                String uri = csvRecord.get(4);
                AuthorizeIdComment api = new AuthorizeIdComment();
                api.setType(type).setApiOrGroupId(apiOrGroupId).setName(name).setComment(comment).setUri(uri);
                authorizeIdCommentRepository.save(api);
            });
        }
         */
    }

    /**
     * 从 csv 文件中读取数据，并执行指定逻辑
     *
     * @param fileName csv 文件名，不包含后缀
     * @param consumer 具体读取 csv 后执行的逻辑
     * @author Jeong Geol
     */
    private void csvHandler(String fileName, Consumer<CSVRecord> consumer) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader()
                .setSkipHeaderRecord(true).setTrim(true).build();
        try (
                Reader reader = new FileReader(resourceLoader.getResource("classpath:" + fileName + ".csv").getFile());
                CSVParser csvParser = new CSVParser(reader, csvFormat)) {
            csvParser.forEach(consumer);
        } catch (Exception e) {
            log.error("从CSV文件读取数据失败 ", e);
            throw new ServiceException(LOAD_CSV_ERROR);
        }
    }

}
