package update;

import exceptions.TypeErrorException;

public interface UpdateService {
    public void add();

    public void remove();

    public void update(Object object) throws TypeErrorException;
}
