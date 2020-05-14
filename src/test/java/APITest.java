import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;

public class APITest {
    List<String> links;

    @BeforeTest
    public void readAllLinks(){
        String path = "src\\test\\resources\\autoria_API_links.csv";
        links = Collections.emptyList();

        try {
            links = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "links")
    public Object[] dataProvider(){
        return links.stream()
                .map(link -> new Object[] {link})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "links")
    public void APITestExecution(String url){
        with().
                header("User-Agent", "Jmeter")
        .when()
                .get(url)
        .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "text/html; charset=utf-8")
                .header("Content-Encoding", "gzip");
    }

}
