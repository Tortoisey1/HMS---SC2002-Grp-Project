import java.io.IOException;
import java.util.ArrayList;

import entities.Staff;
import information.ReplenishmentRequest;

public class ReplenishmentRequestDataManager implements DataManager<ReplenishmentRequest,String> {
  private static DataManager<ReplenishmentRequest, String> ReplenishmentRequest;
  private final String inputFilePath = "src/data/staffs.csv";
  private static ArrayList<Staff> staffs = new ArrayList<Staff>();

  @Override
  public ReplenishmentRequest retrieve(String t2) {

  }

  @Override
  public boolean update(ReplenishmentRequest t1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public boolean delete(String t1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public boolean add(ReplenishmentRequest T1) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'add'");
  }

  @Override
  public void retrieveAll() throws IOException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'retrieveAll'");
  }

  @Override
  public void writeAll() throws IOException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'writeAll'");
  }

  @Override
  public ArrayList<ReplenishmentRequest> getList() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getList'");
  }

}