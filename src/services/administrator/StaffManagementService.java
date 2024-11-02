package services.administrator;


import java.util.List;

import entities.Staff;
import exceptions.StaffDoesNotExistException;
import exceptions.StaffExistException;
import information.id.UserID;

public abstract class StaffManagementService {
  private List<Staff> staffList;

  public void addStaff(Staff staff) throws StaffExistException {
      // check if staff already exist in the list
      Staff checkedStaff = findStaff(staff.getUserInformation().getID());
      if (checkedStaff == null) {
          this.staffList.add(checkedStaff);
          System.out.println("Staff added: \n" + staff);
      } else {
          throw new StaffExistException("Staff already exist in the system");
      }
  }

  // transfered functionality to updateservice
  public abstract void updateStaffList(Staff staff);

  public void removeStaff(Staff staff) throws StaffDoesNotExistException {
      // check if staff exist in the list
      Staff checkedStaff = findStaff(staff.getUserInformation().getID());
      if (checkedStaff != null) {
          this.staffList.remove(staff);
          System.out.println("Staff removed: \n"
                  + staff);
      } else {
          throw new StaffDoesNotExistException("Staff does not exist, can't remove");
      }
  }

   // ○ Display a list of staff filtered by role, gender, age, etc.
   // transfered functionality to displaylistservice

  public Staff findStaff(UserID id) {
      for (Staff staff : this.staffList) {
          if (staff.getUserInformation().getID().equals(id)) {
              return staff;
          }
      }
      return null;
  }

}
