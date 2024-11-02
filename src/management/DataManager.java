package management;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface DataManager<T1,T2>{
    T1 retrieve(T2 t2);
    boolean update(T1 t1);
    boolean delete(T2 t1);
    boolean add(T1 T1);
    void retrieveAll() throws IOException;
    void writeAll() throws IOException;
    ArrayList<T1> getList();
}
