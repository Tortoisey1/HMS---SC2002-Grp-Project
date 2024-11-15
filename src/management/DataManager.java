package management;
import java.io.IOException;
import java.util.ArrayList;



/**
 * A generic interface that accepts two types: T1 for Object and T2 for String.
 * @param <T1> the type of the Object associated to the data manager
 * @param <T2> String associated to the data manager
 */
public interface DataManager<T1,T2>{
    /**
     * Retrieve {@param <T1>} the type of the Object based on the {@param t2} in String
     */
    T1 retrieve(T2 t2);

    /**
     * update {@param <T1>} the type of the Object and
     * @return true if update is successful else false
     */
    boolean update(T1 t1);

    /**
     * delete the object based on the {@param t1}
     * @return true if delete is successful else false
     */
    boolean delete(T2 t1);

    /**
     * add the object based on the {@param T1}
     * @return true if add is successful else false
     */
    boolean add(T1 T1);

    /**
     * Retrieve all the objects of type {@param <T1>} from CSV
     */
    void retrieveAll() throws IOException;

    /**
     * Write all the objects of type {@param <T1>} into CSV
     */
    void writeAll() throws IOException;

    /**
     * Retrieve {@link ArrayList} of all objects with type {@code <T1>}
     */
    ArrayList<T1> getList();
}
