package update;

import exceptions.TypeErrorException;
import information.ReplenishmentRequest;

public class UpdateReplenishmentListService implements UpdateService {

    @Override
    public void add() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void update(Object object) throws TypeErrorException {
        if (!(object instanceof ReplenishmentRequest)) {
            throw new TypeErrorException("Not type of replenishment request");
        }

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
