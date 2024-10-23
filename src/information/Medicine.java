package information;

public class Medicine {
    private String name;
    private int currentStock;
    private int alertStockLvl;

    public Medicine(String name, int currentStock, int alertStockLvl) {
        this.name = name;
        this.currentStock = currentStock;
        this.alertStockLvl = alertStockLvl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getAlertStockLvl() {
        return alertStockLvl;
    }

    public void setAlertStockLvl(int alertStockLvl) {
        this.alertStockLvl = alertStockLvl;
    }

}
